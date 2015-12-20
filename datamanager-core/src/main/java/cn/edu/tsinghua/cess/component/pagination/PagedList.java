package cn.edu.tsinghua.cess.component.pagination;

import java.util.Iterator;
import java.util.List;

public class PagedList<T> extends Paged implements Iterable<T> {
	
	private List<T> list;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

}
