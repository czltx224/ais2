package com.xbwl.common.bean;

public class ValidatedExcel<T> {
	private Boolean success = true;
	
	public Boolean getSuccess() {
		return success;
	}
	
	public T entity;

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private String msg;
}
