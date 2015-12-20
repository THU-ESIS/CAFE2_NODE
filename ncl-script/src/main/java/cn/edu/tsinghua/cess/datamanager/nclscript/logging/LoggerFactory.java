package cn.edu.tsinghua.cess.datamanager.nclscript.logging;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by kurt on 2014/10/6.
 */
public class LoggerFactory {

    private static Logger stdoutLogger = new Logger() {
        @Override
        public void info(Object message) {
            System.out.println(message);
        }

        @Override
        public void warn(Object message) {
            System.out.println(message);
        }

        @Override
        public void error(Object message, Throwable ex) {
            System.err.println(message);
            ex.printStackTrace();
        }
    };

    public static Logger getLogger(Class<?> caller) {
        try {
            final Class<?> clazz = Class.forName("org.apache.log4j.Logger");
            final Method method = clazz.getMethod("getLogger", Class.class);
            final Object logger = method.invoke(clazz, caller);


            return new Logger() {
                @Override
                public void info(Object message) {
                    try {
                        clazz.getMethod("info", Object.class).invoke(logger, message);
                    } catch (Exception e) {
                        stdoutLogger.info(message);
                    }
                }

                @Override
                public void warn(Object message) {
                    try {
                        clazz.getMethod("warn", Object.class).invoke(logger, message);
                    } catch (Exception e) {
                        stdoutLogger.warn(message);
                    }
                }

                @Override
                public void error(Object message, Throwable ex) {
                    try {
                        clazz.getMethod("error", new Class<?>[] { Object.class, Throwable.class }).invoke(logger, new Object[] { message, ex });
                    } catch (Exception e) {
                        stdoutLogger.error(message, ex);
                    }

                }
            };
        } catch (Exception e) {
            return stdoutLogger;
        }
    }

}
