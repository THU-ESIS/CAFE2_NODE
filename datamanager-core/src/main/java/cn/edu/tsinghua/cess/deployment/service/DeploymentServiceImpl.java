package cn.edu.tsinghua.cess.deployment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.tsinghua.cess.component.remote.RemoteServiceFactory;
import cn.edu.tsinghua.cess.deployment.dao.DeploymentDao;
import cn.edu.tsinghua.cess.deployment.entity.Deployment;
import cn.edu.tsinghua.cess.workernode.entity.WorkerNode;
import cn.edu.tsinghua.cess.workernode.service.WorkerNodeManageService;

@Component
public class DeploymentServiceImpl implements DeploymentService{

    @Autowired DeploymentDao deploymentDao;
    @Autowired RemoteServiceFactory remoteServiceFactory;
    
    private Deployment inst;

    @Override
    public void deploy(Deployment deployment) {
    	synchronized (this) {
    		inst = null;
		}
    	
        switch (deployment.getMode()) {
            case DISTRIBUTED_CENTRAL:
            case LOCAL:
                deploymentDao.insert(deployment);
                break;
            case DISTRIBUTED_WORKER:
                WorkerNode workerNode = WorkerNode.fromDeployment(deployment);
                Integer nodeId = remoteServiceFactory.getRemoteService(deployment.getCentralServer(), WorkerNodeManageService.class).register(workerNode);
                
                deployment.setNodeId(nodeId);
                deploymentDao.insert(deployment);
                
                break;
        }
    }

	@Override
	public Deployment get() {
		if (inst == null) {
			synchronized (this) {
				if (inst == null) {
					inst = deploymentDao.query();
					if (inst == null) {
                        throw new NotYetDeployedException();
					}
				}
			}
		}
		
		return inst;
	}
	
}
