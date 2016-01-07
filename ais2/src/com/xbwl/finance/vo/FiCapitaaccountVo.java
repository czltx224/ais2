package com.xbwl.finance.vo;

import com.xbwl.entity.FiCapitaaccount;

public class FiCapitaaccountVo extends FiCapitaaccount{
	

	private static final long serialVersionUID = 1L;
	private String paymentTypeName; //数据字典：收支类型
	private String accountTypeName; //数据字典：账号类型
	private String departName;// 部门表：所属部门表名称
	private Long departId; //资金账号设置表:部门
	private String accountNum; //资金账号设置表:账号
	private String accountName; //:资金账号设置表账号名称
	private String bank; //:资金账号设置表开户行
	private String responsible; //:负责人

	
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
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getResponsible() {
		return responsible;
	}
	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
	
}
