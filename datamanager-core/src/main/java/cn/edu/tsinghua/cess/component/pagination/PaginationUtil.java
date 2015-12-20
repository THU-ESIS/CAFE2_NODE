package cn.edu.tsinghua.cess.component.pagination;

import java.util.List;
import java.util.concurrent.Callable;

public class PaginationUtil {
	
	public static <T> PagedList<T> pagedQuery(Integer page, Integer pageSize, Callable<List<T>> command) {
		try {
			PaginationContext pc = PaginationContext.current();
			pc.setPage(page);
			pc.setPageSize(pageSize);
			
			List<T> data = command.call();
			
			PagedList<T> pagedList = new PagedList<T>();
			pagedList.setPage(page);
			pagedList.setPageSize(pageSize);
			pagedList.setRowCount(pc.getRowCount());
			pagedList.setList(data);
			
			return pagedList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			PaginationContext.clear();
		}
	}

}
