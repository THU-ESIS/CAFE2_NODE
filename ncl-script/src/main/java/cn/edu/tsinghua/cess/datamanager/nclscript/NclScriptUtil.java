package cn.edu.tsinghua.cess.datamanager.nclscript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NclScriptUtil {
	
	public static int waitFor(Process p) throws InterruptedException {
		StreamConsumer.newInstance(p.getInputStream()).start();
		StreamConsumer.newInstance(p.getErrorStream()).start();
		
		while (true) {
			try {
				return p.exitValue();
			} catch (IllegalThreadStateException e) {
				Thread.sleep(100);
			}
		}
	}
	
	private static class StreamConsumer implements Runnable {
		private InputStream is;
		
		private static StreamConsumer newInstance(InputStream is) {
			StreamConsumer sc = new StreamConsumer();
			sc.is = is;
			
			return sc;
		}
		
		public void start() {
			new Thread(this).start();
		}

		@Override
		public void run() {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			
			while (true) {
				try {
					if ((line = br.readLine()) == null) {
						break;
					}
				} catch (IOException e) {
					break;
				}
			}
			try {
				br.close();
			} catch (IOException e) {
			}
		}
	}

}
