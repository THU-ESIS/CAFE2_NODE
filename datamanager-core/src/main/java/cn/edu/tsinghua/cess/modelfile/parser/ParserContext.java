package cn.edu.tsinghua.cess.modelfile.parser;

import cn.edu.tsinghua.cess.modelfile.entity.Model;

import java.util.Stack;

// ParserContext defines functions for pushing/popping to a ModelFileHandler,
// building Model Entity from Directory stack,
// some conditional statements and getting parsed file/dir counts
class ParserContext {

    /**
     * the 4 properties below are presented here for logging purpose
     */
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

    // CMIP5 folder hierarchy pattern: 10 levels
    // /CMIP5/output/NASA-GISS/GISS-E2-H-CC/historical/mon/seaIce/sic/r1i1p1/sic_OImon_GISS-E2-H-CC_historical_r1i1p1_195101-201012.nc
    // CMIP6 folder hierarchy pattern defined by DRS: 12 levels
    // /CMIP6/ScenarioMIP/THU/CIESM/ssp245/r1i1p1f1/SImon/simassacrossline/gn/v20200107/simassacrossline_SImon_CIESM_ssp245_r1i1p1f1_gn_201501-206412.nc
    public void fillPathProperties(Model modelEntity) {
        // same values between cmip 5 and 6
        String mipEra = directoryStack.get(1);
        String activity = directoryStack.get(2);
        String institute = directoryStack.get(3);
        String model = directoryStack.get(4);
        String experiment = directoryStack.get(5);
        // setting values
        modelEntity.setMipEra(mipEra);
        modelEntity.setActivity(activity);
        modelEntity.setInstitute(institute);
        modelEntity.setModel(model);
        modelEntity.setExperiment(experiment);

        // different conditions for cmip5 and cmip6:
        if (mipEra.equals("CMIP5")) {
            String frequency = directoryStack.get(6);
            String modelingRealm = directoryStack.get(7);
            String variableName = directoryStack.get(8);
            String ensembleMember = directoryStack.get(9);
            // setting values
            modelEntity.setFrequency(frequency);
            modelEntity.setModelingRealm(modelingRealm);
            modelEntity.setEnsembleMember(ensembleMember);
            modelEntity.setVariableName(variableName);
        } else if (mipEra.equals("CMIP6")) {
            String ensembleMember = directoryStack.get(6);
            String mipTable = directoryStack.get(7);
            String variableName = directoryStack.get(8);
            String gridLabel = directoryStack.get(9);
            String datasetVersion = directoryStack.get(10);
            // setting values
            modelEntity.setEnsembleMember(ensembleMember);
            modelEntity.setMipTable(mipTable);
            modelEntity.setVariableName(variableName);
            modelEntity.setGridLabel(gridLabel);
            modelEntity.setDatasetVersion(datasetVersion);
            // setting frequency and modelling realm the same as mipTable
            modelEntity.setFrequency(mipTable);
            modelEntity.setModelingRealm(mipTable);
        }
    }

    // determine whether or not to continue drill to child dir
    public boolean conformWithPattern() {
        // different conditions for cmip5 and cmip6:
        String mipEra = directoryStack.get(1);
        if (mipEra.equals("CMIP5")) {
            if (directoryStack.size() == 10) {
                return true;
            }
            else return false;
        } else if (mipEra.equals("CMIP6")) {
            if (directoryStack.size() == 11) {
                return true;
            }
            else return false;
        }
        return false;
    }

    public boolean continueDrillingIn() {
        // different conditions for cmip5 and cmip6:
        String mipEra = directoryStack.get(1);
        if (mipEra.equals("CMIP5")) {
            return directoryStack.size() < 11;
        } else if (mipEra.equals("CMIP6")) {
            return directoryStack.size() < 12;
        }
        return false;
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
