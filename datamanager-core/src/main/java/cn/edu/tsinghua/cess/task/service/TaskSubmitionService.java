package cn.edu.tsinghua.cess.task.service;

import cn.edu.tsinghua.cess.component.remote.Remote;
import cn.edu.tsinghua.cess.task.entity.dto.TaskSubmition;

import java.util.List;

public interface TaskSubmitionService {

    @Remote("/task/submit")
    public String submitTask(TaskSubmition submition);

    @Remote("/task/subtask/submit")
    public List<Integer> submitSubTask(TaskSubmition submition);

}
