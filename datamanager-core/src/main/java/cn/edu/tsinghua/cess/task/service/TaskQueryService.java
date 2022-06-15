package cn.edu.tsinghua.cess.task.service;

import cn.edu.tsinghua.cess.component.remote.Remote;
import cn.edu.tsinghua.cess.task.entity.SubTaskResultFile;
import cn.edu.tsinghua.cess.task.entity.dto.SubTaskResult;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kurt on 2014/9/21.
 */
public interface TaskQueryService {

    /**
     * for querying result of MAIN task
     *
     * @param uuid
     * @return
     */
    @Remote(value = "/task/query", method = RequestMethod.GET, paramProperties = {"taskId"})
    public SubTaskResult[] queryTaskResult(String uuid);

    /**
     * for batch querying result of SUB task
     *
     * @param subTaskId
     * @return
     */
    @Remote(value = "/task/subtask/query", method = RequestMethod.GET, paramProperties = {"subTaskId"})
    public SubTaskResult[] querySubTaskResult(Integer[] subTaskId);

    public SubTaskResultFile querySubTaskResultFile(Integer subTaskId, Integer resultFileId);

}
