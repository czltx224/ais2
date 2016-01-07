package com.xbwl.finance.vo;

import com.xbwl.entity.FiCapitaaccountset;

public class FiCapitaaccountsetVo extends FiCapitaaccountset {
	private String paymentTypeName; //收支类型
	private String accountTypeName; //账号类型
	private String departName;// 所属部门表名称
	
	private String ownedBankName;//所属银行名称
	
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
