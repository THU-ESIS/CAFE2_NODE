package cn.edu.tsinghua.cess.task.service;

import java.util.List;

import cn.edu.tsinghua.cess.component.remote.Remote;
import cn.edu.tsinghua.cess.task.entity.dto.TaskSubmition;

public interface TaskSubmitionService {
	
	@Remote("/task/submit")
	public String submitTask(TaskSubmition submition);

    @Remote("/task/subtask/submit")
    public List<Integer> submitSubTask(TaskSubmition submition);

}
