package cn.edu.tsinghua.cess.modelfile.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFile;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFileFilter;
import cn.edu.tsinghua.cess.workernode.entity.WorkerNode;

public interface ModelFileDao {
	
	public List<WorkerNode> queryRelatedNodes(@Param("modelList") List<Model> modelList);
	
	public void insertModelFileList(List<ModelFile> modelFile);
	
	public void deleteModelFileOfNode(Integer nodeId);
	
	public List<Model> queryModel(@Param("filter") ModelFileFilter filter);

    public List<Model> queryModelOfNode(@Param("modelList") List<Model> modelList, @Param("nodeId") Integer nodeId);

    public List<Model> queryModelOfLocal(@Param("modelList") List<Model> modelList);

	public List<String> listFieldCandidate(@Param("field") String field, @Param("filter") ModelFileFilter filter);
	
	public String listTemporalRangeByFilter(@Param("isUpper") boolean isUpper, @Param("filter") ModelFileFilter filter);
	
	public String listTemporalRangeByModel(@Param("isUpper") boolean isUpper, @Param("model") Model model);
	
	public List<String> queryModelFileOfModel(@Param("model") Model model);

}
