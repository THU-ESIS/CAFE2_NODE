package cn.edu.tsinghua.cess.task.service.impl.executor;

import cn.edu.tsinghua.cess.datamanager.nclscript.NclScript;
import cn.edu.tsinghua.cess.datamanager.nclscript.NclScriptContext;
import cn.edu.tsinghua.cess.modelfile.service.ModelFileQueryService;
import cn.edu.tsinghua.cess.task.dao.TaskExecutionDao;
import cn.edu.tsinghua.cess.task.entity.SubTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

@Component
public class TaskExecutorFactory {

    @Autowired TaskExecutionDao taskExecutionDao;
    @Autowired NclScriptFactory nclScriptFactory;
    @Autowired ModelFileQueryService modelFileQueryService;


    public TaskExecutor getTaskExecutor(final SubTask subTask) {
    	Callable<NclScriptContext> contextBuilder = new Callable<NclScriptContext>() {
			@Override
			public NclScriptContext call() throws Exception {
		        return NclScriptContextImpl.newInstance(subTask, taskExecutionDao, modelFileQueryService);
			}
		};
		Callable<NclScript> scriptBuilder = new Callable<NclScript>() {
			@Override
			public NclScript call() throws Exception {
		        return nclScriptFactory.getNclScript(subTask.getScriptEntity().getName());
			}
		};

        return TaskExecutor.newInstance(subTask, taskExecutionDao, contextBuilder, scriptBuilder);
	}

}
