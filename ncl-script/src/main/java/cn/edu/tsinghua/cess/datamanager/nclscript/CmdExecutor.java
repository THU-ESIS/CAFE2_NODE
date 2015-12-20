/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.tsinghua.cess.datamanager.nclscript;

import cn.edu.tsinghua.cess.datamanager.nclscript.logging.Logger;
import cn.edu.tsinghua.cess.datamanager.nclscript.logging.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ericxuhao
 */
public class CmdExecutor {

    private Logger logger = LoggerFactory.getLogger(getClass());


        private String begin_time;
        private String inputFileFolder;
        private List<String> filelist;
        private String end_time;
        private int year_start;
        private int year_end;
        private int begin_index;
        private int end_index;
        private int lat_min;
        private int lat_max;
        private int lon_min;
        private int lon_max;
        private int timeout;
        private int taskId;
        private String OutputDir;
        private String var_name;
        public final static String FIGURE_TYPE_POLAR = "polar";
        public final static String FIGURE_TYPE_ADDLON = "addlon";
        public final static String FIGURE_TYPE_ADDTIME = "addtime";
        public final static String FIGURE_TYPE_ADDBOTH = "addboth";
        public final static String FIGURE_TYPE_NULL = "null";
        
    CmdExecutor(NclScriptContext context){
        begin_time=context.getBeginTime();
        inputFileFolder=context.getInputFileFolder();
        filelist=context.getNcFileList();
        end_time=context.getEndTime();
        year_start=Integer.parseInt(begin_time.substring(0,4));
        year_end=Integer.parseInt(end_time.substring(0,4));
        begin_index=context.getBeginIndex();
        end_index=context.getEndIndex();
        lat_min=context.getLatMin();
        lat_max=context.getLatMax();
        lon_min=context.getLonMin();
        lon_max=context.getLonMax();
        var_name=context.getVarName();
        timeout=500;
        taskId=context.getTaskId();
        OutputDir=NclOutput.getOutputDir(taskId);
    }
    public void setTimeout(int timeout) {
       //To change body of generated methods, choose Tools | Templates.
        this.timeout=timeout;
    }
    public int getTimeout() {
        return this.timeout;//To change body of generated methods, choose Tools | Templates.
    }
    private void execute(NclScriptContext context,String cmd){
        try{
            Process p = Runtime.getRuntime().exec(cmd);
                try{
                  p.waitFor();
                }
                catch(InterruptedException ex){
                  context.failed(ex);
                }
            }catch(IOException ex){
                context.failed(ex);
            } 
    }
    public File getOriData(NclScriptContext context,int percentage){
        System.out.println("beginIndex:"+begin_index+"");
        System.out.println("beginIndex:"+end_index+"");
        String fi_ori=NclOutput.getOriDir(OutputDir);
        String fi_ori_name=filelist.get(0).substring(inputFileFolder.length()+1,filelist.get(0).length()-16)+
        begin_time+"-"+end_time+"_ori.nc";
        StringBuilder buffer=new StringBuilder("");
        buffer.append("ncrcat ").append("-d ").append("time,");
        buffer.append(Integer.toString(begin_index)+",");
        buffer.append(Integer.toString(end_index)+" ");
        for(int i=0;i<filelist.size();i++){
          buffer.append(filelist.get(i)+" ");
        }
        buffer.append(fi_ori).append("/").append(fi_ori_name);
        File file_ori=new File(fi_ori+"/"+fi_ori_name);
        String cmd=buffer.toString();

        logger.info("cmd=" + cmd);

        if(!file_ori.exists()){
            execute(context,cmd);
            TimeOutValidate.fileValidate(timeout, file_ori, "original merged data", context, 1000);
        }
        context.updateProgress(percentage, "Successfully generated original merged data!");
        return file_ori;
    }
    public File getCdoData(NclScriptContext context,File InputFile,int percentage){
        String fi_cdo=NclOutput.getCdoDir(OutputDir);
        String fi_cdo_name=filelist.get(0).substring(inputFileFolder.length()+1,filelist.get(0).length()-16)+
        begin_time+"-"+end_time+"_cdo.nc";
        File file_cdo=new File(fi_cdo+"/"+fi_cdo_name);
        String cmd="cdo remapbil,r360x180 "+InputFile.getAbsolutePath()+" "+file_cdo.getAbsolutePath();
        if(!file_cdo.exists()){
            execute(context,cmd);
            TimeOutValidate.fileValidate(timeout, file_cdo, "cdo converted data", context, 1000);
        }
        context.updateProgress(percentage, "Successfully generated cdo converted data!");
        return file_cdo; 
    }
    public File getYearData(NclScriptContext context,File InputFile,int percentage){
        String fi_data=NclOutput.getYearDir(OutputDir);
        String fi_name=filelist.get(0).substring(inputFileFolder.length()+1,filelist.get(0).length()-16)+
        begin_time+"-"+end_time+"_year.nc";
        File file_year=new File(fi_data+"/"+fi_name);
        String cmd="cdo "+"yearavg "+InputFile.getAbsolutePath()+" "+file_year.getAbsolutePath();        
        if(!file_year.exists()){
            execute(context,cmd);
            TimeOutValidate.fileValidate(timeout, file_year, "year average data", context, 1000);
        }
        context.updateProgress(percentage, "Successfully generated year average data!");
        return file_year; 
    }
    public File getMeanData(NclScriptContext context,File InputFile,int percentage){
        String fi_data=NclOutput.getMeanDir(OutputDir);
        String fi_name=filelist.get(0).substring(inputFileFolder.length()+1,filelist.get(0).length()-16)+
        begin_time+"-"+end_time+"_mean.nc";
        File file_mean=new File(fi_data+"/"+fi_name);
	String cmd="ncra "+InputFile.getAbsolutePath()+" "+file_mean.getAbsolutePath();
        if(!file_mean.exists()){
            execute(context,cmd);
            TimeOutValidate.fileValidate(timeout, file_mean, "long term mean data", context, 1000);
        }
        context.updateProgress(percentage, "Successfully generated long term mean data!");
        return file_mean; 
    }
    public File getSeasData(NclScriptContext context,File InputFile,int percentage){
        String fi_data=NclOutput.getSeasDir(OutputDir);
        String fi_name=filelist.get(0).substring(inputFileFolder.length()+1,filelist.get(0).length()-16)+
        begin_time+"-"+end_time+"_seas.nc";
        File file_seas=new File(fi_data+"/"+fi_name);
        String cmd="cdo "+"seasavg "+InputFile.getAbsolutePath()+" "+file_seas.getAbsolutePath();
        if(!file_seas.exists()){
            execute(context,cmd);
            TimeOutValidate.fileValidate(timeout, file_seas, "seasonal average data", context, 1000);
        }
        context.updateProgress(percentage, "Successfully generated seasonal average data!");
        return file_seas;
    }
    public void runNcl(NclScriptContext context,File inputFile,OutputFile[] outputfiles,String figureType,String NclScript,String[] otherArguments){
        List<String> Arguments = new ArrayList();
    //    String ncl_path="/usr/local/ncl/bin/ncl";
        String ncl_path=NclOutput.getNclPath();
        Arguments.add(ncl_path);
        String fi_data=inputFile.getParent();
        String fi_name=inputFile.getName();
        fi_data= "fi_data =\""+fi_data +"\"";
        fi_name= "fi_name =\""+fi_name+"\"";
        Arguments.add(fi_data);
        Arguments.add(fi_name);
        for(int i=0;i<outputfiles.length;i++){
            String type=outputfiles[i].outputType;

            if ("nc".equals(type)) {
                String nc_data = NclOutput.getNcDir(OutputDir);
                Arguments.add("nc_data =\"" + nc_data + "\"");
                for (int j = 0; j < outputfiles[i].count; j++) {
                    String nc_name = filelist.get(0).substring(inputFileFolder.length() + 1, filelist.get(0).length() - 16) +
                            begin_time + "-" + end_time + "_" + outputfiles[i].outputAlias[j] + ".nc";
                    outputfiles[i].outputFile[j] = new File(nc_data + "/" + nc_name);
                    Arguments.add(outputfiles[i].outputArgName[j] + "=\"" + nc_name + "\"");
                }
            } else if ("fig".equals(type)) {
                String fig_data = NclOutput.getFigDir(OutputDir);
                String fig_name = filelist.get(0).substring(inputFileFolder.length() + 1, filelist.get(0).length() - 16) +
                        begin_time + "-" + end_time + "_" + outputfiles[i].outputAlias[0];
                Arguments.add("fig_data=\"" + fig_data + "\"");
                Arguments.add(outputfiles[i].outputArgName[0] + "=\"" + fig_name + "\"");
                if (outputfiles[i].count == 1) {
                    outputfiles[i].outputFile[0] = new File(fig_data + "/" + fig_name + ".png");
                } else {
                    for (int j = 0; j < outputfiles[i].count; j++) {
                        outputfiles[i].outputFile[j] = new File(fig_data + "/" + fig_name + ".00000" + String.valueOf(j + 1) + ".png");
                    }
                }
            } else if ("txt".equals(type)) {
                String txt_data = NclOutput.getTxtDir(OutputDir);
                Arguments.add("txt_data =\"" + txt_data + "\"");
                for (int j = 0; j < outputfiles[i].count; j++) {
                    String txt_name = filelist.get(0).substring(inputFileFolder.length() + 1, filelist.get(0).length() - 16) +
                            begin_time + "-" + end_time + "_" + outputfiles[i].outputAlias[j] + ".txt";
                    outputfiles[i].outputFile[j] = new File(txt_data + "/" + txt_name);
                    Arguments.add(outputfiles[i].outputArgName[j] + "=\"" + txt_name + "\"");
                }
            }
        }
        if(!figureType.equals("null")){
            Arguments.add("var_name=\""+var_name+"\"");
            Arguments.add("latmin="+String.valueOf(lat_min));
            Arguments.add("latmax="+String.valueOf(lat_max));
        }
        if(figureType.equals("addlon")||figureType.equals("addboth")){
            Arguments.add("lonmin="+String.valueOf(lon_min));
            Arguments.add("lonmax="+String.valueOf(lon_max));
        }
        if(figureType.equals("addtime")||figureType.equals("addboth")){
            Arguments.add("start_year="+String.valueOf(year_start));
            Arguments.add("end_year="+String.valueOf(year_end));
        }
        if(otherArguments!=null){
            for(int i=0;i<otherArguments.length;i++){
                Arguments.add(otherArguments[i]);
        }
        }
        String nclscript = NclOutput.getScriptPath(NclScript);
        Arguments.add(nclscript);
        String[] cmd=new String[Arguments.size()];
        for(int i=0;i<Arguments.size();i++){
           cmd[i]=Arguments.get(i);
           System.out.println(cmd[i]+"\n");
        }
//        String ncl_env="NCARG_ROOT=/usr/local/ncl";
        String ncl_env=NclOutput.getNclEnv();
        String [] env={ncl_env};
        context.updateProgress(70, "Begin plotting!");
        try {
            Process p = Runtime.getRuntime().exec(cmd,env);
            for(int i=0;i<outputfiles.length;i++){
                String type=outputfiles[i].outputType;
                for(int j=0;j<outputfiles[i].count;j++){
                        if ("nc".equals(type)) {
                            TimeOutValidate.fileValidate2(timeout, outputfiles[i].outputFile[j], "output nc data", context, 500);
                        } else if ("txt".equals(type)) {
                            TimeOutValidate.fileValidate2(timeout, outputfiles[i].outputFile[j], "output text data", context, 500);
                        } else if ("fig".equals(type)) {
                            TimeOutValidate.fileValidate2(timeout, outputfiles[i].outputFile[j], "output figure data", context, 500);
                        }
                    context.addResult(type, outputfiles[i].outputFile[j].getAbsolutePath());
                }
                context.updateProgress(70 + 5 * (i + 1), "Successfully generated output " + type + " data!");
            }
            p.destroy();
        } catch (IOException ex) {
            context.failed(ex);
        }
    }
}
