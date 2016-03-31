/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.tsinghua.cess.datamanager.nclscript;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author ericxuhao
 */
public class NclOutput {

    private static Logger logger = Logger.getLogger(NclOutput.class);

    private static String TempFolder;
    //private static String TempFolder="/mnt/CMIP5/cmip5_tmp/";
    private static String ncl_path;
    private static String ncl_env;
    private static String ScriptFolder;
	static {
        InputStream is = NclOutput.class.getClassLoader().getResourceAsStream("config.properties");
        Properties prop = new Properties();
        try {
            prop.load(is);
			TempFolder=  prop.getProperty( "TempFolder" ).trim(); 
            ncl_path=  prop.getProperty( "ncl_path" ).trim(); 
            ncl_env=  prop.getProperty( "ncl_env" ).trim(); 
            ScriptFolder=  prop.getProperty( "ScriptFolder" ).trim();

            logger.info("load config.properties succeeded" +
                    ", tempFoler=" + TempFolder +
                    ", nclPath=" + ncl_path +
                    ", nclEnv=" + ncl_env +
                    ", scriptFoler=" + ScriptFolder
            );
        } catch (IOException e) {
            logger.error("error loading config.properties", e);
            throw new RuntimeException(e);
        }
    }
    public static void setTempFolder(String tFolder){
         TempFolder=tFolder;
    }
    public static void setScriptFolder(String sFolder){
         ScriptFolder=sFolder;
    }
    public static String getTempFolder(){
         return TempFolder;
    }
    public static String getScriptFolder(){
         return ScriptFolder;
    }
    public static void setNclPath(String NclPath){
         ncl_path=NclPath;
    }
    public static void setNclEnv(String NclEnv){
         ncl_env=NclEnv;
    }
    public static String getNclPath(){
         return ncl_path;
    }
    public static String getNclEnv(){
         return ncl_env;
    }
    public static String getOutputDir(int taskId){
        String s_taskId= String.valueOf(taskId);
        String OutputDir=TempFolder+s_taskId;
        File file =new File(OutputDir);
        if((!file.exists())&&(!file.isDirectory()))      
            file.mkdir();    
        return OutputDir;
    }
    public static String getOriDir(String OutputDir){
        String OriDir=OutputDir+"/"+"ori_data";
        File file =new File(OriDir);
        if((!file.exists())&&(!file.isDirectory()))      
            file.mkdir();    
        return OriDir;
    }
    public static String getMeanDir(String OutputDir){
        String MeanDir=OutputDir+"/"+"mean_data";
        File file =new File(MeanDir);
        if((!file.exists())&&(!file.isDirectory()))      
            file.mkdir();    
        return MeanDir;
    }
    public static String getSeasDir(String OutputDir){
             String SeasDir=OutputDir+"/"+"seas_data";
        File file =new File(SeasDir);
        if((!file.exists())&&(!file.isDirectory()))      
            file.mkdir();    
        return SeasDir;
    }
    public static String getYearDir(String OutputDir){
                String YearDir=OutputDir+"/"+"year_data";
        File file =new File(YearDir);
        if((!file.exists())&&(!file.isDirectory()))      
            file.mkdir();    
        return YearDir;
    }
    public static String getFigDir(String OutputDir){
               String FigDir=OutputDir+"/"+"fig_data";
        File file =new File(FigDir);
        if((!file.exists())&&(!file.isDirectory()))      
            file.mkdir();    
        return FigDir;
    }
    public static String getTxtDir(String OutputDir){
                String TxtDir=OutputDir+"/"+"txt_data";
        File file =new File(TxtDir);
        if((!file.exists())&&(!file.isDirectory()))      
            file.mkdir();    
        return TxtDir;
    }
    public static String getNcDir(String OutputDir){
        String NcDir=OutputDir+"/"+"nc_data";
        File file =new File(NcDir);
        if((!file.exists())&&(!file.isDirectory()))      
            file.mkdir();    
        return NcDir;
    }
    public static String getCdoDir(String OutputDir){
        String CdoDir=OutputDir+"/"+"cdo_data";
        File file =new File(CdoDir);
        if((!file.exists())&&(!file.isDirectory()))      
            file.mkdir();    
        return CdoDir;
    }
    public static String getScriptPath(String Script){
        String ScriptDir=ScriptFolder+Script;  
        return ScriptDir;
    }
    
}
