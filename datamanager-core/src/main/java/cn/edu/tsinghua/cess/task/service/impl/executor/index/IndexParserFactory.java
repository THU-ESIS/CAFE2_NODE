package cn.edu.tsinghua.cess.task.service.impl.executor.index;

import cn.edu.tsinghua.cess.modelfile.entity.Model;

public class IndexParserFactory {
	
	public static IndexParser getParser(Model model) {
		if (model.getFrequency().contains("mon")) {
			return new MonthlyIndexParser();
		} else {
			throw new IllegalArgumentException("only frequency of mon is supported");
		}
	}

}
