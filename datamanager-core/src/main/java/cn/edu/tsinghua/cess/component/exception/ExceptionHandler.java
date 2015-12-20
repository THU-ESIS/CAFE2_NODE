package cn.edu.tsinghua.cess.component.exception;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.util.concurrent.Callable;


public class ExceptionHandler {

    public static RuntimeException wrapAsUnchecked(Throwable t) {
        ExceptionHandler.<RuntimeException>wrap(t);
        return null;
    }
    
    public static <T> T unchecked(Callable<T> command) {
    	try {
    		return command.call();
    	} catch (Exception e) {
    		throw wrapAsUnchecked(e);
		}
    }
    
    public static <T> T exceptionFree(Callable<T> command) {
    	try {
    		return command.call();
    	} catch (Exception e) {
    		return null;
		}
    	
    }
    
    @SuppressWarnings("unchecked")
	private static <T extends Throwable> void wrap(Throwable t) throws T {
        throw (T) t;
    }

    public static void throwUnchecked(String message) {
        throw new RuntimeException(message);
    }

    public static String printStackTrace(Throwable e) {
        CharArrayWriter caw = new CharArrayWriter();
        e.printStackTrace(new PrintWriter(caw));
        caw.flush();

        return caw.toString();
    }

}
