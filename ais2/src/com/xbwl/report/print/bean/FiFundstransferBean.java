package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * author shuw
 * 奖金交接单Bean
 * time Dec 5, 2011 10:34:43 AM
 */
@XBlinkAlias("billLading")
public class FiFundstransferBean extends PrintBean {

	private String payDepartName;  //付款部门
	private String  incomeDepartName; //收款部门
	private String creataRemark;   // 摘要
	private String payAccountNo;  // 付款账号
	private String incomeAccountNo;  // 付款账号
	private String amount;   // 金额
	private String printAccountNo;  //打印流水号
	private String maxAmount;   // 大写金额合计
	private String payData;  //交付日期
	private String cashStatus="1"; //判断是否是现金还是转账  1:现金 2:汇款
	
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
