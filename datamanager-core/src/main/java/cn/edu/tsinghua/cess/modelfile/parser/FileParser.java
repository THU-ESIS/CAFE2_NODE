package cn.edu.tsinghua.cess.modelfile.parser;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			context.increaseTraversedFileCount();
			this.internalParseFile(file, context);
		} catch (Throwable e) {
			log.error("error parsing file=" + file.getAbsolutePath(), e);
		}
	}

	private void internalParseFile(File file, ParserContext context) {
		String fileName = file.getName();
		
		/*
		 * group1 - variableName
		 * group2 - mipTable
		 * group3 - model
		 * group4 - experiment
		 * group5 - ensembleMember
		 * 
		 * the group 1, 3, 4, 5 are redundant fields which were already specified in the path,
		 * so we can just ignore them
		 */
		Pattern pt = Pattern.compile("([^_.]*)_([^_.]*)_([^_.]*)_([^_.]*)_([^_.]*)(.*)\\.nc");
		Matcher mt = pt.matcher(fileName);
		
		if (mt.matches()) {
			context.increaseMatchedFileCount();
			
			ModelFile modelFile = new ModelFile();
			
            String mipTable = mt.group(2);
			String fullPath = file.getAbsolutePath();

			modelFile.setMipTable(mipTable);
			modelFile.setFullPath(fullPath);
			context.fillPathProperties(modelFile);

            if (mt.groupCount() >= 6) {	// if temporalSubset or geographicalInfo is specified
                this.parseAndFillOptionalArgs(mt.group(6), modelFile);
            }
			
			context.getModelFileHandler().handle(modelFile);
		} else {
            String unmatchedFileName = file.getAbsolutePath();
            log.warn("unmatched file: " + unmatchedFileName);
		}
	}

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
