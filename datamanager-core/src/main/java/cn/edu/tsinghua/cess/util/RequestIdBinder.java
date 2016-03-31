package cn.edu.tsinghua.cess.util;

import org.apache.log4j.MDC;

/**
 * Created by yanke on 2016/4/1.
 */
public class RequestIdBinder {

    private static String key = "requestId";

    public static void bind() {
        String requestId = RandomUtil.randomString(8);
        MDC.put(key, requestId);
    }

    public static void unbind() {
        MDC.remove(key);
    }

}
