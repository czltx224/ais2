package com.xbwl.finance.vo;

import java.util.Date;

import com.xbwl.entity.FiArrearset;
public class FiArrearsetVo extends FiArrearset {

	private String departName;// ���ű�����
	private String cusName;// ���̱�����
	private String custprop;
	
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getCustprop() {
		return custprop;
	}
	public void setCustprop(String custprop) {
		this.custprop = custprop;
	}
	
	
}
