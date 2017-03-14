package com.esy.stack.exception;
/**
 * 处理异常
 * @author guanjie
 *
 */
public class ProcessExecption extends Exception{

	private static final long serialVersionUID = 1L;

	public ProcessExecption() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProcessExecption(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ProcessExecption(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ProcessExecption(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ProcessExecption(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
