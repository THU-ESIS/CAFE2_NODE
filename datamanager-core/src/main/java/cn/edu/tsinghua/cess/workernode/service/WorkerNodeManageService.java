package cn.edu.tsinghua.cess.workernode.service;

import java.util.List;

import cn.edu.tsinghua.cess.component.remote.Remote;
import cn.edu.tsinghua.cess.workernode.entity.WorkerNode;
import org.springframework.web.bind.annotation.RequestMethod;

public interface WorkerNodeManageService {
	
	/**
	 * 
	 * @param node
	 * @return
	 */
    @Remote("/workernode/register")
	public Integer register(WorkerNode node);
	
	/**
	 * this method will be called remotely.
	 * besides the to-be-deregistered nodeId, the caller of this method should also provide the remote caller's ip
	 * to verify the validation of the requester
	 */
    @Remote("/workernode/deregister")
	public void deregister(WorkerNode node);

	@Remote(value = "/workernode/query_by_id", method = RequestMethod.GET, paramProperties = "nodeId")
	public WorkerNode queryById(Integer nodeId);
    
    public List<WorkerNode> listAll();

}
