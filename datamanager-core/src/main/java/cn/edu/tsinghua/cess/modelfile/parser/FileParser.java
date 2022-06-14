package cn.edu.tsinghua.cess.modelfile.parser;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;
import org.apache.log4j.Logger;

import cn.edu.tsinghua.cess.modelfile.entity.ModelFile;

class FileParser {

	private static Logger log = Logger.getLogger(FileParser.class);
	private TemporalSubsetParser temporalSubsetParser = new TemporalSubsetParser();

	public void parseFile(File file, ParserContext context) {
		/*
		 * make this method exception-free by wrapping the innerParseFile() method with try-catch block
		 */
		try {
			// for logging	
			context.increaseTraversedFileCount();
			this.internalParseFile(file, context);
		} catch (Throwable e) {
			log.error("error parsing file=" + file.getAbsolutePath(), e);
		}
	}

	private void internalParseFile(File file, ParserContext context) {
		// also 3 types of folder patterns.therefore need directory stack to determine.
		Stack<String> directoryStack = context.getDirectoryStack();
		// the file name to parse
		String fileName = file.getName();
		// ModelFile is extended from Model, used to deal with filename parsed information
		ModelFile modelFile = new ModelFile();
		// patter to match the filenames
		Pattern pt;
		Matcher mt;
		// mipTable, geographicalInfo and gridLabel, full Path default values;
		String mipTable = "N/A";
		String geographicalInfo = "N/A";
		String gridLabel = "N/A";
		String fullPath = "N/A";
		// check filename pattern, and if it is correct, overwrite some default values
		// the number of fields of each pattern
		Integer patternGroupNumber = 0;
		switch (directoryStack.get(1)) {
			case "cmip5_data":
				/* example: sic_OImon_GISS-E2-H_historical_r1i1p1_185001-200512.nc
				 * group1 - variableName
				 * group2 - mipTable
				 * group3 - model
				 * group4 - experiment
				 * group5 - ensembleMember
				 *
				 * the group 1, 3, 4, 5 are redundant fields which were already specified in the path,
				 * so we can just ignore them
				 */
				pt = Pattern.compile("([^_.]*)_([^_.]*)_([^_.]*)_([^_.]*)_([^_.]*)(.*)\\.nc");
				mt = pt.matcher(fileName);
				if (mt.matches()) {
					// for logging
					context.increaseMatchedFileCount();
					// get new values from filename
					mipTable = mt.group(2);
					fullPath = file.getAbsolutePath();
					// if temporalSubset is specified
					if (mt.groupCount() >= 6) {
						this.parseAndFillOptionalArgs(mt.group(6), modelFile);
					}
					// set all field information from filename;
					modelFile.setMipTable(mipTable);
					modelFile.setFullPath(fullPath);
					modelFile.setGeographicalInfo(geographicalInfo);
					modelFile.setGridLabel(gridLabel);
					// fill in other values parsed from directory path
					context.fillPathProperties(modelFile);
					// persistence in DB
					context.getModelFileHandler().handle(modelFile);
				} else {
					// for logging
					String unmatchedFileName = file.getAbsolutePath();
					log.warn("unmatched file: " + unmatchedFileName);
				}
				break;
			case "cmip6_data":
				/* example: snc_LImon_CIESM_historical_r1i1p1f1_gr_200001-201412.nc. this is 1 field more than cmip5(grid label)
				 * group1 - variableName
				 * group2 - mipTable
				 * group3 - model
				 * group4 - experiment
				 * group5 - ensembleMember
				 * group6 - grid label
				 *
				 * the group 1, 3, 4, 5, 6 are redundant fields which were already specified in the path,
				 * so we can just ignore them
				 */
				pt = Pattern.compile("([^_.]*)_([^_.]*)_([^_.]*)_([^_.]*)_([^_.]*)_([^_.]*)(.*)\\.nc");
				mt = pt.matcher(fileName);
				if (mt.matches()) {
					// for logging
					context.increaseMatchedFileCount();
					// get values from filename
					mipTable = mt.group(2);
					gridLabel = mt.group(6);
					fullPath = file.getAbsolutePath();
					// if temporalSubset or geographicalInfo is specified
					if (mt.groupCount() >= 7) {
						this.parseAndFillOptionalArgs(mt.group(7), modelFile);
					}
					// set all field information from filename;
					modelFile.setMipTable(mipTable);
					modelFile.setFullPath(fullPath);
					modelFile.setGeographicalInfo(geographicalInfo);
					modelFile.setGridLabel(gridLabel);
					// fill in other values parsed from directory path
					context.fillPathProperties(modelFile);
					// persistence in DB
					context.getModelFileHandler().handle(modelFile);
				} else {
					// for logging
					String unmatchedFileName = file.getAbsolutePath();
					log.warn("unmatched file: " + unmatchedFileName);
				}
				break;
			case "observation_data":
				/* example: tos_Omon_AVHRR_observation_Global_198201-201412.nc. this is 1 field more than cmip5(geographicalInfo)
				 * group1 - variableName
				 * group2 - mipTable
				 * group3 - model
				 * group4 - experiment
				 * group5 - geographicalInfo
				 *
				 * the group 1, 3, 4 are redundant fields which were already specified in the path,
				 * so we can just ignore them
				 */
				pt = Pattern.compile("([^_.]*)_([^_.]*)_([^_.]*)_([^_.]*)_([^_.]*)(.*)\\.nc");
				mt = pt.matcher(fileName);
				if (mt.matches()) {
					// for logging
					context.increaseMatchedFileCount();
					// get values from filename
					mipTable = mt.group(2);
					geographicalInfo = mt.group(5);
					fullPath = file.getAbsolutePath();
					// if temporalSubset or geographicalInfo is specified
					if (mt.groupCount() >= 6) {
						this.parseAndFillOptionalArgs(mt.group(6), modelFile);
					}
					// set all field information from filename;
					modelFile.setMipTable(mipTable);
					modelFile.setFullPath(fullPath);
					modelFile.setGeographicalInfo(geographicalInfo);
					modelFile.setGridLabel(gridLabel);
					// fill in other values parsed from directory path
					context.fillPathProperties(modelFile);
					// persistence in DB
					context.getModelFileHandler().handle(modelFile);
				} else {
					// for logging
					String unmatchedFileName = file.getAbsolutePath();
					log.warn("unmatched file: " + unmatchedFileName);
				}
				break;
			default:
				break;
		}
	}

	// to parse  temporalSubset or geographicalInfo
	private void parseAndFillOptionalArgs(String string, ModelFile modelFile) {
		string = string.replaceAll("^_", "");
		String[] splitted = string.split("_");

		Pattern pt = Pattern.compile("\\d*-\\d*(-.*)*");

		for (String s : splitted) {
			if (s == null || s.trim().length() == 0) {
				continue;
			}
			if (pt.matcher(s).matches()) {
				temporalSubsetParser.parseAndFill(s, modelFile);
			} else {
				modelFile.setGeographicalInfo(s);
			}
		}
	}
}