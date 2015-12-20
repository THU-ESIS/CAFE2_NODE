package cn.edu.tsinghua.cess.task.entity;

import cn.edu.tsinghua.cess.component.exception.ExceptionHandler;
import cn.edu.tsinghua.cess.modelfile.entity.Model;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.concurrent.Callable;

public class SubTask {

    private static ObjectMapper mapper = new ObjectMapper();

    private Integer id;
    private Model modelEntity;
    private ScriptArgument scriptEntity;
	private SubTaskStatus status;
	private Integer progress;
	private String progressDescription;
    private String failureCause;

    private List<SubTaskResultFile> resultFileList;


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getModel() {
        return ExceptionHandler.unchecked(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return mapper.writeValueAsString(modelEntity);
            }
        });
    }
    public void setModel(final String model) {
        ExceptionHandler.unchecked(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                SubTask.this.modelEntity = mapper.readValue(model, Model.class);
                return null;
            }
        });
    }
    public String getScript() {
        return ExceptionHandler.unchecked(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return mapper.writeValueAsString(scriptEntity);
            }
        });
    }
    public void setScript(final String script) {
        ExceptionHandler.unchecked(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                SubTask.this.scriptEntity = mapper.readValue(script, ScriptArgument.class);
                return null;
            }
        });
    }
    public Model getModelEntity() {
        return modelEntity;
    }
    public void setModelEntity(Model modelEntity) {
        this.modelEntity = modelEntity;
    }
    public ScriptArgument getScriptEntity() {
        return scriptEntity;
    }
    public void setScriptEntity(ScriptArgument scriptEntity) {
        this.scriptEntity = scriptEntity;
    }
    public String getFailureCause() {
        return failureCause;
    }
    public void setFailureCause(String failureCause) {
        this.failureCause = failureCause;
    }
    public SubTaskStatus getStatus() {
		return status;
	}
	public void setStatus(SubTaskStatus status) {
		this.status = status;
	}
	public Integer getProgress() {
		return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	public String getProgressDescription() {
		return progressDescription;
	}
	public void setProgressDescription(String progressDescription) {
		this.progressDescription = progressDescription;
	}
    public List<SubTaskResultFile> getResultFileList() {
        return resultFileList;
    }
    public void setResultFileList(List<SubTaskResultFile> resultFileList) {
        this.resultFileList = resultFileList;
    }

    public static SubTask newInstance(Model model, ScriptArgument script) {
		SubTask task = new SubTask();
        task.setModelEntity(model);
        task.setScriptEntity(script);
		task.setStatus(SubTaskStatus.running);
        task.setProgress(0);
		
		return task;
	}
	
}