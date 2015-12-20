package cn.edu.tsinghua.cess.task.service.impl.executor.index;

public class TemporalRange {
	
	private String start;
	private String end;
	
	public TemporalRange(String start, String end) {
		this.start = start;
		this.end = end;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}

}
