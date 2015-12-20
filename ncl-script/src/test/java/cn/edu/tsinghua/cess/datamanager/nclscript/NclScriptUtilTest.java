package cn.edu.tsinghua.cess.datamanager.nclscript;

import java.io.IOException;

public class NclScriptUtilTest {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Process p = Runtime.getRuntime().exec("notepad");
		NclScriptUtil.waitFor(p);
	}

}
