package com.xbwl.finance.vo;

import java.util.Date;

import com.xbwl.entity.FiArrearset;
import com.xbwl.entity.FiInvoice;
public class FiInvoiceVo extends FiInvoice {

	private String departName;// ���ű�����
	private String cusName;// ���̱�����
	
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
}
