package cn.edu.tsinghua.cess.modelfile.parser;

import cn.edu.tsinghua.cess.modelfile.entity.ModelFile;

public class TemporalSubsetParser {
	
	public void parseAndFill(String temporalSubset, ModelFile modelFile) {
		String[] splitted = temporalSubset.split("-");
		
		String temporalStart = splitted[0].trim();
		String temporalEnd = splitted[1].trim();
		
		TemporalInfo start = this.parse(temporalStart);
		TemporalInfo end = this.parse(temporalEnd);
		
		modelFile.setTemporalStartYear(start.year);
		modelFile.setTemporalStartMonth(start.month);
		modelFile.setTemporalStartDay(start.day);
		modelFile.setTemporalStartHour(start.hour);
		modelFile.setTemporalStartMinute(start.minute);
		
		modelFile.setTemporalEndYear(end.year);
		modelFile.setTemporalEndMonth(end.month);
		modelFile.setTemporalEndDay(end.day);
		modelFile.setTemporalEndHour(end.hour);
		modelFile.setTemporalEndMinute(end.minute);
	}
	
	private TemporalInfo parse(String temporalString) {
		TemporalInfo info = new TemporalInfo();
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < temporalString.length(); i++) {
			char c = temporalString.charAt(i);
			builder.append(c);
			
			if (i == 3) {
				info.year = builder.toString();
				builder = new StringBuilder();
			} else if (i == 5) {
				info.month = builder.toString();
				builder = new StringBuilder();
			} else if (i == 7) {
				info.day = builder.toString();
				builder = new StringBuilder();
			} else if (i == 9) {
				info.hour = builder.toString();
				builder = new StringBuilder();
			} else if (i == 11) {
				info.minute = builder.toString();
				builder = new StringBuilder();
			}
		}
		
		return info;
	}
	
	
	class TemporalInfo {
		String year;
		String month;
		String day;
		String hour;
		String minute;
	}

}
