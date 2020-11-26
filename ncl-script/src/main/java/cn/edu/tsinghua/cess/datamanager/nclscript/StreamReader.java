/**
 * Created by eric on 9/20/16.
 */

package cn.edu.tsinghua.cess.datamanager.nclscript;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamReader extends Thread {

    InputStream is;

    String type;

    Logger logger;

    StreamReader(InputStream is, String type, Logger logger) {
        this.is = is;
        this.type = type;
        this.logger = logger;
    }

    public void run() {
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (logger != null) logger.info(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            close(isr, br);
        }
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        String line = null;
//
//        while (true) {
//            try {
//                if ((line = br.readLine()) == null) {
//                    break;
//                }
//                System.out.println(line);
//            } catch (IOException e) {
//                e.printStackTrace();
//                break;
//            }
//        }
//        try {
//            br.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void close(InputStreamReader isr, BufferedReader br) {
        if (null != isr) {
            try {
                isr.close();
            } catch (IOException e) {
                isr = null;
            }
        }
        if (null != br) {
            try {
                br.close();
            } catch (IOException e) {
                br = null;
            }
        }
    }

}