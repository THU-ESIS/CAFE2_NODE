package cn.edu.tsinghua.cess.workernode.dao;

import java.util.List;

import cn.edu.tsinghua.cess.workernode.entity.WorkerNode;

public interface WorkerNodeDao {
	
	public Integer insert(WorkerNode node);
	
	public void remove(Integer id);
	
	public WorkerNode query(Integer id);
	
	public List<WorkerNode> listAll();

}
