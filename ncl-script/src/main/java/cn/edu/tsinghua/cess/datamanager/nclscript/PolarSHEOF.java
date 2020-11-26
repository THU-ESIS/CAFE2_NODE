/**
 * Created by eric on 9/24/16.
 */

package cn.edu.tsinghua.cess.datamanager.nclscript;

import java.io.File;

public class PolarSHEOF implements NclScript {

    @Override
    public void run(NclScriptContext context) {
        String lat_min = String.valueOf(context.getLatMin());
        String lat_max = String.valueOf(context.getLatMax());
        CmdExecutor cExecutor = new CmdExecutor(context);
        File file_ori = cExecutor.getEOFOriData(context, 30);
        File file_cdo = cExecutor.getCdoData(context, file_ori, 45);
        //       File file_year=cExecutor.getYearData(context, file_cdo,60);
        OutputFile[] outputFiles = new OutputFile[3];
        int minlat = -Integer.parseInt(lat_max);
        int maxlat = -Integer.parseInt(lat_min);
        String Alia = "_" + minlat + "S-" + maxlat + "S";
        outputFiles[0] = new OutputFile(NclScriptContext.RESULT_TYPE_NC, new String[]{"EOF-SH" + Alia}, new String[]{"nc_name"}, 1);
        outputFiles[1] = new OutputFile(NclScriptContext.RESULT_TYPE_TXT, new String[]{"EOF-TS-SH" + Alia}, new String[]{"txt_name"}, 1);
        outputFiles[2] = new OutputFile(NclScriptContext.RESULT_TYPE_FIG, new String[]{"EOF-SH" + Alia}, new String[]{"fig_name"}, 6);
        cExecutor.runNcl(context, file_cdo, outputFiles, CmdExecutor.FIGURE_TYPE_POLAR, "PolarSHEOF.ncl", null);
        cExecutor.rmData(context, file_ori);
        cExecutor.rmData(context, file_cdo);

    }

}
