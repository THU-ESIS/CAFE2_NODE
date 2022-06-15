package cn.edu.tsinghua.cess.datamanager.logging;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.Charset;

/**
 * Created by yanke on 2015-11-17 14:25
 */
public class WrappedResponseProvider {

    HttpServletResponse originResponse;
    HttpServletResponse wrappingResponse;
    CachingOutputStream wrappingOutputStream;
    int bodyCachingThreshold = 10 * 1024;

    public WrappedResponseProvider(final HttpServletResponse originResponse, int bodyCachingThreshold) {
        this.originResponse = originResponse;
        this.bodyCachingThreshold = bodyCachingThreshold;
        this.wrappingResponse = (HttpServletResponse) Proxy.newProxyInstance(
                WrappedResponseProvider.class.getClassLoader(),
                new Class[]{HttpServletResponse.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().contains("getOutputStream")) {
                            return getOutputStream();
                        }
                        if (method.getName().contains("getWriter")) {
                            return getWriter();
                        }
                        return method.invoke(originResponse, args);
                    }
                }
        );
    }

    public HttpServletResponse getOriginResponse() {
        return originResponse;
    }

    public void setOriginResponse(HttpServletResponse originResponse) {
        this.originResponse = originResponse;
    }

    public HttpServletResponse getWrappingResponse() {
        return wrappingResponse;
    }

    public void setWrappingResponse(HttpServletResponse wrappingResponse) {
        this.wrappingResponse = wrappingResponse;
    }

    public CachingOutputStream getWrappingOutputStream() {
        return wrappingOutputStream;
    }

    public void setWrappingOutputStream(CachingOutputStream wrappingOutputStream) {
        this.wrappingOutputStream = wrappingOutputStream;
    }

    public int getBodyCachingThreshold() {
        return bodyCachingThreshold;
    }

    public void setBodyCachingThreshold(int bodyCachingThreshold) {
        this.bodyCachingThreshold = bodyCachingThreshold;
    }

    ServletOutputStream getOutputStream() throws IOException {
        if (wrappingOutputStream == null) {
            ServletOutputStream originOutputStream = originResponse.getOutputStream();
            wrappingOutputStream = new CachingOutputStream(originOutputStream, bodyCachingThreshold);
        }

        return wrappingOutputStream;
    }

    PrintWriter getWriter() throws IOException {
        return new PrintWriter(this.getOutputStream());
    }

    String getBodyContent() {
        String charset = originResponse.getCharacterEncoding();

        if (wrappingOutputStream == null
                || wrappingOutputStream.baos == null
                || wrappingOutputStream.baos.size() == 0) {
            return "empty body";
        }

        String body = new String(wrappingOutputStream.baos.toByteArray(), Charset.forName(charset));

        if (wrappingOutputStream.isComplete) {
            return body;
        } else {
            return "truncated body: " + body;
        }
    }

    class CachingOutputStream extends ServletOutputStream {

        int capacityThreshold = bodyCachingThreshold;
        boolean isComplete = true;
        ByteArrayOutputStream baos;
        OutputStream wrapped;

        /**
         * Creates an output stream filter built on top of the specified
         * underlying output stream.
         *
         * @param out the underlying output stream to be assigned to
         *            the field <tt>this.out</tt> for later use, or
         *            <code>null</code> if this instance is to be
         *            created without an underlying stream.
         */
        public CachingOutputStream(OutputStream out, int capacityThreshold) {
            wrapped = out;
            baos = new ByteArrayOutputStream();
            this.capacityThreshold = capacityThreshold;
        }

        @Override
        public void write(int b) throws IOException {
            wrapped.write(b);

            if (baos.size() > capacityThreshold) {
                if (isComplete) {
                    isComplete = false;
                }
            } else {
                baos.write(b);
            }
        }


        @Override
        public void write(byte[] b) throws IOException {
            wrapped.write(b);

            if (baos.size() + b.length > capacityThreshold) {
                if (isComplete) {
                    baos.write(b, 0, capacityThreshold - baos.size());
                    isComplete = false;
                }

            } else {
                baos.write(b);
            }
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            wrapped.write(b, off, len);

            if (baos.size() + len > capacityThreshold) {
                if (isComplete) {
                    baos.write(b, off, capacityThreshold - baos.size());
                    isComplete = false;
                }
            } else {
                baos.write(b, off, len);
            }
        }

        @Override
        public void flush() throws IOException {
            wrapped.flush();
            baos.flush();
        }

        @Override
        public void close() throws IOException {
            wrapped.close();
            baos.close();
        }

    }

}
