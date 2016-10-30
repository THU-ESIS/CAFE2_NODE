package cn.edu.tsinghua.cess.task.service.impl;

import cn.edu.tsinghua.cess.component.remote.RemoteServiceFactory;
import cn.edu.tsinghua.cess.deployment.entity.Deployment;
import cn.edu.tsinghua.cess.deployment.service.DeploymentService;
import cn.edu.tsinghua.cess.modelfile.dao.ModelFileDao;
import cn.edu.tsinghua.cess.modelfile.dto.ModelNodeRelation;
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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("workerNodeSubmitionService")
public class WorkerNodeSubmitionServiceImpl implements TaskSubmitionService {
	
	private Logger log = Logger.getLogger(getClass());

    @Autowired DeploymentService deploymentService;
	@Autowired TaskExecutionService taskExecutionService;
	@Autowired TaskSubmitionServiceFactory serviceFactory;
	@Autowired TaskSubmitionDao taskSubmitionDao;
	@Autowired ModelFileDao modelFileDao;
	@Autowired RemoteServiceFactory remoteServiceFactory;

	ObjectMapper objectMapper; {
		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}


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

	/**
	 * will assure that duplicated model across multiple nodes will only be accounted only once.
	 * @param relations
	 * @return
	 */
	private Map<WorkerNode, Set<Model>> classify(ModelNodeRelation[] relations) {
		Set<Model> containedModels = new HashSet<Model>();

		Map<WorkerNode, Set<Model>> result = new HashMap<WorkerNode, Set<Model>>();

        for (ModelNodeRelation relation : relations) {
        	Model model = relation.getModel();

        	if (containedModels.contains(model)) {
        		continue;
			}


        	Set<Model> set = result.get(relation.getWorkerNode());
			if (set == null) {
				set = new HashSet<Model>();
				result.put(relation.getWorkerNode(), set);
			}

			set.add(model);
			containedModels.add(model);
		}

		return result;
	}

	private TaskSubmition getActualSubmition(TaskSubmition submition, Map<WorkerNode, Set<Model>> workerNodeSetMap) {
		List<Model> actualSubmitionModels = new ArrayList<Model>();
		for (Set<Model> models : workerNodeSetMap.values()) {
			actualSubmitionModels.addAll(models);
		}

		Collections.sort(actualSubmitionModels, new ModelSubmitionOrderSortingComparator(
				submition.getModels()
		));

		log.info("actual submition models=" + toJsonString(actualSubmitionModels));

	    TaskSubmition actualSubmition = new TaskSubmition();
        actualSubmition.setNclScript(submition.getNclScript());
		actualSubmition.setModels(actualSubmitionModels);

		return actualSubmition;
	}

	private String toJsonString(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
            log.error("error formatting json", e);
			return String.valueOf(object);
		}
	}

	@Override
	public String submitTask(TaskSubmition submition) {
		log.info("submitTask called, submition=" + submition);
		Date ts = new Date();

		// query model file related workerNodes
		// note that some model file may exist on multiple workerNode
		// this central modelfile query service will filter out duplicated models
		// only remaining one single model for the duplicating ones
		ModelNodeRelation[] modelNodeRelations = getCentralModelFileQueryService().queryRelatedNodes(submition.getModels().toArray(new Model[0]));

		log.info("modelNodeRelation=" + toJsonString(modelNodeRelations));

		Map<WorkerNode, Set<Model>> workerModelSetMap = this.classify(modelNodeRelations);

		log.info("classified modelNode relation=" + toJsonString(workerModelSetMap));


		Task task = new Task();
		task.setUuid(UUID.randomUUID().toString());
		task.setCreateTime(ts);
		task.setSubmitionEntity(
				this.getActualSubmition(submition, workerModelSetMap)
		);

		taskSubmitionDao.insert(task);

    	log.info("this submition will involve [count=" + workerModelSetMap.size() + "] workerNodes");

		for (Map.Entry<WorkerNode, Set<Model>> mapEntry : workerModelSetMap.entrySet()) {
			WorkerNode workerNode = mapEntry.getKey();
            List<Model> modelList = new ArrayList<Model>(mapEntry.getValue());

            TaskSubmition nodeSubmition = new TaskSubmition();
			nodeSubmition.setModels(modelList);
			nodeSubmition.setNclScript(submition.getNclScript());

			try {
				log.info("begin to submit subtask on [workerNodeId=" + workerNode.getId() + "]");

				List<Integer> subTaskIds = getSubmitionServiceByNode(workerNode).submitSubTask(nodeSubmition);

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

		return task.getUuid();
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
