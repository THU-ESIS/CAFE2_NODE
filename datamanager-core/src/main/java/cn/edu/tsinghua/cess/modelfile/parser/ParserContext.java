package cn.edu.tsinghua.cess.modelfile.parser;

import java.util.Stack;

import cn.edu.tsinghua.cess.modelfile.entity.Model;

// parsecontext: store the directorystack, retrieve directory stack information, check parse condition, logging
class ParserContext {
	
	/** below 4 properties are presented here for logging purpose */
	private int matchedFileCount;
	private int traversedFileCount;
	private int matchedDirectoryCount;
	private int traversedDirectoryCount;
	
	private Stack<String> directoryStack = new Stack<String>();
	private ModelFileHandler modelFileHandler;
	
	public ParserContext(ModelFileHandler modelFileHandler) {
		this.modelFileHandler = modelFileHandler;
	}
	
	public void push(String directory) {
		directoryStack.push(directory);
	}
	
	public String pop() {
		return directoryStack.pop();
	}

	public Stack<String> getDirectoryStack() {
		return directoryStack;
	}

	// this is to be called in FileParser to fill properties.
	public void fillPathProperties(Model modelEntity) {
		// the variables to parse from path, default values are N/A
		String datasourceType = "N/A";
		String institute = "N/A";
		String model = "N/A";
		String experiment = "N/A";
		String frequency = "N/A";
		String modelingRealm = "N/A";
		String mipTable = "N/A";
		String variableName = "N/A";
		String datasetVersion = "N/A";
		String ensembleMember = "N/A";
		// 3 data directory pattern: CMIP5/CMIP6/observation
		switch (directoryStack.get(1)) {
			case "cmip5_data":
				/* dirtectory stack example:
				/data/cmip5_data/CMIP5/output/NASA-GISS/GISS-E2-H/historical/mon/seaIce/sic/r1i1p1/sic_OImon_GISS-E2-H_historical_r1i1p1_185001-200512.nc
				**/
				// get new values that are not N/A
				datasourceType = directoryStack.get(2);
				institute = directoryStack.get(4);
				model = directoryStack.get(5);
				experiment = directoryStack.get(6);
				frequency = directoryStack.get(7);
				modelingRealm = directoryStack.get(8);
				variableName = directoryStack.get(9);
				ensembleMember = directoryStack.get(10);
				break;
			case "cmip6_data":
				/* example:
				/data/cmip6_data/CMIP6/CMIP/THU/CIESM/historical/r1i1p1f1/LImon/snc/gr/v20200322/snc_LImon_CIESM_historical_r1i1p1f1_gr_200001-201412.nc
				**/
				// get new values that are not N/A
				datasourceType = directoryStack.get(2);
				institute = directoryStack.get(4);
				model = directoryStack.get(5);
				experiment = directoryStack.get(6);
				ensembleMember = directoryStack.get(7);
				mipTable = directoryStack.get(8);
				variableName = directoryStack.get(9);
				datasetVersion = directoryStack.get(11);
				/* the old version of model file query/filter is based on cmip5, the modelling realm and frequency are seperated
				but in cmip6 the 2 fields are combined into miptable in directory name. so here for simplicity we copy miptable
				value to both frequency and modelingrealm.
				*/
				frequency = mipTable;
				modelingRealm = mipTable;
				break;
			case "observation_data":
				/* example:
				/data/observation_data/OBS/NCEI/AVHRR/observation/mon/ocean/tos/tos_Omon_AVHRR_observation_Global_198201-201412.nc
				**/
				// get new values that are not N/A
				datasourceType = directoryStack.get(2);
				institute = directoryStack.get(3);
				model = directoryStack.get(4);
				experiment = directoryStack.get(5);
				frequency = directoryStack.get(6);
				modelingRealm = directoryStack.get(7);
				variableName = directoryStack.get(8);
				break;
			default:
				break;
			}
		// set these values
		modelEntity.setDatasourceType(datasourceType);
		modelEntity.setInstitute(institute);
		modelEntity.setModel(model);
		modelEntity.setExperiment(experiment);
		modelEntity.setFrequency(frequency);
		modelEntity.setModelingRealm(modelingRealm);
		modelEntity.setVariableName(variableName);
		modelEntity.setDatasetVersion(datasetVersion);
		modelEntity.setEnsembleMember(ensembleMember);
	}
	
	
	/**
	 * 
	 * @return return true if current file's directory confroms with cmip5/6 DRS or pre-defined observation folder pattern.
	 */
	public boolean conformWithPattern() {
		boolean conformWithPattern = false;
		if (directoryStack.size() >1) {
			switch (directoryStack.get(1)) {
				case "cmip5_data":
					if ( directoryStack.size() == 11) {
						conformWithPattern = true;
					}
					break;
				case "cmip6_data":
					if ( directoryStack.size() == 12) {
						conformWithPattern = true;
					}
					break;
				case "observation_data":
					if ( directoryStack.size() == 9) {
						conformWithPattern = true;
					}
					break;
				default:
					break;
			}
		}
		return conformWithPattern;
	}
	
	/**
	 * @return return true if current file's directory stack length is smaller than defined pattern. this means directoryparser need go to subfolders
	 */
	public boolean continueDrillingIn() {
		boolean continueDrillingIn = false;
		// the first level does not have directoryStack.get(1), therefore continueDrilling should be set to true
		if (directoryStack.size() <= 1) {
			continueDrillingIn = true;
		}
		else {
			switch (directoryStack.get(1)) {
				case "cmip5_data":
					if (directoryStack.size() < 11) {
						continueDrillingIn = true;
					}
					break;
				case "cmip6_data":
					if (directoryStack.size() < 12) {
						continueDrillingIn = true;
					}
					break;
				case "observation_data":
					if (directoryStack.size() < 9) {
						continueDrillingIn = true;
					}
					break;
				default:
					break;
			}
		}
		return continueDrillingIn;
	}

	public ModelFileHandler getModelFileHandler() {
		return modelFileHandler;
	}

	public void setModelFileHandler(ModelFileHandler modelFileHandler) {
		this.modelFileHandler = modelFileHandler;
	}

	public int getMatchedFileCount() {
		return matchedFileCount;
	}

	public int getTraversedFileCount() {
		return traversedFileCount;
	}

	public int getMatchedDirectoryCount() {
		return matchedDirectoryCount;
	}

	public int getTraversedDirectoryCount() {
		return traversedDirectoryCount;
	}
	
	public void increaseMatchedFileCount() {
		matchedFileCount++;
	}

	public void increaseTraversedFileCount() {
		traversedFileCount++;
	}

	public void increaseMatchedDirectoryCount() {
		matchedDirectoryCount++;
	}

	public void increaseTraversedDirectoryCount() {
		traversedDirectoryCount++;
	}

}
