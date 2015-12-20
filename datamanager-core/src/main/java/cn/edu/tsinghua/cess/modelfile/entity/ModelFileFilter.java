package cn.edu.tsinghua.cess.modelfile.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ModelFileFilter {
	
	private List<String> institute;
	private List<String> model;
	private List<String> experiment;
	private List<String> frequency;
	private List<String> modelingRealm;
	private List<String> ensembleMember;
	private List<String> variableName;
	private Map<String, String> temporalRange;
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
	public Map<String, String> getTemporalRange() {
		return temporalRange;
	}
	public void setTemporalRange(Map<String, String> temporalRange) {
		this.temporalRange = temporalRange;
	}
	
	/**
	 * for query purpose
	 * @param start
	 */
	public void setTemporalStart(String start) {
		if (start == null) {
			return;
		}
		this.ensureTemporalRangeMap().put("start", start);
	}
	
	/**
	 * for query purpose
	 * @param end
	 */
	public void setTemporalEnd(String end) {
		if (end == null) {
			return;
		}
		this.ensureTemporalRangeMap().put("end", end);
	}
	
	private Map<String, String> ensureTemporalRangeMap() {
		if (temporalRange == null) {
			temporalRange = new HashMap<String, String>();
		}
		return temporalRange;
	}
	@Override
	public String toString() {
		return "ModelFileFilter [institute=" + institute + ", model=" + model + ", experiment=" + experiment
				+ ", frequency=" + frequency + ", modelingRealm=" + modelingRealm + ", ensembleMember="
				+ ensembleMember + ", variableName=" + variableName + ", temporalRange=" + temporalRange + "]";
	}
	
}
