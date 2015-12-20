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
public class TimeOutValidate {
    public static void validate(int timeout,int i,String filetype) throws TimeOutException{
        if(i>=timeout){
          throw new TimeOutException("Time out Error!Failed to generate "+filetype+"!");//抛出异常
        }
    }
    
    public static void fileValidate(int timeout,File file,String filetype,NclScriptContext context,int sleeptime){
        for(int i=0;i<=timeout;i++){
                if(i==timeout){
                    try { 
                        validate(timeout, i, filetype);
                    } catch (TimeOutException ex) {
                        context.failed(ex);
                    }
                }
                if(!file.exists()){
                try {
                      Thread.sleep(sleeptime);
                    } 
                catch (InterruptedException ex) {
                       context.failed(ex);
                    }
                }
                else break;
            }  
    }
    public static void fileValidate2(int timeout,File file,String filetype,NclScriptContext context,int sleeptime){
        long temp=10000;
        long filelength=0;
        for(int i=0;i<=timeout;i++){
                if(i==timeout){
                    try { 
                        TimeOutValidate.validate(timeout, i, filetype);
                    } catch (TimeOutException ex) {
                        context.failed(ex);
                    }
                }
                if(!file.exists()||temp!=filelength){
                temp=file.length();
                try {
                      Thread.sleep(sleeptime);
                    } 
                catch (InterruptedException ex) {
                       context.failed(ex);
                    }
                filelength=file.length();
                }
                else break;
            }
    }
}
