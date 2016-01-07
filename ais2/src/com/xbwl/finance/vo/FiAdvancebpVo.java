package com.xbwl.finance.vo;

import com.xbwl.entity.FiAdvancebp;

public class FiAdvancebpVo extends FiAdvancebp {
	private Long accountNum; //账号
	private String accountName;//账号名称
	private String bank;//开户行
	private Long departId;//所属部门
	private String departName;  //部门名称
	public Long getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(Long accountNum) {
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
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
	
}
