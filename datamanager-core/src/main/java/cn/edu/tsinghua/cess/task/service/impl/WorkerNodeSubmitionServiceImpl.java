package cn.edu.tsinghua.cess.task.service.impl;

import cn.edu.tsinghua.cess.component.remote.RemoteServiceFactory;
import cn.edu.tsinghua.cess.deployment.entity.Deployment;
import cn.edu.tsinghua.cess.deployment.service.DeploymentService;
import cn.edu.tsinghua.cess.modelfile.dao.ModelFileDao;
import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.modelfile.service.ModelFileQueryService;
import cn.edu.tsinghua.cess.task.dao.TaskSubmitionDao;
import cn.edu.tsinghua.cess.task.entity.SubTask;
import cn.edu.tsinghua.cess.task.entity.SubTaskListEntry;
import cn.edu.tsinghua.cess.task.entity.Task;
import cn.edu.tsinghua.cess.task.entity.dto.TaskSubmition;
import cn.edu.tsinghua.cess.task.service.TaskExecutionService;
import cn.edu.tsinghua.cess.task.service.TaskSubmitionService;
import cn.edu.tsinghua.cess.workernode.entity.WorkerNode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component("workerNodeSubmitionService")
public class WorkerNodeSubmitionServiceImpl implements TaskSubmitionService {
	
	private Logger log = Logger.getLogger(getClass());

    @Autowired DeploymentService deploymentService;
	@Autowired TaskExecutionService taskExecutionService;
	@Autowired TaskSubmitionServiceFactory serviceFactory;
	@Autowired TaskSubmitionDao taskSubmitionDao;
	@Autowired ModelFileDao modelFileDao;
	@Autowired RemoteServiceFactory remoteServiceFactory;


	private ModelFileQueryService getCentralModelFileQueryService() {
		Deployment deployment = deploymentService.get();
		return remoteServiceFactory.getRemoteService(deployment.getCentralServer(), ModelFileQueryService.class);
	}

	private TaskSubmitionService getSubmitionServiceByNode(WorkerNode workerNode) {
		Deployment deployment = deploymentService.get();

		if (deployment.getNodeId().equals(workerNode.getId())) {
			return this;
		} else {
			return remoteServiceFactory.getRemoteService(workerNode, TaskSubmitionService.class);
		}
	}

	@Override
	public String submitTask(TaskSubmition submition) {
		log.info("submitTask called, submition=" + submition);

		Date ts = new Date();
		String taskId = UUID.randomUUID().toString();

		Task task = new Task();
		task.setUuid(taskId);
		task.setCreateTime(ts);
		task.setSubmitionEntity(submition);

		taskSubmitionDao.insert(task);

		WorkerNode[] workerNodeList = getCentralModelFileQueryService().queryRelatedNodes(submition.getModels().toArray(new Model[0]));

    	log.info("this submition will involve [count=" + workerNodeList.length + "] workerNodes");

		for (WorkerNode workerNode : workerNodeList) {
			try {
				log.info("begin to submit subtask on [workerNodeId=" + workerNode.getId() + "]");

				List<Integer> subTaskIds = getSubmitionServiceByNode(workerNode).submitSubTask(submition);

				log.info("successfully submitted subtask, [result=" + subTaskIds + "]");

				for (Integer id : subTaskIds) {
					SubTaskListEntry entry = new SubTaskListEntry();
					entry.setTaskId(task.getId());
					entry.setSubTaskId(id);
					entry.setNodeId(workerNode.getId());

					taskSubmitionDao.insertSubTaskListEntry(entry);
				}
			} catch (Exception e) {
				String message = "error occured while submiting subTask, [targetNodeId=" + workerNode.getId() + "]";
				log.error(message, e);
			}
		}

		return taskId;
	}

	@Override
	public List<Integer> submitSubTask(TaskSubmition submition) {
    	log.info("submitSubTask called");
    	
        List<Integer> subTaskIdList = new ArrayList<Integer>();

        List<Model> modelList = modelFileDao.queryModelOfLocal(submition.getModels());
        
        log.info("this submition will involve [modelCount=" + modelList.size() + "] model(s)");

        for (Model model : modelList) {
        	try {
	            SubTask task = SubTask.newInstance(model, submition.getNclScript());
	
	            taskSubmitionDao.insertSubTask(task);
	            taskExecutionService.addTask(task);
	
	            subTaskIdList.add(task.getId());

	            log.info("subtask created, [subTaskId=" + task.getId() + "]");
        	} catch (Exception e) {
        		String message = "error occurred while submiting task, msg=" + e.getMessage();
        		log.error(message, e);
			}
        }

        return subTaskIdList;
    }

}
