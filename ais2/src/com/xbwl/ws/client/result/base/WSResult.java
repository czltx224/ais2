package com.xbwl.ws.client.result.base;

/**
 * WebService���ؽ������,�������з�����.
 * 
 * @author calvin
 */
public class WSResult implements java.io.Serializable{
	

	//-- ���ش��붨�� --//
	// ����Ŀ�Ĺ�����ж���, ����4xx����ͻ��˲�������5xx��������ҵ������.
	public static final String SUCCESS = "0";
	public static final String PARAMETER_ERROR = "400";

	public static final String SYSTEM_ERROR = "500";
	public static final String SYSTEM_ERROR_MESSAGE = "Runtime unknown internal error.";

	//-- WSResult�������� --//
	private String code = SUCCESS;
	private String message;

	/**
	* �������.
	*/
	public <T extends WSResult> T setError(String resultCode, String resultMessage) {
		code = resultCode;
		message = resultMessage;
		return (T) this;
	}

	/**
	 * ����Ĭ���쳣���.
	 */
	public <T extends WSResult> T setDefaultError() {
		return (T) setError(SYSTEM_ERROR, SYSTEM_ERROR_MESSAGE);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
