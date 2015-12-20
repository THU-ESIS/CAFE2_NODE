package cn.edu.tsinghua.cess.modelfile.dto;

import cn.edu.tsinghua.cess.modelfile.entity.ModelFileFilter;

/**
 * Created by yanke on 8/20/15.
 */
public class ModelQueryParam {

    private ModelFileFilter filter;
    private int page;
    private int pageSize;

    public ModelQueryParam() {
    }

    public ModelQueryParam(ModelFileFilter filter, int page, int pageSize) {
        this.filter = filter;
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public ModelFileFilter getFilter() {
        return filter;
    }

    public void setFilter(ModelFileFilter filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return "ModelQueryParam{" +
            "filter=" + filter +
            ", page=" + page +
            ", pageSize=" + pageSize +
            '}';
    }

}
