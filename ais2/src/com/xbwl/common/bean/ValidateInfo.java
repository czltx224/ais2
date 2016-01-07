package com.xbwl.common.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ValidateInfo {
	private boolean success = true;
	
	private LinkedHashMap<String,String> errors = new LinkedHashMap<String,String>();
	
	private String value;
	
	private String msg;
	
	private List<String> errorInfos = new ArrayList<String>();

	public List<String> getErrorInfos() {
		return errorInfos;
	}

	public void setErrorInfos(List<String> errorInfos) {
		this.errorInfos = errorInfos;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public LinkedHashMap<String, String> getErrors() {
		return errors;
	}

	public void setErrors(LinkedHashMap<String, String> errors) {
		this.errors = errors;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
