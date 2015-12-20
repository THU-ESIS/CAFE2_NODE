package cn.edu.tsinghua.cess.modelfile.entity;

/**
 * 
 * @author kurtyan777@gmail.com
 *
 */
public class ModelFile extends Model {
	
	private Integer id;
	private String fullPath;
	
	/* properties extracted from fileName */
	private String mipTable;
    private String temporalStartYear;
    private String temporalStartMonth;
    private String temporalStartDay;
    private String temporalStartHour;
    private String temporalStartMinute;
    private String temporalEndYear;
    private String temporalEndMonth;
    private String temporalEndDay;
    private String temporalEndHour;
    private String temporalEndMinute;
    private String geographicalInfo;

    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public String getMipTable() {
		return mipTable;
	}
	public void setMipTable(String mipTable) {
		this.mipTable = mipTable;
	}
    public String getGeographicalInfo() {
        return geographicalInfo;
    }
    public void setGeographicalInfo(String geographicalInfo) {
        this.geographicalInfo = geographicalInfo;
    }
	public String getTemporalStartYear() {
		return temporalStartYear;
	}
	public void setTemporalStartYear(String temporalStartYear) {
		this.temporalStartYear = temporalStartYear;
	}
	public String getTemporalStartMonth() {
		return temporalStartMonth;
	}
	public void setTemporalStartMonth(String temporalStartMonth) {
		this.temporalStartMonth = temporalStartMonth;
	}
	public String getTemporalStartDay() {
		return temporalStartDay;
	}
	public void setTemporalStartDay(String temporalStartDay) {
		this.temporalStartDay = temporalStartDay;
	}
	public String getTemporalStartHour() {
		return temporalStartHour;
	}
	public void setTemporalStartHour(String temporalStartHour) {
		this.temporalStartHour = temporalStartHour;
	}
	public String getTemporalStartMinute() {
		return temporalStartMinute;
	}
	public void setTemporalStartMinute(String temporalStartMinute) {
		this.temporalStartMinute = temporalStartMinute;
	}
	public String getTemporalEndYear() {
		return temporalEndYear;
	}
	public void setTemporalEndYear(String temporalEndYear) {
		this.temporalEndYear = temporalEndYear;
	}
	public String getTemporalEndMonth() {
		return temporalEndMonth;
	}
	public void setTemporalEndMonth(String temporalEndMonth) {
		this.temporalEndMonth = temporalEndMonth;
	}
	public String getTemporalEndDay() {
		return temporalEndDay;
	}
	public void setTemporalEndDay(String temporalEndDay) {
		this.temporalEndDay = temporalEndDay;
	}
	public String getTemporalEndHour() {
		return temporalEndHour;
	}
	public void setTemporalEndHour(String temporalEndHour) {
		this.temporalEndHour = temporalEndHour;
	}
	public String getTemporalEndMinute() {
		return temporalEndMinute;
	}
	public void setTemporalEndMinute(String temporalEndMinute) {
		this.temporalEndMinute = temporalEndMinute;
	}

}