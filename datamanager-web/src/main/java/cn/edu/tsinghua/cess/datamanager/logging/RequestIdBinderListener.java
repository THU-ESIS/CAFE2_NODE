package cn.edu.tsinghua.cess.datamanager.logging;

import org.apache.log4j.MDC;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * Created by yanke on 2015-12-05 16:37
 */
public class RequestIdBinderListener implements ServletRequestListener {

    private static String key = "requestId";

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        MDC.remove(key);

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        String requestId = RandomUtil.randomString(8);
        MDC.put(key, requestId);
    }

}
