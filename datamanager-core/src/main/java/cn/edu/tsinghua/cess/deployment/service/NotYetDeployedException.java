package cn.edu.tsinghua.cess.deployment.service;

/**
 * Created by kurt on 2014/10/6.
 */
public class NotYetDeployedException extends IllegalStateException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -496854360636131398L;

	public NotYetDeployedException() {
        super("this server has not been deployed yet");
    }

}
