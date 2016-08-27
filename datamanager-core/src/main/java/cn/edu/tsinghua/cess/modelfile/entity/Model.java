package cn.edu.tsinghua.cess.modelfile.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Model {

    /* properties to be set while being persisted */
    private Integer nodeId;
    private String nodeName;
	
	/* properties extracted from filePath */
	private String institute;
	private String model;
	private String experiment;
	private String frequency;
	private String modelingRealm;
	private String ensembleMember;
	private String variableName;

	private String nullToEmpty(String str) {
		return str == null ? "" : str;
	}

	@Override
	public int hashCode() {
		return
				nullToEmpty(institute).hashCode()
				+ nullToEmpty(model).hashCode()
				+ nullToEmpty(experiment).hashCode()
				+ nullToEmpty(frequency).hashCode()
				+ nullToEmpty(modelingRealm).hashCode()
				+ nullToEmpty(ensembleMember).hashCode()
				+ nullToEmpty(variableName).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
	    if (obj instanceof Model) {
	    	return nullToEmpty(institute).equals(nullToEmpty(((Model) obj).institute))
					&& nullToEmpty(model).equals(nullToEmpty(((Model) obj).model))
					&& nullToEmpty(experiment).equals(nullToEmpty(((Model) obj).experiment))
					&& nullToEmpty(frequency).equals(nullToEmpty(((Model) obj).frequency))
					&& nullToEmpty(modelingRealm).equals(nullToEmpty(((Model) obj).modelingRealm))
					&& nullToEmpty(ensembleMember).equals(((Model) obj).ensembleMember)
					&& nullToEmpty(variableName).equals(nullToEmpty(((Model) obj).variableName));
		}

		return false;
	}

	/* concatenated temporal fields */
	private String temporalStart;
	private String temporalEnd;

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
			", institute='" + institute + '\'' +
			", model='" + model + '\'' +
			", experiment='" + experiment + '\'' +
			", frequency='" + frequency + '\'' +
			", modelingRealm='" + modelingRealm + '\'' +
			", ensembleMember='" + ensembleMember + '\'' +
			", variableName='" + variableName + '\'' +
			", temporalStart='" + temporalStart + '\'' +
			", temporalEnd='" + temporalEnd + '\'' +
			'}';
	}

}