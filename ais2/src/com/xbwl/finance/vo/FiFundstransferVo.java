package com.xbwl.finance.vo;

import com.xbwl.entity.FiFundstransfer;

public class FiFundstransferVo extends FiFundstransfer {
	private String paymentAccountNum; //付款账号
	private String paymentAccountName; //付款账号名称
	private String paymentAank; //付款开户行
	
	
	private String receivablesAccountNum; //付款账号
	private String receivablesAccountName; //付款账号名称
	private String receivablesAank; //付款开户行
	private String receivablesResponsible; //收款负责人
	
	public String getPaymentAccountNum() {
		return paymentAccountNum;
	}
	public void setPaymentAccountNum(String paymentAccountNum) {
		this.paymentAccountNum = paymentAccountNum;
	}
	public String getPaymentAccountName() {
		return paymentAccountName;
	}
	public void setPaymentAccountName(String paymentAccountName) {
		this.paymentAccountName = paymentAccountName;
	}
	public String getPaymentAank() {
		return paymentAank;
	}
	public void setPaymentAank(String paymentAank) {
		this.paymentAank = paymentAank;
	}

	public String getReceivablesAccountNum() {
		return receivablesAccountNum;
	}
	public void setReceivablesAccountNum(String receivablesAccountNum) {
		this.receivablesAccountNum = receivablesAccountNum;
	}
	public String getReceivablesAccountName() {
		return receivablesAccountName;
	}
	public void setReceivablesAccountName(String receivablesAccountName) {
		this.receivablesAccountName = receivablesAccountName;
	}

	public String getReceivablesAank() {
		return receivablesAank;
	}
	public void setReceivablesAank(String receivablesAank) {
		this.receivablesAank = receivablesAank;
	}
	public String getReceivablesResponsible() {
		return receivablesResponsible;
	}
	public void setReceivablesResponsible(String receivablesResponsible) {
		this.receivablesResponsible = receivablesResponsible;
	}
	
	
}
