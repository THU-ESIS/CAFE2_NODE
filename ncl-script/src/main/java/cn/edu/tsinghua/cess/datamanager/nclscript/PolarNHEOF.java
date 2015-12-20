/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.tsinghua.cess.datamanager.nclscript;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author ericxuhao
 */
public class PolarNHEOF implements NclScript{

    @Override
    public void run(NclScriptContext context) {
        String lat_min=String.valueOf(context.getLatMin());
        String lat_max=String.valueOf(context.getLatMax());
        CmdExecutor cExecutor=new CmdExecutor(context);
        File file_ori=cExecutor.getOriData(context,30);
        File file_cdo=cExecutor.getCdoData(context, file_ori,45);  
 //       File file_year=cExecutor.getYearData(context, file_cdo,60);
        OutputFile[] outputFiles=new OutputFile[3];
        String Alia="_"+lat_min+"_"+lat_max;
        outputFiles[0]=new OutputFile(NclScriptContext.RESULT_TYPE_NC,new String[]{"EOF-NH"+Alia},new String[]{"nc_name"},1);
        outputFiles[1]=new OutputFile(NclScriptContext.RESULT_TYPE_TXT,new String[]{"EOF-TS-NH"+Alia},new String[]{"txt_name"},1);
        outputFiles[2]=new OutputFile(NclScriptContext.RESULT_TYPE_FIG,new String[]{"EOF-NH"+Alia},new String[]{"fig_name"},2);
        cExecutor.runNcl(context, file_cdo, outputFiles, CmdExecutor.FIGURE_TYPE_POLAR,"PolarNHEOF.ncl", null);
        
        try {
             Process p4 = Runtime.getRuntime().exec("rm "+file_ori.getAbsolutePath());
             p4.waitFor();
             Process p6 = Runtime.getRuntime().exec("rm "+file_cdo.getAbsolutePath());
             p6.waitFor();
        } catch (IOException ex) {
             context.failed(ex);
        } catch (InterruptedException ex) {
             context.failed(ex);
        }
        context.updateProgress(100, "Task accomplished!"); 
    }
    
}
