package cn.edu.tsinghua.cess.modelfile.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFile;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFileFilter;
import cn.edu.tsinghua.cess.workernode.entity.WorkerNode;

public interface ModelFileDao {

	/**
	 * given a list of models, query nodes on which these models exist
	 * @param modelList
	 * @return
	 */
	public List<WorkerNode> queryRelatedNodes(@Param("modelList") List<Model> modelList);

	public void insertModelFileList(List<ModelFile> modelFile);
	
	public void deleteModelFileOfNode(Integer nodeId);

	/**
	 * query model by filter
	 * produces result for api layer
	 * @param filter
	 * @return
	 */
	public List<Model> queryModel(@Param("filter") ModelFileFilter filter);

	/**
	 * query distinct model
	 * if same model exist on multiple nodes, only one of them will be counted
	 * @param filter
	 * @return
	 */
	public List<Model> queryDistinctModel(@Param("filter") ModelFileFilter filter);

	public List<Model> queryModelOfNode(@Param("modelList") List<Model> modelList, @Param("nodeId") Integer nodeId);

	/**
	 * given a list of models, query which of them exist on local node
	 * @param modelList
	 * @return
	 */
    public List<Model> queryModelOfLocal(@Param("modelList") List<Model> modelList);

	public List<String> listFieldCandidate(@Param("field") String field, @Param("filter") ModelFileFilter filter);
	
	public String listTemporalRangeByFilter(@Param("isUpper") boolean isUpper, @Param("filter") ModelFileFilter filter);
	
	public String listTemporalRangeByModel(@Param("isUpper") boolean isUpper, @Param("model") Model model);

	/**
	 * given a model, query the local file system path of related model file
	 * @param model
	 * @return
	 */
	public List<String> queryModelFileOfModel(@Param("model") Model model);

}
