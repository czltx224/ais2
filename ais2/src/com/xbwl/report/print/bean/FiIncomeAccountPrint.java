package com.xbwl.report.print.bean;

import java.util.Date;

import org.xblink.annotation.XBlinkAlias;

/**
 * �����������˴�ӡ
 * author shuw
 * time May 18, 2012 9:22:01 AM
 */
@XBlinkAlias("billLading")
public class FiIncomeAccountPrint extends PrintBean{
	
	private Long id;
	private Long batchNo;//���˵���
	private String typeName; //����(����\����)
	private Double cpFee;//Ԥ������
	private Double consigneeFee;//��������
	private Double incomeAmount;//�����ܶ�
	private Double cashAmount;//�ֽ�
	private Double posAmount;//POS
	private Double checkAmount;//֧Ʊ
	private Double intecollectionAmount;//�ڲ�����
	private Double eliminationAmount;//��������
	private Double collectionAmount;//���ջ���
	private Double consigneeAmount;//������
	private String accountStatus;//����״̬(0��δ����,1���ѽ���)
	private String accountData;//����

	private String printHead;
	
	private String printName;// ��ӡ��
	private Long printNum=0l;// ��ӡ����
	private String printTime;// ��ӡʱ��
	private String printId;// ��ӡ��¼��ID
	private String sourceNo;// ��ԴID
	
	public String getPrintId() {
		return printId;
	}

	public String getPrintName() {
		return printName;
	}

	public Long getPrintNum() {
		return printNum;
	}

	public String getPrintTime() {
		return printTime;
	}

	public String getSourceNo() {
		return sourceNo;
	}

	public void setPrintId(String printId) {
		this.printId=printId;
	}

	public void setPrintName(String printName) {
		this.printName=printName;
	}

	public void setPrintNum(Long printNum) {
		this.printNum=printNum;
	}

	public void setPrintTime(String printTime) {
		this.printTime=printTime;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo=sourceNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Double getCpFee() {
		return cpFee;
	}

	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}

	public Double getConsigneeFee() {
		return consigneeFee;
	}

	public void setConsigneeFee(Double consigneeFee) {
		this.consigneeFee = consigneeFee;
	}

	public Double getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(Double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	public Double getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(Double cashAmount) {
		this.cashAmount = cashAmount;
	}

	public Double getPosAmount() {
		return posAmount;
	}

	public void setPosAmount(Double posAmount) {
		this.posAmount = posAmount;
	}

	public Double getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(Double checkAmount) {
		this.checkAmount = checkAmount;
	}

	public Double getIntecollectionAmount() {
		return intecollectionAmount;
	}

	public void setIntecollectionAmount(Double intecollectionAmount) {
		this.intecollectionAmount = intecollectionAmount;
	}

	public Double getEliminationAmount() {
		return eliminationAmount;
	}

	public void setEliminationAmount(Double eliminationAmount) {
		this.eliminationAmount = eliminationAmount;
	}

	public Double getCollectionAmount() {
		return collectionAmount;
	}

	public void setCollectionAmount(Double collectionAmount) {
		this.collectionAmount = collectionAmount;
	}

	public Double getConsigneeAmount() {
		return consigneeAmount;
	}

	public void setConsigneeAmount(Double consigneeAmount) {
		this.consigneeAmount = consigneeAmount;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccountData() {
		return accountData;
	}

	public void setAccountData(String accountData) {
		this.accountData = accountData;
	}

	public String getPrintHead() {
		return printHead;
	}

	public void setPrintHead(String printHead) {
		this.printHead = printHead;
	}

	
}
