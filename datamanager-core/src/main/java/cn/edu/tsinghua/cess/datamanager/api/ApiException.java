package cn.edu.tsinghua.cess.datamanager.api;

/**
 * Created by kurt on 2014/9/6.
 */
public class ApiException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6931635334335036938L;

	public ApiException(String message) {
        super(message);
    }

}
