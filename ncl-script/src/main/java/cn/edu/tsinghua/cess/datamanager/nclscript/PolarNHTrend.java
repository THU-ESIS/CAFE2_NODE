/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.edu.tsinghua.cess.datamanager.nclscript;

import java.io.File;

/**
 * @author ericxuhao
 */
public class PolarNHTrend implements NclScript {

    @Override
    public void run(NclScriptContext context) {
        String lat_min = String.valueOf(context.getLatMin());
        String lat_max = String.valueOf(context.getLatMax());
        CmdExecutor cExecutor = new CmdExecutor(context);
        File file_ori = cExecutor.getOriData(context, 30);
        File file_cdo = cExecutor.getCdoData(context, file_ori, 45);
        File file_year = cExecutor.getYearData(context, file_cdo, 60);
        OutputFile[] outputFiles = new OutputFile[2];
        String Alia = "TREND-NH_" + lat_min + "N-" + lat_max + "N";
        outputFiles[0] = new OutputFile(NclScriptContext.RESULT_TYPE_NC, new String[]{"TREND"}, new String[]{"nc_name"}, 1);
        outputFiles[1] = new OutputFile(NclScriptContext.RESULT_TYPE_FIG, new String[]{Alia}, new String[]{"fig_name"}, 1);
        cExecutor.runNcl(context, file_year, outputFiles, CmdExecutor.FIGURE_TYPE_POLAR, "PolarNHTrend.ncl", null);
        cExecutor.rmData(context, file_ori);
        cExecutor.rmData(context, file_cdo);
        cExecutor.rmData(context, file_year);
    }

}
