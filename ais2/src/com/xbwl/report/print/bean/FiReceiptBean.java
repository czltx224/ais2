package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * author shuw * 收据打印
 * time Dec 6, 2011 2:19:44 PM
 */
@XBlinkAlias("billLading")
public class FiReceiptBean extends PrintBean {
	
	private String creataRemark;   // 摘要
	private String creataRemark2;   // 摘要2
	private String creataRemark3;   // 摘要3
	private String outName;  // 出纳
	private String doName;  // 经手人
	private String amount;   // 金额
	private String printReceiptAccountNo;  //打印流水号
	private String maxAmount;   // 大写金额合计
	private String receiptData;  //收据日期
	private String cashStatus="1"; //判断是否是现金还是转账  1:现金 2:汇款 3:支票 4:其他
	private Long receiptId;  //收据单号
	private String printPhoto;
	
	private String printName;// 打印人
	private Long printNum=0l;// 打印次数
	private String printTime;// 打印时间
	private String printId;// 打印记录表ID
	private String sourceNo;// 来源ID
	
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
