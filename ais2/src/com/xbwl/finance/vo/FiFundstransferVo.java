package com.xbwl.finance.vo;

import com.xbwl.entity.FiFundstransfer;

public class FiFundstransferVo extends FiFundstransfer {
	private String paymentAccountNum; //�����˺�
	private String paymentAccountName; //�����˺�����
	private String paymentAank; //�������
	
	
	private String receivablesAccountNum; //�����˺�
	private String receivablesAccountName; //�����˺�����
	private String receivablesAank; //�������
	private String receivablesResponsible; //�տ����
	
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
