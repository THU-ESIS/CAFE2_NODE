package cn.edu.tsinghua.cess.modelfile.parser;

import cn.edu.tsinghua.cess.modelfile.entity.ModelFile;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    // Split and get information from file names
    private void internalParseFile(File file, ParserContext context) {
        String fileName = file.getName();
        /* CMIP5 filenames: sic_OImon_GISS-E2-H-CC_historical_r1i1p1_195101-201012.nc
         * group1 - variableName
         * group2 - mipTable
         * group3 - model
         * group4 - experiment
         * group5 - ensembleMember
         *
         * the group 1, 3, 4, 5 are redundant fields which were already specified in the path,
         * so we can just ignore them. group 2 - mipTable can be added.
         */
        /* CMIP6 filenames: tas_Amon_BCC-CSM2-MR_1pctCO2_r1i1p1f1_gn_185001-200012.nc
         * group1 - variableName
         * group2 - mipTable
         * group3 - model
         * group4 - experiment
         * group5 - ensembleMember
         * group6 - gridLabel
         *
         * the group 1, 2, 3, 4, 5, 6 are redundant fields which were already specified in the path,
         * so we can just ignore them
         */
        Pattern pt = Pattern.compile("([^_.]*)_([^_.]*)_([^_.]*)_([^_.]*)_([^_.]*)(.*)\\.nc");
        Matcher mt = pt.matcher(fileName);

        if (mt.matches()) {
            context.increaseMatchedFileCount();

            ModelFile modelFile = new ModelFile();

            String mipTable = mt.group(2);
            String fullPath = file.getAbsolutePath();
            // miptable already extracted from CMIP6 file path, therfore modelfile.miptable is overwritten for cmip6, but
            // CMIP5 miptable is extracted and set for the first time
			modelFile.setMipTable(mipTable);
            modelFile.setFullPath(fullPath);
            context.fillPathProperties(modelFile);

            if (mt.groupCount() >= 6) {    // if temporalSubset or geographicalInfo is specified, ther will be >=6 matched groups
                this.parseAndFillOptionalArgs(mt.group(6), modelFile);
            }

            context.getModelFileHandler().handle(modelFile);
        } else {
            String unmatchedFileName = file.getAbsolutePath();
            log.warn("unmatched file: " + unmatchedFileName);
        }
    }

    private void parseAndFillOptionalArgs(String string, ModelFile modelFile) {
        // the 6th matched group will be
        string = string.replaceAll("^_", "");
        String[] splitted = string.split("_");

        Pattern pt = Pattern.compile("\\d*-\\d*(-.*)*");

        for (String s : splitted) {
            if (s == null || s.trim().length() == 0) {
                continue;
            }
            // temporal information will be extracted by this matcher
            if (pt.matcher(s).matches()) {
                temporalSubsetParser.parseAndFill(s, modelFile);
            } else {
                // gridlabel already extracted from CMIP6 file path, therfore modelfile.gridlabel is overwritten for cmip6 here
                // if cmip5 file name has grid label, this will set modelfile.gridlabel for it.
                modelFile.setGridLabel(s);
            }
        }
    }

}
