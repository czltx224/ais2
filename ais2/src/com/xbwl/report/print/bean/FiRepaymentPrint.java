package com.xbwl.report.print.bean;

import java.util.Date;

import org.xblink.annotation.XBlinkAlias;

/**
 * 代理还款交账打印
 * author shuw
 * time May 18, 2012 5:07:38 PM
 */
@XBlinkAlias("billLading")
public class FiRepaymentPrint extends PrintBean{

	private Long batchNo;//交账单号
	private Date accountData;//交账日期
	private String accountDataString;//交账日期
	private Long customerId;//客商ID
	private String customerName;//客商ID
	private Double accountsBalance;//欠款金额
	private Double eliminationAccounts;//还款金额
	private Double eliminationCope;//代收货款金额
	private Double problemAmount;//问题账款金额
	private String sourceData;//数据来源
	private Long id;
	
	private String departName;
	private String printHead;
	
	private String printName;// 打印人
	private Long printNum=0l;// 打印次数
	private String printTime;// 打印时间
	private String printId;// 打印记录表ID
	private String sourceNo;// 来源ID
	
	public FiRepaymentPrint(){
		
	}
	
	public FiRepaymentPrint(Long batchNo, Date accountData, Long customerId,
			String customerName, Double accountsBalance,
			Double eliminationAccounts, Double eliminationCope,
			Double problemAmount, String sourceData, String departName) {
		super();
		this.batchNo = batchNo;
		this.accountData = accountData;
		this.customerId = customerId;
		this.customerName = customerName;
		this.accountsBalance = accountsBalance;
		this.eliminationAccounts = eliminationAccounts;
		this.eliminationCope = eliminationCope;
		this.problemAmount = problemAmount;
		this.sourceData = sourceData;
		this.departName = departName;
	}

	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

	public Long getPrintNum() {
		return printNum;
	}

	public void setPrintNum(Long printNum) {
		this.printNum = printNum;
	}

	public String getPrintTime() {
		return printTime;
	}

	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}

	public String getPrintId() {
		return printId;
	}

	public void setPrintId(String printId) {
		this.printId = printId;
	}

	public String getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}

	public Long getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

	public Date getAccountData() {
		return accountData;
	}

	public void setAccountData(Date accountData) {
		this.accountData = accountData;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Double getAccountsBalance() {
		return accountsBalance;
	}

	public void setAccountsBalance(Double accountsBalance) {
		this.accountsBalance = accountsBalance;
	}

	public Double getEliminationAccounts() {
		return eliminationAccounts;
	}

	public void setEliminationAccounts(Double eliminationAccounts) {
		this.eliminationAccounts = eliminationAccounts;
	}

	public Double getEliminationCope() {
		return eliminationCope;
	}

	public void setEliminationCope(Double eliminationCope) {
		this.eliminationCope = eliminationCope;
	}

	public Double getProblemAmount() {
		return problemAmount;
	}

	public void setProblemAmount(Double problemAmount) {
		this.problemAmount = problemAmount;
	}

	public String getSourceData() {
		return sourceData;
	}

	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getPrintHead() {
		return printHead;
	}

	public void setPrintHead(String printHead) {
		this.printHead = printHead;
	}

	public String getAccountDataString() {
		return accountDataString;
	}

	public void setAccountDataString(String accountDataString) {
		this.accountDataString = accountDataString;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
