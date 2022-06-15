package cn.edu.tsinghua.cess.task.dao;

import cn.edu.tsinghua.cess.task.entity.SubTask;
import cn.edu.tsinghua.cess.task.entity.SubTaskListEntry;
import cn.edu.tsinghua.cess.task.entity.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by kurt on 2014/9/22.
 */
public interface TaskQueryDao {

    public Task queryByUuid(String uuid);

    public List<SubTaskListEntry> querySubTaskList(@Param("taskId") Integer taskId);

    public SubTask querySubTaskResult(@Param("subTaskId") Integer subTaskId);

}
