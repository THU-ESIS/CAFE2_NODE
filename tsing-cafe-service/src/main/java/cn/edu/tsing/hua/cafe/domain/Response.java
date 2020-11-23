package cn.edu.tsing.hua.cafe.domain;

/**
 * @author: snn
 * @created: 2019-01-26 17:54
 */
public class Response <T> {

    /**
     * 返回状态
     */
    private Boolean success;

    /**
     * 错误码
     */
    private String error;

    /**
     * 数据
     */
    private T data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Response(Boolean success) {
        this.success = success;
    }
}
