package cn.edu.tsinghua.cess.task.entity;

/**
 * Created by kurt on 2014/9/17.
 */
public class SubTaskResultFile {

    private Integer id;
    private Integer subTaskId;
    private String type;
    private String path;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getSubTaskId() {
		return subTaskId;
	}
	public void setSubTaskId(Integer subTaskId) {
		this.subTaskId = subTaskId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

}
