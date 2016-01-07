package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * author shuw
 * ���𽻽ӵ�Bean
 * time Dec 5, 2011 10:34:43 AM
 */
@XBlinkAlias("billLading")
public class FiFundstransferBean extends PrintBean {

	private String payDepartName;  //�����
	private String  incomeDepartName; //�տ��
	private String creataRemark;   // ժҪ
	private String payAccountNo;  // �����˺�
	private String incomeAccountNo;  // �����˺�
	private String amount;   // ���
	private String printAccountNo;  //��ӡ��ˮ��
	private String maxAmount;   // ��д���ϼ�
	private String payData;  //��������
	private String cashStatus="1"; //�ж��Ƿ����ֽ���ת��  1:�ֽ� 2:���
	
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

	public String getPayDepartName() {
		return payDepartName;
	}

	public void setPayDepartName(String payDepartName) {
		this.payDepartName = payDepartName;
	}

	public String getIncomeDepartName() {
		return incomeDepartName;
	}

	public void setIncomeDepartName(String incomeDepartName) {
		this.incomeDepartName = incomeDepartName;
	}

	public String getCreataRemark() {
		return creataRemark;
	}

	public void setCreataRemark(String creataRemark) {
		this.creataRemark = creataRemark;
	}

	public String getPayAccountNo() {
		return payAccountNo;
	}

	public void setPayAccountNo(String payAccountNo) {
		this.payAccountNo = payAccountNo;
	}

	public String getIncomeAccountNo() {
		return incomeAccountNo;
	}

	public void setIncomeAccountNo(String incomeAccountNo) {
		this.incomeAccountNo = incomeAccountNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getPayData() {
		return payData;
	}

	public void setPayData(String payData) {
		this.payData = payData;
	}

	public String getPrintAccountNo() {
		return printAccountNo;
	}

	public void setPrintAccountNo(String printAccountNo) {
		this.printAccountNo = printAccountNo;
	}

	public String getCashStatus() {
		return cashStatus;
	}

	public void setCashStatus(String cashStatus) {
		this.cashStatus = cashStatus;
	}

}
