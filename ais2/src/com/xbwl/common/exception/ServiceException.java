package com.xbwl.common.exception;

/**
 * 这个是业务逻辑错误抛出的异常，用于业务处理，一般不放在前台显示
 * @author Administrator
 *
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 3932944684989538887L;
	
	private String errorCode;
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String errorCode,String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public ServiceException(Throwable cause) {
		super(cause);
		// REVIEW 这个时候错误码为空，应当设置一个默认错误码
	}
}
