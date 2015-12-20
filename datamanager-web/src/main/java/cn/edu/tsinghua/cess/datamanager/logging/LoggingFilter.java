package cn.edu.tsinghua.cess.datamanager.logging;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by kurt on 2014/10/6.
 */
public class LoggingFilter implements Filter {

    private static Logger log = Logger.getLogger(LoggingFilter.class);
    private static int bodyLoggingSizeThreshold = 10 * 1024;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {



        long begin = System.currentTimeMillis();

        StringBuilder builder = this.buildMessage(request, "");
        log.info("begin processing request: " + builder.toString());

        chain.doFilter(request, response);




//        WrappedResponseProvider responseProvider = new WrappedResponseProvider((HttpServletResponse) response, bodyLoggingSizeThreshold);
//        Object[] requestAndBody = this.getRequestAndBody(request);
//        HttpServletRequest hrequest = (HttpServletRequest) requestAndBody[0];
//        HttpServletResponse hresponse = responseProvider.getWrappingResponse();
//        String requestBody = (String) requestAndBody[1];
//
//
//        long begin = System.currentTimeMillis();
//
//        StringBuilder builder = this.buildMessage(hrequest, requestBody);
//        log.info("begin processing request: " + builder.toString());
//
//        chain.doFilter(hrequest, response);

        // TODO: below code is for debug purpose only
//        try {
//            chain.doFilter(hrequest, hresponse);
//        } finally {
//            long elapsed = System.currentTimeMillis() - begin;
//            builder.append("[elapsed=").append(elapsed).append("]");
//            builder.append("[body=").append(this.getResponseBody(hresponse, responseProvider)).append("]");
//            log.info("finished processing request: " + builder.toString());
//        }
    }

    private String getResponseBody(HttpServletResponse response, WrappedResponseProvider provider) {
        try {
            String bodyType = response.getContentType();

            if (bodyType != null
                    && (bodyType.contains("text") || bodyType.contains("json"))) {
                return provider.getBodyContent();
            } else {
                return "body ignored due to bodyType=" + bodyType;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "error occured while getting body";
        }
    }

    private StringBuilder buildMessage(ServletRequest request, String requestBody) {
        HttpServletRequest r = (HttpServletRequest) request;

        StringBuilder builder = new StringBuilder();
        builder.append("[method=").append(r.getMethod()).append("]")
                .append("[uri=").append(r.getRequestURI()).append("]");
        this.printRequestMap(r.getParameterMap(), builder, r.getCharacterEncoding());
        builder.append("[requestBody=").append(requestBody).append("]");
        builder.append("[remoteAddr=").append(r.getRemoteAddr()).append("]")
                .append("[remoteHost=").append(r.getRemoteHost()).append("]");

        return builder;
    }


    private Object[] getRequestAndBody(ServletRequest request) {
        HttpServletRequest hrequest = (HttpServletRequest) request;
        int contentLength = hrequest.getContentLength();

        try {

            if (hrequest.getContentLength() > bodyLoggingSizeThreshold) {
                // request body larger than ${contentLength} will not be logged
                return new Object[]{
                        hrequest,
                        "requestSize=" + contentLength + " too large, ignored"

                };
            } else {

                HttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(hrequest);

                return new Object[]{
                        wrappedRequest,
                        IOUtils.toString(wrappedRequest.getInputStream(), hrequest.getCharacterEncoding())
                };
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Object[]{
                    hrequest,
                    "error getting request body"
            };
        }
    }

    private void printRequestMap(Map<?, ?> map, StringBuilder builder, String enc) {

        builder.append("[parameter=");

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            builder.append("[");

            Object key = entry.getKey();
            Object value = entry.getValue();

            builder.append("[")
                    .append("key=").append(key)
                    .append(", value=").append(toString(value, enc))
                    .append("]");
        }

        builder.append("]");
    }

    private String toString(Object obj, String enc) {
        if (obj == null) {
            return "null";
        } else if (obj.getClass().isArray()) {
            return new ArrayList<Object>(Arrays.asList((Object[]) obj)).toString();
        } else {
            return obj.toString();
        }
    }

    @Override
    public void destroy() {

    }

}
