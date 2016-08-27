package cn.edu.tsinghua.cess.modelfile.service;

import cn.edu.tsinghua.cess.component.remote.Remote;
import cn.edu.tsinghua.cess.modelfile.dto.ModelNodeRelation;
import cn.edu.tsinghua.cess.modelfile.dto.ModelQueryParam;
import cn.edu.tsinghua.cess.modelfile.dto.ModelQueryResult;
import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFileFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


public interface ModelFileQueryService {

    /**
     * list all possible values of each of following properties:
     * - institute
     * - model
     * - experiment
     * - frequency
     * - modeling realm
     * - ensemble member
     * - variable name
     *
     * @return
     */
	@Remote("/modelfile/query/filter")
	public ModelFileFilter listFilter();

	public Model queryModelWithTemporal(Model model);
	
	public List<String> queryModelFile(Model model);

	@Remote("/modelfile/query_related_workernode")
	public ModelNodeRelation[] queryRelatedNodes(Model[] modelList);

	@Remote(value = "/modelfile/query_by_dto", method = RequestMethod.POST)
	public ModelQueryResult queryModel(ModelQueryParam param);

}