package cn.edu.tsinghua.cess.modelfile.dto;

import cn.edu.tsinghua.cess.component.pagination.PagedList;
import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFileFilter;

/**
 * Created by yanke on 8/20/15.
 */
public class ModelQueryResult {

    private PagedList<Model> modelList;
    private ModelFileFilter filter;

    public ModelQueryResult() {
    }

    public ModelQueryResult(PagedList<Model> modelList, ModelFileFilter filter) {
        this.modelList = modelList;
        this.filter = filter;
    }

    public void setModelList(PagedList<Model> modelList) {
        this.modelList = modelList;
    }

    public void setFilter(ModelFileFilter filter) {
        this.filter = filter;
    }

    public PagedList<Model> getModelList() {
        return modelList;
    }

    public ModelFileFilter getFilter() {
        return filter;
    }

}
