package cn.edu.tsinghua.cess.datamanager.nclscript.logging;

/**
 * Created by kurt on 2014/10/6.
 */
public interface Logger {

    public void info(Object message);

    public void warn(Object message);

    public void error(Object message, Throwable ex);

}
