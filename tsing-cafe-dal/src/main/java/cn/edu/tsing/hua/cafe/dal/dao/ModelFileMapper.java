package cn.edu.tsing.hua.cafe.dal.dao;

import cn.edu.tsing.hua.cafe.dal.domain.ModelFile;

import java.util.List;
import java.util.Map;

public interface ModelFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ModelFile record);

    int insertSelective(ModelFile record);

    ModelFile selectByPrimaryKey(Integer id);

    List<ModelFile> listModelFile(ModelFile modelFile);

    List<ModelFile> listModelFileMulti(Map map);

    int updateByPrimaryKeySelective(ModelFile record);

    int updateByPrimaryKey(ModelFile record);
}