package cn.edu.tsinghua.cess.task.dao;

import cn.edu.tsinghua.cess.task.entity.SubTaskStatus;
import org.apache.ibatis.annotations.Param;

/**
 * Created by kurt on 2014/9/16.
 */
public interface TaskExecutionDao {

    public void updateProgress(@Param("id") Integer id, @Param("progress") Integer progress, @Param("description") String description);

    public void setStatus(@Param("id") Integer id, @Param("status") SubTaskStatus status, @Param("description") String description);

    public void setFailed(@Param("id") Integer id, @Param("failureCause") String failureCause);

    public void addResultFile(@Param("id") Integer id, @Param("type") String type, @Param("path") String path);

}
