package cn.edu.tsinghua.cess.task.entity.dto;

import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.task.entity.ScriptArgument;

import java.util.List;

public class TaskSubmition {

    private List<Model> models;
	private ScriptArgument nclScript;

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

	public ScriptArgument getNclScript() {
		return nclScript;
	}

	public void setNclScript(ScriptArgument script) {
		this.nclScript = script;
	}


    @Override
    public String toString() {
        return "TaskSubmition{" +
            "models=" + models +
            ", nclScript=" + nclScript +
            '}';
    }

}