package dto;

import java.io.Serializable;

/**
 * @author Administrator
 * @createTime 11:50:25 AM
 * @updateName Administrator
 * @updateTime 11:50:25 AM
 * 
 */

public class HessianResult implements Serializable {
	
	public HessianResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static final String SUCCESS = "0";
	public static final String PARAMETER_ERROR = "400";

	public static final String SYSTEM_ERROR = "500";
	public static final String SYSTEM_ERROR_MESSAGE = "Runtime unknown internal error.";
	//REVIEW ÂÒÂë£¬ÇëÐÞÕý
	//-- WSResultåŸºæœ¬å±žæ?? --//
	private String code = SUCCESS;
	private String message="success";
	
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
	public static String getSUCCESS() {
		return SUCCESS;
	}
	public static String getPARAMETER_ERROR() {
		return PARAMETER_ERROR;
	}
	public static String getSYSTEM_ERROR() {
		return SYSTEM_ERROR;
	}
	public static String getSYSTEM_ERROR_MESSAGE() {
		return SYSTEM_ERROR_MESSAGE;
	}
	public HessianResult(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

}
