package com.xbwl.common.exception;

/**
 * 封装到前台显示的异常
 * @author Administrator
 *
 */
public class ActionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ActionException() {
		super();
	}

	public ActionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ActionException(String message) {
		super(message);
	}

	public ActionException(Throwable cause) {
		super(cause);
	}
}
