package cn.edu.tsinghua.cess.workernode.dao;

import cn.edu.tsinghua.cess.workernode.entity.WorkerNode;

import java.util.List;

public interface WorkerNodeDao {

    public Integer insert(WorkerNode node);

    public void remove(Integer id);

    public WorkerNode query(Integer id);

    public List<WorkerNode> listAll();

}
