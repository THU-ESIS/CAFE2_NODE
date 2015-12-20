package cn.edu.tsinghua.cess.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.edu.tsinghua.cess.task.entity.SubTask;
import cn.edu.tsinghua.cess.task.entity.SubTaskListEntry;
import cn.edu.tsinghua.cess.task.entity.Task;

public interface TaskSubmitionDao {
	
	public Integer insert(Task task);
	
	public Integer insertSubTask(SubTask subTask);
	
	public void insertSubTaskListEntry(SubTaskListEntry entry);
	
	public List<SubTask> listRunningSubTask();
	
	public SubTask querySubTask(@Param("id") Integer id);

}
