package cn.edu.tsinghua.cess.deployment.service;

import cn.edu.tsinghua.cess.deployment.entity.Deployment;

/**
 * Created by kurt on 2014/9/6.
 */
public interface DeploymentService {

    public void deploy(Deployment deployment);
    
    public Deployment get();

}
