package cn.edu.tsinghua.cess.task.service.impl.executor;

import java.util.concurrent.Callable;

import cn.edu.tsinghua.cess.task.entity.SubTask;
import org.apache.log4j.Logger;

import cn.edu.tsinghua.cess.datamanager.nclscript.NclScript;
import cn.edu.tsinghua.cess.datamanager.nclscript.NclScriptContext;
import cn.edu.tsinghua.cess.task.dao.TaskExecutionDao;
import cn.edu.tsinghua.cess.task.entity.SubTaskStatus;

public class TaskExecutor implements Runnable {

	private static Logger log = Logger.getLogger(TaskExecutor.class);
	
	private SubTask subTask;
	private TaskExecutionDao taskExecutionDao;
	private Callable<NclScriptContext> contextBuilder;
	private Callable<NclScript> scriptBuilder;

	private TaskExecutor() {}

	@Override
	public void run() {
        try {
            long begin = System.currentTimeMillis();
            StringBuilder builder = new StringBuilder();
            builder.append("begin to execute task, ")
                    .append("[id=").append(subTask.getId()).append("]")
                    .append("[model=").append(subTask.getModel()).append("]")
                    .append("[script=").append(subTask.getScript()).append("]");

            log.info(builder.toString());



        	NclScriptContext context = contextBuilder.call();
        	NclScript script = scriptBuilder.call();

            script.run(context);
            taskExecutionDao.setStatus(subTask.getId(), SubTaskStatus.finished, null);


            builder = new StringBuilder();
            builder.append("task execution succeeded, ")
                    .append("[id=").append(subTask.getId()).append("]")
                    .append("[elapsed=").append(System.currentTimeMillis() - begin).append("]");
            log.info(builder.toString());
        } catch (Exception ex) {
            taskExecutionDao.setFailed(subTask.getId(), ex.getMessage());

            StringBuilder builder = new StringBuilder();
            builder.append("task execution failed, ")
                    .append("[id=").append(subTask.getId()).append("]")
                    .append("[exception=").append(ex.getMessage()).append("]");

            log.error(builder.toString(), ex);
        }
	}
	
	public static TaskExecutor newInstance(
			    SubTask subTask,
			    TaskExecutionDao taskExecutionDao,
			    Callable<NclScriptContext> contextBuilder,
			    Callable<NclScript> scriptBuilder) {
		TaskExecutor executor = new TaskExecutor();
		
        executor.subTask = subTask;
        executor.taskExecutionDao = taskExecutionDao;
        executor.contextBuilder = contextBuilder;
        executor.scriptBuilder = scriptBuilder;
		
        return executor;
	}

}
