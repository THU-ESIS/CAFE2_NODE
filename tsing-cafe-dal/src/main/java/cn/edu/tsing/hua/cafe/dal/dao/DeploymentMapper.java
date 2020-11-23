package cn.edu.tsing.hua.cafe.dal.dao;

import cn.edu.tsing.hua.cafe.dal.domain.Deployment;

public interface DeploymentMapper {
    int insert(Deployment record);

    int insertSelective(Deployment record);
}