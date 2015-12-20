package cn.edu.tsinghua.cess.task.entity.dto;

import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.task.entity.SubTaskStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubTaskResult {
	
    private Model model;
    private SubTaskStatus status;
    private Integer progress;
    private String progressDescription;
    private String failureCause;

    private List<SubTaskFile> resultFile;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
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

    public List<SubTaskFile> getResultFile() {
        return resultFile;
    }

    public void setResultFile(List<SubTaskFile> resultFile) {
        this.resultFile = resultFile;
    }

}
