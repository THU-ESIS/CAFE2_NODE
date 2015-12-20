package cn.edu.tsinghua.cess.datamanager.nclscript;

import java.util.List;

/**
 * @author kurtyan777@gmail.com
 */
public interface NclScriptContext {

    public final static String RESULT_TYPE_NC = "nc";
    public final static String RESULT_TYPE_FIG = "fig";
    public final static String RESULT_TYPE_TXT = "txt";

    public List<String> getNcFileList();
    public int getBeginIndex();
    public int getEndIndex();
    public String getBeginTime(); //add 用户请求的开始时间，如"185001"
    public String getEndTime();   //add 用户请求的结束时间，如"185012"
    public String getInputFileFolder(); //add 
    public String getVarName(); //add 
    public int getLatMin();
    public int getLatMax();
    public int getLonMin();
    public int getLonMax();
    public Integer getTaskId();

    public void addResult(String type, String resultFilePath);

    public void updateProgress(int percentage, String comment);
    public void failed(Exception e);
    
}


