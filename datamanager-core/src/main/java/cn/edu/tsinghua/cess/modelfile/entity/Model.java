package cn.edu.tsinghua.cess.modelfile.entity;

import cn.edu.tsinghua.cess.task.entity.ModelProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Model implements ModelProvider {

    /* properties to be set while being persisted */
    private Integer nodeId;
    private String nodeName;

    /* properties extracted from filePath */
    private String mipEra;
    private String activity;
    private String institute;
    private String model;
    private String experiment;
    private String frequency;
    private String modelingRealm;
    private String ensembleMember;
    private String variableName;
    private String mipTable;
    private String datasetVersion;
    private String gridLabel;
    /* concatenated temporal fields */
    private String temporalStart;
    private String temporalEnd;

    private String nullToEmpty(String str) {
        return str == null ? "" : str;
    }

    @Override
    public int hashCode() {
        return
                nullToEmpty(mipEra).hashCode()
                        + nullToEmpty(activity).hashCode()
                        + nullToEmpty(institute).hashCode()
                        + nullToEmpty(model).hashCode()
                        + nullToEmpty(experiment).hashCode()
                        + nullToEmpty(frequency).hashCode()
                        + nullToEmpty(modelingRealm).hashCode()
                        + nullToEmpty(ensembleMember).hashCode()
                        + nullToEmpty(variableName).hashCode()
                        + nullToEmpty(mipTable).hashCode()
                        + nullToEmpty(datasetVersion).hashCode()
                        + nullToEmpty(gridLabel).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Model) {
            return nullToEmpty(mipEra).equals(nullToEmpty(((Model) obj).mipEra))
                    && nullToEmpty(activity).equals(nullToEmpty(((Model) obj).activity))
                    && nullToEmpty(institute).equals(nullToEmpty(((Model) obj).institute))
                    && nullToEmpty(model).equals(nullToEmpty(((Model) obj).model))
                    && nullToEmpty(experiment).equals(nullToEmpty(((Model) obj).experiment))
                    && nullToEmpty(frequency).equals(nullToEmpty(((Model) obj).frequency))
                    && nullToEmpty(modelingRealm).equals(nullToEmpty(((Model) obj).modelingRealm))
                    && nullToEmpty(ensembleMember).equals(((Model) obj).ensembleMember)
                    && nullToEmpty(variableName).equals(nullToEmpty(((Model) obj).variableName))
                    && nullToEmpty(mipTable).equals(nullToEmpty(((Model) obj).mipTable))
                    && nullToEmpty(datasetVersion).equals(nullToEmpty(((Model) obj).datasetVersion))
                    && nullToEmpty(gridLabel).equals(nullToEmpty(((Model) obj).gridLabel));
        }

        return false;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getMipEra() {
        return mipEra;
    }

    public void setMipEra(String mipEra) {
        this.mipEra = mipEra;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getExperiment() {
        return experiment;
    }

    public void setExperiment(String experiment) {
        this.experiment = experiment;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getModelingRealm() {
        return modelingRealm;
    }

    public void setModelingRealm(String modelingRealm) {
        this.modelingRealm = modelingRealm;
    }

    public String getEnsembleMember() {
        return ensembleMember;
    }

    public void setEnsembleMember(String ensembleMember) {
        this.ensembleMember = ensembleMember;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getMipTable() {
        return mipTable;
    }

    public void setMipTable(String mipTable) {
        this.mipTable = mipTable;
    }

    public String getDatasetVersion() {
        return datasetVersion;
    }

    public void setDatasetVersion(String datasetVersion) {
        this.datasetVersion = datasetVersion;
    }

    public String getGridLabel() {
        return gridLabel;
    }

    public void setGridLabel(String gridLabel) {
        this.gridLabel = gridLabel;
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


    @Override
    public String toString() {
        return "Model{" +
                "nodeId=" + nodeId +
                ", nodeName='" + nodeName + '\'' +
                ", mipEra='" + mipEra + '\'' +
                ", activity='" + activity + '\'' +
                ", institute='" + institute + '\'' +
                ", model='" + model + '\'' +
                ", experiment='" + experiment + '\'' +
                ", frequency='" + frequency + '\'' +
                ", modelingRealm='" + modelingRealm + '\'' +
                ", ensembleMember='" + ensembleMember + '\'' +
                ", variableName='" + variableName + '\'' +
                ", mipTable='" + mipTable + '\'' +
                ", datasetVersion='" + datasetVersion + '\'' +
                ", gridLabel='" + gridLabel + '\'' +
                ", temporalStart='" + temporalStart + '\'' +
                ", temporalEnd='" + temporalEnd + '\'' +
                '}';
    }

    @JsonIgnore
    @Override
    public Model getModelEntity() {
        return this;
    }

}