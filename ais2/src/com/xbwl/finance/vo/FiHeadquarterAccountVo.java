package com.xbwl.finance.vo;

import com.xbwl.entity.FiHeadquarterAccount;

public class FiHeadquarterAccountVo extends FiHeadquarterAccount {
	private String accountNum; //�ʽ��˺����ñ�:�˺�
	private String accountName; //:�ʽ��˺����ñ��˺�����
	private String bank; //:�ʽ��˺����ñ�����
	private String responsible; //:������
	private Long responsibleId; //������id
	
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
	public Long getResponsibleId() {
		return responsibleId;
	}
	public void setResponsibleId(Long responsibleId) {
		this.responsibleId = responsibleId;
	}
	
}
