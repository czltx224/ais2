package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * author shuw * �վݴ�ӡ
 * time Dec 6, 2011 2:19:44 PM
 */
@XBlinkAlias("billLading")
public class FiReceiptBean extends PrintBean {
	
	private String creataRemark;   // ժҪ
	private String creataRemark2;   // ժҪ2
	private String creataRemark3;   // ժҪ3
	private String outName;  // ����
	private String doName;  // ������
	private String amount;   // ���
	private String printReceiptAccountNo;  //��ӡ��ˮ��
	private String maxAmount;   // ��д���ϼ�
	private String receiptData;  //�վ�����
	private String cashStatus="1"; //�ж��Ƿ����ֽ���ת��  1:�ֽ� 2:��� 3:֧Ʊ 4:����
	private Long receiptId;  //�վݵ���
	private String printPhoto;
	
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

	public String getCreataRemark() {
		return creataRemark;
	}

	public void setCreataRemark(String creataRemark) {
		this.creataRemark = creataRemark;
	}

	public String getOutName() {
		return outName;
	}

	public void setOutName(String outName) {
		this.outName = outName;
	}

	public String getDoName() {
		return doName;
	}

	public void setDoName(String doName) {
		this.doName = doName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPrintReceiptAccountNo() {
		return printReceiptAccountNo;
	}

	public void setPrintReceiptAccountNo(String printReceiptAccountNo) {
		this.printReceiptAccountNo = printReceiptAccountNo;
	}

	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getReceiptData() {
		return receiptData;
	}

	public void setReceiptData(String receiptData) {
		this.receiptData = receiptData;
	}

	public String getCashStatus() {
		return cashStatus;
	}

	public void setCashStatus(String cashStatus) {
		this.cashStatus = cashStatus;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public String getCreataRemark2() {
		return creataRemark2;
	}

	public void setCreataRemark2(String creataRemark2) {
		this.creataRemark2 = creataRemark2;
	}

	public String getCreataRemark3() {
		return creataRemark3;
	}

	public void setCreataRemark3(String creataRemark3) {
		this.creataRemark3 = creataRemark3;
	}

	public String getPrintPhoto() {
		return printPhoto;
	}

	public void setPrintPhoto(String printPhoto) {
		this.printPhoto = printPhoto;
	}


}
