package com.xbwl.finance.vo;

import com.xbwl.entity.FiAdvancebp;

public class FiAdvancebpVo extends FiAdvancebp {
	private Long accountNum; //�˺�
	private String accountName;//�˺�����
	private String bank;//������
	private Long departId;//��������
	private String departName;  //��������
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
