package com.xbwl.finance.vo;

import com.xbwl.entity.FiHeadquarterAccount;

public class FiHeadquarterAccountVo extends FiHeadquarterAccount {
	private String accountNum; //资金账号设置表:账号
	private String accountName; //:资金账号设置表账号名称
	private String bank; //:资金账号设置表开户行
	private String responsible; //:负责人
	private Long responsibleId; //负责人id
	
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
