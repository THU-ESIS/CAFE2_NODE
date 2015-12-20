package cn.edu.tsinghua.cess.workernode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.tsinghua.cess.modelfile.service.ModelFileManageService;
import cn.edu.tsinghua.cess.workernode.dao.WorkerNodeDao;
import cn.edu.tsinghua.cess.workernode.entity.WorkerNode;
import cn.edu.tsinghua.cess.workernode.service.WorkerNodeManageService;

@Component("localWorkerNodeManageService")
public class WorkerNodeManageServiceImpl implements WorkerNodeManageService {
	
	@Autowired WorkerNodeDao workerNodeDao;
	@Autowired ModelFileManageService modelFileManageService;

	@Override
	public Integer register(WorkerNode node) {
		workerNodeDao.insert(node);
		return node.getId();
	}

	@Override
	public void deregister(WorkerNode node) {
		WorkerNode persistedNode = workerNodeDao.query(node.getId());

		// verify the request comes from the workerNode
		if (!persistedNode.getIp().equals(node.getAddress())) {
			throw new IllegalArgumentException();
		}

		modelFileManageService.deleteModelFileOfNode(node.getId());
		workerNodeDao.remove(node.getId());
	}

	@Override
	public WorkerNode queryById(Integer nodeId) {
		return workerNodeDao.query(nodeId);
	}

	@Override
	public List<WorkerNode> listAll() {
		return workerNodeDao.listAll();
	}

}
