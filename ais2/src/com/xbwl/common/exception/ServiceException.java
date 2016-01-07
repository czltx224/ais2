package com.xbwl.common.exception;

/**
 * �����ҵ���߼������׳����쳣������ҵ����һ�㲻����ǰ̨��ʾ
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
		// REVIEW ���ʱ�������Ϊ�գ�Ӧ������һ��Ĭ�ϴ�����
	}
}
