package cn.edu.tsinghua.cess.task.service.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.tsinghua.cess.task.dao.TaskSubmitionDao;
import cn.edu.tsinghua.cess.task.entity.SubTask;
import cn.edu.tsinghua.cess.task.service.TaskExecutionService;
import cn.edu.tsinghua.cess.task.service.impl.executor.TaskExecutor;
import cn.edu.tsinghua.cess.task.service.impl.executor.TaskExecutorFactory;

@Component
public class TaskExecutionServiceImpl implements TaskExecutionService {
	
	private Logger log = Logger.getLogger(getClass());
	
	@Autowired TaskExecutorFactory factory;
    @Autowired TaskSubmitionDao taskSubmitionDao;
	private ExecutorService service;

	@Override
	public void addTask(SubTask subTask) {
		log.info("adding task to scheduler, taskId=" + subTask.getId() + ", taskSubmition=" + subTask);
		
		TaskExecutor executor = factory.getTaskExecutor(subTask);
		service.submit(executor);
	}

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		service = Executors.newFixedThreadPool(3);
		
		log.info("execution service initializing, will load running task into scheduler");

        List<SubTask> runningSubTaskList = taskSubmitionDao.listRunningSubTask();
        if (runningSubTaskList != null) {
            for (SubTask t : runningSubTaskList) {
            	try {
            		this.addTask(t);
            	} catch (Exception e) {
            		log.error("error adding task on initialization", e);
				} 
            }
        }
	}

}
