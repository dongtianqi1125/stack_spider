package com.esy.stack.exception;
/**
 * 下载失败异常
 * @author guanjie
 *
 */
public class DownLoadException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DownLoadException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DownLoadException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public DownLoadException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DownLoadException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DownLoadException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
