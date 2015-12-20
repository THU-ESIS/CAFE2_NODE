package cn.edu.tsinghua.cess.task.entity;

public class ScriptArgument {
	
	private String name;
    private String temporalStart;
    private String temporalEnd;
	private Integer latMin;
	private Integer latMax;
	private Integer lonMin;
	private Integer lonMax;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    public String getTemporalStart() {
        return temporalStart;
    }
    public void setTemporalStart(String temporalStart) {
        this.temporalStart = temporalStart;
    }
    public String getTemporalEnd() {
        return temporalEnd;
    }
    public void setTemporalEnd(String temporalEnd) {
        this.temporalEnd = temporalEnd;
    }
    public Integer getLatMin() {
		return latMin;
	}
	public void setLatMin(Integer latMin) {
		this.latMin = latMin;
	}
	public Integer getLatMax() {
		return latMax;
	}
	public void setLatMax(Integer latMax) {
		this.latMax = latMax;
	}
	public Integer getLonMin() {
		return lonMin;
	}
	public void setLonMin(Integer lonMin) {
		this.lonMin = lonMin;
	}
	public Integer getLonMax() {
		return lonMax;
	}
	public void setLonMax(Integer lonMax) {
		this.lonMax = lonMax;
	}


	@Override
	public String toString() {
		return "ScriptArgument{" +
			"name='" + name + '\'' +
			", temporalStart='" + temporalStart + '\'' +
			", temporalEnd='" + temporalEnd + '\'' +
			", latMin=" + latMin +
			", latMax=" + latMax +
			", lonMin=" + lonMin +
			", lonMax=" + lonMax +
			'}';
	}

}
