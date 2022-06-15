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
public class PolarNHLTM implements NclScript {

    @Override
    public void run(NclScriptContext context) {
        String lat_min = String.valueOf(context.getLatMin());
        String lat_max = String.valueOf(context.getLatMax());
        CmdExecutor cExecutor = new CmdExecutor(context);
        File file_ori = cExecutor.getOriData(context, 30);
        File file_cdo = cExecutor.getCdoData(context, file_ori, 45);
        File file_mean = cExecutor.getMeanData(context, file_cdo, 60);
        context.addResult(NclScriptContext.RESULT_TYPE_NC, file_mean.getAbsolutePath());
        OutputFile[] outputFiles = new OutputFile[1];
        String Alia = "LTM-NH_" + lat_min + "N-" + lat_max + "N";
        outputFiles[0] = new OutputFile(NclScriptContext.RESULT_TYPE_FIG, new String[]{Alia}, new String[]{"fig_name"}, 1);
        cExecutor.runNcl(context, file_mean, outputFiles, CmdExecutor.FIGURE_TYPE_ADDTIME, "PolarNHLTM.ncl", null);
        cExecutor.rmData(context, file_ori);
        cExecutor.rmData(context, file_cdo);

    }

}
