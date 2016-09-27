package cn.edu.tsinghua.cess.datamanager.nclscript;

/**
 * This is the base interface for java encapsulation of ncl script.
 * Any java class with nc file analyzing capability should implement this interface
 * and provide the whole ncl script execution function by overriding the run(NclScriptContext) method.
 *
 * In the run() method, following argument(s) can be acquired by invoking corresponding getter method
 * on NclScriptContext:
 *  - ncFileList
 *  - beginIndex
 *  - endIndex
 *  - latMin
 *  - latMax
 *  - lonMin
 *  - lonMax
 *
 * During the execution phase, progress information can be passed to the caller
 * by invoking NclScriptContext.updateProgress(int, String) method.
 *
 * After ncl script execution, multi result can be passed to the caller
 * by repeatably invoking NclScriptContext.addResult(String, String) method.
 * When calling the addResult method, the result file path should be specified along with its type.
 * The three supported result type are defined as following in NclScriptContext:
 *  - NclScriptContext.RESULT_TYPE_NC, denotes nc data type
 *  - NclScriptContext.RESULT_TYPE_FIG, denotes figure data type
 *  - NclScriptContext.RESULT_TYPE_TXT, denotes txt data type
 *
 * On circumstance of failure, NclScriptContext.failed(Exception) method
 * should be invoked in order to inform the caller of the failure cause.
 *
 * @author kurtyan777@gmail.com
 */
public interface NclScript {

    public void run(NclScriptContext context);

}
