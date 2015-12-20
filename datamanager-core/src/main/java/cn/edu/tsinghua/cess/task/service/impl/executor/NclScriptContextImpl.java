package cn.edu.tsinghua.cess.task.service.impl.executor;

import java.util.List;

import cn.edu.tsinghua.cess.component.exception.ExceptionHandler;
import cn.edu.tsinghua.cess.datamanager.nclscript.NclScriptContext;
import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.modelfile.service.ModelFileQueryService;
import cn.edu.tsinghua.cess.task.dao.TaskExecutionDao;
import cn.edu.tsinghua.cess.task.entity.ScriptArgument;
import cn.edu.tsinghua.cess.task.entity.SubTask;
import cn.edu.tsinghua.cess.task.service.impl.executor.index.Index;
import cn.edu.tsinghua.cess.task.service.impl.executor.index.IndexParser;
import cn.edu.tsinghua.cess.task.service.impl.executor.index.IndexParserFactory;
import cn.edu.tsinghua.cess.task.service.impl.executor.index.TemporalRange;

/**
 * Created by kurt on 2014/9/17.
 */
public class NclScriptContextImpl implements NclScriptContext {

	private Integer subTaskId;
    private Model model;
	private ScriptArgument argument;
	private TaskExecutionDao taskExecutionDao;
	private String inputFileFolder;
	private List<String> ncFileList;
	private int beginIndex;
	private int endIndex;

	
	private NclScriptContextImpl() {}
	
	@Override
	public Integer getTaskId() {
		return subTaskId;
	}

    @Override
    public List<String> getNcFileList() {
    	return ncFileList;
    }

    @Override
    public int getBeginIndex() {
    	return beginIndex;
    }

    @Override
    public int getEndIndex() {
    	return endIndex;
    }

    @Override
    public String getBeginTime() {
        return argument.getTemporalStart();
    }

    @Override
    public String getEndTime() {
    	return argument.getTemporalEnd();
    }

    @Override
    public String getInputFileFolder() {
    	return inputFileFolder;
    }

    @Override
    public String getVarName() {
        return model.getVariableName();
    }

    @Override
    public int getLatMin() {
        return argument.getLatMin();
    }

    @Override
    public int getLatMax() {
        return argument.getLatMax();
    }

    @Override
    public int getLonMin() {
        return argument.getLonMin();
    }

    @Override
    public int getLonMax() {
        return argument.getLonMax();
    }

    @Override
    public void addResult(String type, String resultFilePath) {
        taskExecutionDao.addResultFile(subTaskId, type, resultFilePath);
    }

    @Override
    public void updateProgress(int percentage, String comment) {
        taskExecutionDao.updateProgress(subTaskId, percentage, comment);
    }

    @Override
    public void failed(Exception e) {
        ExceptionHandler.wrapAsUnchecked(e);
    }
    
    public static NclScriptContext newInstance(
    			SubTask subTask,
    			TaskExecutionDao taskExecutionDao,
    			ModelFileQueryService modelFileQueryService) {
    	NclScriptContextImpl context = new NclScriptContextImpl();
    	
        context.subTaskId = subTask.getId();
        context.argument = subTask.getScriptEntity();
        context.taskExecutionDao = taskExecutionDao;
        context.model = modelFileQueryService.queryModelWithTemporal(subTask.getModelEntity());

        IndexParser parser = IndexParserFactory.getParser(context.model);
        Index index = parser.parse(
        				new TemporalRange(context.model.getTemporalStart(), context.model.getTemporalEnd()),
        				new TemporalRange(context.argument.getTemporalStart(), context.argument.getTemporalEnd())
        			);
        
        context.beginIndex = index.getBeginIndex();
        context.endIndex = index.getEndIndex();
        context.ncFileList = modelFileQueryService.queryModelFile(context.model);
        
        String file = context.ncFileList.get(0);
    	context.inputFileFolder = file.substring(0, file.lastIndexOf('/'));
        
        return context;
    }

}
