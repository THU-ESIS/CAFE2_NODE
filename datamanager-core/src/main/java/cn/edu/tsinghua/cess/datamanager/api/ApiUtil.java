package cn.edu.tsinghua.cess.datamanager.api;

import java.util.concurrent.Callable;

import cn.edu.tsinghua.cess.component.exception.ExceptionHandler;
import org.apache.log4j.Logger;

/**
 * @author kurtyan777@gmail.com
 */
public class ApiUtil {
	
	private static Logger log = Logger.getLogger(ApiUtil.class);
	
	public static ApiResult execute(Callable<Object> callable) {
		ApiResult result = new ApiResult();
		
		try {
			Object data = callable.call();
			
			result.setSuccess(true);
			result.setData(data);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			
			result.setSuccess(false);
			result.setErrorMsg(ExceptionHandler.printStackTrace(e));
		}
		
		return result;
	}
	
}
