package cn.edu.tsinghua.cess.task.service.impl.executor.index;


public interface IndexParser {
	
	public Index parse(TemporalRange range, TemporalRange specified);

}
