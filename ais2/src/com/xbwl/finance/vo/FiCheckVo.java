package com.xbwl.finance.vo;

import com.xbwl.entity.FiCheck;

public class FiCheckVo extends FiCheck {
	private String accountNum; //�ʽ��˺����ñ�:�˺�
	private String accountName; //:�ʽ��˺����ñ��˺�����
	private String bank; //:�ʽ��˺����ñ�����
	private String responsible; //:������
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


	
}
