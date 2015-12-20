package cn.edu.tsinghua.cess.task.service.impl.executor.index;

public class MonthlyIndexParser implements IndexParser {

	@Override
	public Index parse(TemporalRange range, TemporalRange specified) {
		Month rangeStart = parse(range.getStart());
		Month rangeEnd = parse(range.getEnd());
		Month specifiedStart = parse(specified.getStart());
		Month specifiedEnd = parse(specified.getEnd());
		
		
		if (specifiedStart.isGreaterThan(rangeEnd)) {
			throw new IllegalArgumentException("specified temporalStart is greater than temporalRange of the model");
		}
		
		Index index = new Index();
		
		if (rangeStart.isGreaterThan(specifiedStart)) {
			index.setBeginIndex(0);
		} else {
			index.setBeginIndex(rangeStart.indexOf(specifiedStart));
		}
		
		if (specifiedEnd.isGreaterThan(rangeEnd)) {
			index.setEndIndex(rangeStart.indexOf(rangeEnd));
		} else {
			index.setEndIndex(rangeStart.indexOf(specifiedEnd));
		}
		
		return index;
	}
	
	private Month parse(String dateTime) {
		Month m = new Month();
		m.year = Integer.parseInt(dateTime.substring(0, 4));
		m.month = Integer.parseInt(dateTime.substring(4, 6));
		
		return m;
	}
	
	private class Month {
		int year;
		int month;
		
		int indexOf(Month specified) {
			if (specified.year < year) {
				return 0;
			} else if (specified.year == year && specified.month < month) {
				return 0;
			}
			
			int years = specified.year - year;
			if (specified.month < month) {
				return (years - 1) * 12 + (month - specified.month);
			} else {
				return years * 12 + (specified.month - month);
			}
		}
		
		boolean isGreaterThan(Month m) {
			if (year > m.year) {
				return true;
			}
			if (year == m.year && month > m.month) {
				return true;
			}
			
			return false;
		}
	}

    public static void main(String[] args) {
        TemporalRange range = new TemporalRange("201401", "201412");
        TemporalRange specified = new TemporalRange("201302", "201511");

        MonthlyIndexParser parser = new MonthlyIndexParser();
        Index index = parser.parse(range, specified);

        System.out.println(index);
    }
	
}
