package cn.edu.tsinghua.cess.deployment.dao;

import cn.edu.tsinghua.cess.deployment.entity.Deployment;

/**
 * Created by kurt on 2014/9/6.
 */
public interface DeploymentDao {

    public void insert(Deployment deployment);

    public Deployment query();

}
