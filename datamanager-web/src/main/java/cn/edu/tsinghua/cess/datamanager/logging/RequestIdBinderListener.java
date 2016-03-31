package cn.edu.tsinghua.cess.datamanager.logging;

import cn.edu.tsinghua.cess.util.RequestIdBinder;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * Created by yanke on 2015-12-05 16:37
 */
public class RequestIdBinderListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        RequestIdBinder.unbind();
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        RequestIdBinder.bind();
    }

}
