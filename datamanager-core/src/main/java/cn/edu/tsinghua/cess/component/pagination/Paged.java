package cn.edu.tsinghua.cess.component.pagination;

public abstract class Paged {
	
	protected Integer page;
	protected Integer pageSize;
	protected Integer rowCount;
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getRowCount() {
		return rowCount;
	}
	public void setRowCount(Integer count) {
		this.rowCount = count;
	}

}
