package cn.edu.tsing.hua.cafe.dal.domain;

import java.util.Date;

public class ModelFile {
    private Integer id;

    private Integer nodeId;

    private String fullPath;

    private String institute;

    private String model;

    private String experiment;

    private String frequency;

    private String modelingRealm;

    private String ensembleMember;

    private String variableName;

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

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath == null ? null : fullPath.trim();
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute == null ? null : institute.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getExperiment() {
        return experiment;
    }

    public void setExperiment(String experiment) {
        this.experiment = experiment == null ? null : experiment.trim();
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency == null ? null : frequency.trim();
    }

    public String getModelingRealm() {
        return modelingRealm;
    }

    public void setModelingRealm(String modelingRealm) {
        this.modelingRealm = modelingRealm == null ? null : modelingRealm.trim();
    }

    public String getEnsembleMember() {
        return ensembleMember;
    }

    public void setEnsembleMember(String ensembleMember) {
        this.ensembleMember = ensembleMember == null ? null : ensembleMember.trim();
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName == null ? null : variableName.trim();
    }

    public String getMipTable() {
        return mipTable;
    }

    public void setMipTable(String mipTable) {
        this.mipTable = mipTable == null ? null : mipTable.trim();
    }

    public String getTemporalStartYear() {
        return temporalStartYear;
    }

    public void setTemporalStartYear(String temporalStartYear) {
        this.temporalStartYear = temporalStartYear == null ? null : temporalStartYear.trim();
    }

    public String getTemporalStartMonth() {
        return temporalStartMonth;
    }

    public void setTemporalStartMonth(String temporalStartMonth) {
        this.temporalStartMonth = temporalStartMonth == null ? null : temporalStartMonth.trim();
    }

    public String getTemporalStartDay() {
        return temporalStartDay;
    }

    public void setTemporalStartDay(String temporalStartDay) {
        this.temporalStartDay = temporalStartDay == null ? null : temporalStartDay.trim();
    }

    public String getTemporalStartHour() {
        return temporalStartHour;
    }

    public void setTemporalStartHour(String temporalStartHour) {
        this.temporalStartHour = temporalStartHour == null ? null : temporalStartHour.trim();
    }

    public String getTemporalStartMinute() {
        return temporalStartMinute;
    }

    public void setTemporalStartMinute(String temporalStartMinute) {
        this.temporalStartMinute = temporalStartMinute == null ? null : temporalStartMinute.trim();
    }

    public String getTemporalEndYear() {
        return temporalEndYear;
    }

    public void setTemporalEndYear(String temporalEndYear) {
        this.temporalEndYear = temporalEndYear == null ? null : temporalEndYear.trim();
    }

    public String getTemporalEndMonth() {
        return temporalEndMonth;
    }

    public void setTemporalEndMonth(String temporalEndMonth) {
        this.temporalEndMonth = temporalEndMonth == null ? null : temporalEndMonth.trim();
    }

    public String getTemporalEndDay() {
        return temporalEndDay;
    }

    public void setTemporalEndDay(String temporalEndDay) {
        this.temporalEndDay = temporalEndDay == null ? null : temporalEndDay.trim();
    }

    public String getTemporalEndHour() {
        return temporalEndHour;
    }

    public void setTemporalEndHour(String temporalEndHour) {
        this.temporalEndHour = temporalEndHour == null ? null : temporalEndHour.trim();
    }

    public String getTemporalEndMinute() {
        return temporalEndMinute;
    }

    public void setTemporalEndMinute(String temporalEndMinute) {
        this.temporalEndMinute = temporalEndMinute == null ? null : temporalEndMinute.trim();
    }

    public String getGeographicalInfo() {
        return geographicalInfo;
    }

    public void setGeographicalInfo(String geographicalInfo) {
        this.geographicalInfo = geographicalInfo == null ? null : geographicalInfo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}