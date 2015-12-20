package cn.edu.tsinghua.cess.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cn.edu.tsinghua.cess.deployment.entity.Deployment;
import cn.edu.tsinghua.cess.deployment.service.DeploymentService;
import cn.edu.tsinghua.cess.task.service.TaskSubmitionService;

@Component
public class TaskSubmitionServiceFactory {

    @Autowired
    @Qualifier("localSubmitionService")
    TaskSubmitionService localSubmitionService;

    @Autowired
    @Qualifier("workerNodeSubmitionService")
    TaskSubmitionService workerNodeSubmitionService;

    @Autowired
    DeploymentService deploymentService;

    public TaskSubmitionService getService() {
        Deployment deployment = deploymentService.get();

        switch (deployment.getMode()) {
            case DISTRIBUTED_WORKER:
                return workerNodeSubmitionService;
            case LOCAL:
                return localSubmitionService;
            case DISTRIBUTED_CENTRAL:
                throw new UnsupportedOperationException();
            default:
                throw new IllegalArgumentException();
        }
    }

}
