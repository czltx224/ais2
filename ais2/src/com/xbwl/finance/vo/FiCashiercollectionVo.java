package com.xbwl.finance.vo;

import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.entity.FiCashiercollection;

public class FiCashiercollectionVo extends FiCashiercollection {

	private static final long serialVersionUID = 1L;
	private String departName;// ���ű��������ű�����
	private Long departId; // �ʽ��˺����ñ�:����
	private String accountNum; // �ʽ��˺����ñ�:�˺�
	private String accountName; // :�ʽ��˺����ñ��˺�����
	private String bank; // :�ʽ��˺����ñ�����
	private String responsible; // :������


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
