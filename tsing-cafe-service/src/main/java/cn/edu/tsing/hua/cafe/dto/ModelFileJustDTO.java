package cn.edu.tsing.hua.cafe.dto;

import java.util.Date;
import java.util.List;

/**
 * @author: snn
 * @created: 2019-01-26 00:33
 */
public class ModelFileJustDTO {

    private List<String> institute;

    private List<String> model;

    private List<String> experiment;

    private List<String> frequency;

    private List<String> modelingRealm;

    private List<String> ensembleMember;

    private List<String> variableName;

    private List<String> mipTable;

    private List<String> temporalStartYear;

    private List<String> temporalStartMonth;

    private List<String> temporalEndYear;

    private List<String> temporalEndMonth;

    private Date createTime;

    public List<String> getInstitute() {
        return institute;
    }

    public void setInstitute(List<String> institute) {
        this.institute = institute;
    }

    public List<String> getModel() {
        return model;
    }

    public void setModel(List<String> model) {
        this.model = model;
    }

    public List<String> getExperiment() {
        return experiment;
    }

    public void setExperiment(List<String> experiment) {
        this.experiment = experiment;
    }

    public List<String> getFrequency() {
        return frequency;
    }

    public void setFrequency(List<String> frequency) {
        this.frequency = frequency;
    }

    public List<String> getModelingRealm() {
        return modelingRealm;
    }

    public void setModelingRealm(List<String> modelingRealm) {
        this.modelingRealm = modelingRealm;
    }

    public List<String> getEnsembleMember() {
        return ensembleMember;
    }

    public void setEnsembleMember(List<String> ensembleMember) {
        this.ensembleMember = ensembleMember;
    }

    public List<String> getVariableName() {
        return variableName;
    }

    public void setVariableName(List<String> variableName) {
        this.variableName = variableName;
    }

    public List<String> getMipTable() {
        return mipTable;
    }

    public void setMipTable(List<String> mipTable) {
        this.mipTable = mipTable;
    }

    public List<String> getTemporalStartYear() {
        return temporalStartYear;
    }

    public void setTemporalStartYear(List<String> temporalStartYear) {
        this.temporalStartYear = temporalStartYear;
    }

    public List<String> getTemporalStartMonth() {
        return temporalStartMonth;
    }

    public void setTemporalStartMonth(List<String> temporalStartMonth) {
        this.temporalStartMonth = temporalStartMonth;
    }

    public List<String> getTemporalEndYear() {
        return temporalEndYear;
    }

    public void setTemporalEndYear(List<String> temporalEndYear) {
        this.temporalEndYear = temporalEndYear;
    }

    public List<String> getTemporalEndMonth() {
        return temporalEndMonth;
    }

    public void setTemporalEndMonth(List<String> temporalEndMonth) {
        this.temporalEndMonth = temporalEndMonth;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
