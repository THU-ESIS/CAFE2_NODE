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
public class SeasonalContourNH implements NclScript{
        @Override
    public void run(NclScriptContext context) {
        String lat_min=String.valueOf(context.getLatMin());
        String lat_max=String.valueOf(context.getLatMax());
        CmdExecutor cExecutor=new CmdExecutor(context);
        File file_ori=cExecutor.getOriData(context,30);
        File file_cdo=cExecutor.getCdoData(context, file_ori,45);  
        OutputFile[] outputFiles=new OutputFile[2];
        String Alia="_"+lat_min+"N-"+lat_max+"N";
        outputFiles[0]=new OutputFile(NclScriptContext.RESULT_TYPE_FIG,new String[]{"SEAS-CN-NH"+Alia},new String[]{"fig_name"},1);
        outputFiles[1]=new OutputFile(NclScriptContext.RESULT_TYPE_NC,new String[]{"SEAS-CN-NH"+Alia},new String[]{"nc_name"},1);
        cExecutor.runNcl(context, file_cdo, outputFiles, CmdExecutor.FIGURE_TYPE_POLAR,"SeasonalContourNH.ncl", null);
        cExecutor.rmData(context,file_ori);
        cExecutor.rmData(context,file_cdo);

    }
}
