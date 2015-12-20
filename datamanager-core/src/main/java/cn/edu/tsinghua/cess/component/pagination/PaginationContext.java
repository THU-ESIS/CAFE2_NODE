package cn.edu.tsinghua.cess.component.pagination;

public class PaginationContext extends Paged {
	
	private static ThreadLocal<PaginationContext> local = new ThreadLocal<PaginationContext>() {
		protected PaginationContext initialValue() {
			return new PaginationContext();
		};
	};
	
	private boolean paged = false;
	public boolean isPaged() {
		return paged;
	}
	
	@Override
	public void setPage(Integer page) {
		if (page == null) {
			throw new IllegalArgumentException("page must not be null");
		}
		super.setPage(page);
		this.paged = true;
	}
	
	@Override
	public void setPageSize(Integer pageSize) {
		if (pageSize == null) {
			throw new IllegalArgumentException("pageSize must not be null");
		}
		super.setPageSize(pageSize);
		this.paged = true;
	}

	
	public static PaginationContext current() {
		return local.get();
	}
	
	public static void clear() {
		PaginationContext current = current();
		
		current.paged = false;
		current.page = null;
		current.pageSize = null;
	}
	
}
