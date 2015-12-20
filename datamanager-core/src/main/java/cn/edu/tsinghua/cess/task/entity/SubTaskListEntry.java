package cn.edu.tsinghua.cess.task.entity;

import cn.edu.tsinghua.cess.workernode.entity.WorkerNode;

public class SubTaskListEntry {
	
	private Integer taskId;
	private Integer subTaskId;
	private Integer nodeId;

	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getSubTaskId() {
		return subTaskId;
	}
	public void setSubTaskId(Integer subTaskId) {
		this.subTaskId = subTaskId;
	}
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	@Override
	public String toString() {
		return "SubTaskListEntry{" +
			"taskId=" + taskId +
			", subTaskId=" + subTaskId +
			", nodeId=" + nodeId +
			'}';
	}

}
