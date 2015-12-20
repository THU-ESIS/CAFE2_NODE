package cn.edu.tsinghua.cess.task.service.impl;

import cn.edu.tsinghua.cess.component.remote.RemoteServiceFactory;
import cn.edu.tsinghua.cess.deployment.entity.Deployment;
import cn.edu.tsinghua.cess.deployment.service.DeploymentService;
import cn.edu.tsinghua.cess.task.dao.TaskQueryDao;
import cn.edu.tsinghua.cess.task.entity.SubTask;
import cn.edu.tsinghua.cess.task.entity.SubTaskListEntry;
import cn.edu.tsinghua.cess.task.entity.SubTaskResultFile;
import cn.edu.tsinghua.cess.task.entity.Task;
import cn.edu.tsinghua.cess.task.entity.dto.SubTaskFile;
import cn.edu.tsinghua.cess.task.entity.dto.SubTaskResult;
import cn.edu.tsinghua.cess.task.service.TaskQueryService;
import cn.edu.tsinghua.cess.workernode.entity.WorkerNode;
import cn.edu.tsinghua.cess.workernode.service.WorkerNodeManageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by kurt on 2014/9/22.
 */
@Component
public class TaskQueryServiceImpl implements TaskQueryService {
	
	private Logger log = Logger.getLogger(getClass());

    @Autowired
    TaskQueryDao taskQueryDao;
    @Autowired
    DeploymentService deploymentService;
    @Autowired
    RemoteServiceFactory remoteServiceFactory;

    @Autowired
    @Qualifier("apiPath")
    String apiPath;


    @Override
    public SubTaskResult[] queryTaskResult(String uuid) {
        Task task = taskQueryDao.queryByUuid(uuid);
        List<SubTaskListEntry> subTaskList;
        if (task == null) {
            subTaskList = new ArrayList<SubTaskListEntry>();
        } else {
            subTaskList =taskQueryDao.querySubTaskList(task.getId());
        }
        
        log.info("query subTaskListEntry of [taskId=" + uuid + "], got [" + subTaskList + "]");
        
        return this.queryByDeployMode(subTaskList);
    }

    private SubTaskResult[] queryByDeployMode(List<SubTaskListEntry> subTaskList) {
        Deployment deployment = deploymentService.get();
        switch (deployment.getMode()) {
            case DISTRIBUTED_WORKER:
                return this.queryByWorkerNode(subTaskList);
            case LOCAL:
                return this.queryAsLocal(subTaskList);

        }

        throw new IllegalArgumentException();
    }


    private TaskQueryService getSubTaskQueryService(Integer nodeId) {
        Deployment deployment = deploymentService.get();

        if (deployment.getNodeId().equals(nodeId)) {
            return this;
        } else {
            WorkerNode workerNode = remoteServiceFactory.getRemoteService(deployment.getCentralServer(), WorkerNodeManageService.class).queryById(nodeId);
            return remoteServiceFactory.getRemoteService(workerNode, TaskQueryService.class);
        }
    }


    @Override
    public SubTaskResult[] querySubTaskResult(Integer[] subTaskId) {
        List<SubTaskResult> list = new ArrayList<SubTaskResult>();

        for (Integer id : subTaskId) {
            SubTaskResult r = this.querySingleSubTaskResult(id);
            list.add(r);
        }

        return list.toArray(new SubTaskResult[0]);
    }

    private SubTaskResult querySingleSubTaskResult(Integer subTaskId) {
    	log.info("querySingleSubTaskResult called, [subTaskId=" + subTaskId + "]");
    	
        Deployment deployment = deploymentService.get();
        SubTask subTask = taskQueryDao.querySubTaskResult(subTaskId);

        SubTaskResult result = new SubTaskResult();
        result.setModel(subTask.getModelEntity());
        result.setStatus(subTask.getStatus());
        result.setFailureCause(subTask.getFailureCause());
        result.setProgress(subTask.getProgress());
        result.setProgressDescription(subTask.getProgressDescription());


        List<SubTaskFile> dtoList = new ArrayList<SubTaskFile>();
        for (SubTaskResultFile file : subTask.getResultFileList()) {
            String url = "http://" + deployment.getNodeIp() + ":" + deployment.getNodePort()
                    + "/" + deployment.getRootPath() + apiPath + "/task/resultfile/"
                    + subTaskId + "/" + file.getId();

            SubTaskFile dto = new SubTaskFile();
            dto.setType(file.getType());
            dto.setUrl(url);

            dtoList.add(dto);
        }
        
        result.setResultFile(dtoList);

        return result;
    }

    private SubTaskResult[] queryAsLocal(List<SubTaskListEntry> subTaskList) {
    	log.info("queryAsLocal called");
    	
        List<Integer> subTaskIdList = new ArrayList<Integer>();
        for (SubTaskListEntry entry : subTaskList) {
            subTaskIdList.add(entry.getSubTaskId());
        }

        return querySubTaskResult(subTaskIdList.toArray(new Integer[0]));
    }

    private SubTaskResult[] queryByWorkerNode(List<SubTaskListEntry> subTaskList) {
    	log.info("queryByWorkerNode called");
    	
        List<SubTaskResult> subTaskResultList = new ArrayList<SubTaskResult>();

        Map<Integer, List<Integer>> nodeIdListMap = new HashMap<Integer, List<Integer>>();

        for (SubTaskListEntry entry : subTaskList) {
            Integer nodeId = entry.getNodeId();

            if (!nodeIdListMap.containsKey(nodeId)) {
                nodeIdListMap.put(nodeId, new ArrayList<Integer>());
            }
            nodeIdListMap.get(nodeId).add(entry.getSubTaskId());
        }
        
        log.info("this query will involve [nodeCount=" + nodeIdListMap.size() + "] worker nodes");

        for (Map.Entry<Integer, List<Integer>> entry : nodeIdListMap.entrySet()) {
            Integer nodeId = entry.getKey();
            List<Integer> subTaskIdList = entry.getValue();
            
            log.info("will query [nodeId=" + nodeId + "], with [subTaskIdList=" + subTaskIdList + "]");
            SubTaskResult[] subTaskResultListOfNode = getSubTaskQueryService(nodeId).querySubTaskResult(subTaskIdList.toArray(new Integer[0]));

            subTaskResultList.addAll(Arrays.asList(subTaskResultListOfNode));
        }

        return subTaskResultList.toArray(new SubTaskResult[0]);
    }

	@Override
	public SubTaskResultFile querySubTaskResultFile(Integer subTaskId, Integer resultFileId) {
		log.info("querySubTaskResultFile called");
		
        SubTask result = taskQueryDao.querySubTaskResult(subTaskId);

        if (result != null) {
            List<SubTaskResultFile> files  = result.getResultFileList();

            if (!CollectionUtils.isEmpty(files)) {
                for (SubTaskResultFile f : files) {
                    if (f.getId().equals(resultFileId)) {
                        return f;
                    }
                }
            }

        }

		return null;
	}

}
