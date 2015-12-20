/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.tsinghua.cess.datamanager.nclscript;

import java.io.File;

/**
 *
 * @author ericxuhao
 */
public class OutputFile {
    String outputType;
    String[] outputAlias;
    File[] outputFile;
    String[] outputArgName;
    int count;
    OutputFile(){}
    OutputFile(String outputType,String[] outputAlias,String[] outputArgName,int count){
       this.outputType=outputType;   //输出数据类型
       this.outputAlias=outputAlias;  //输出数据文件后缀名
       this.count=count; //输出文件个数
       this.outputArgName=outputArgName;  //NCL脚本中输出文件夹的变量名，如nc_name,fig_name
       this.outputFile=new File[this.count]; 
    }
}
