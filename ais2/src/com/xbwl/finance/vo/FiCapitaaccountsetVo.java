package com.xbwl.finance.vo;

import com.xbwl.entity.FiCapitaaccountset;

public class FiCapitaaccountsetVo extends FiCapitaaccountset {
	private String paymentTypeName; //��֧����
	private String accountTypeName; //�˺�����
	private String departName;// �������ű�����
	
	private String ownedBankName;//������������
	
	public String getPaymentTypeName() {
		return paymentTypeName;
	}
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}
	public String getAccountTypeName() {
		return accountTypeName;
	}
	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getOwnedBankName() {
		return ownedBankName;
	}
	public void setOwnedBankName(String ownedBankName) {
		this.ownedBankName = ownedBankName;
	}
	
	
}
