package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 *  中转成本打印
 * author shuw
 * time May 14, 2012 10:01:42 AM
 */
@XBlinkAlias("billLading")
public class FiTrainCostBean  extends PrintBean {
	
	
	private String batchNo;   //批次号
	private Long dno;  //配送单号
	private String consigneeInfo;    //收货人信息
	private String customer;  //中转客商
	private Long piece;// 件数
	private Double  weight;//重量
	private Double amount;  // 金额
	private String sourceData ;// 数据不源
	private Long realSourceNo;  //       来源单号
	
	private Long totalPiece;           //总件数
	private Double totalWeight;        //总重量
	private Double totalAmount;

	private String printDateString;  //打印时间
	private String printName;// 打印人
	private Long printNum=0l;// 打印次数
	private String printTime;// 打印时间
	private String printId;// 打印记录表ID
	private String sourceNo;// 来源ID
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
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Long getDno() {
		return dno;
	}
	public void setDno(Long dno) {
		this.dno = dno;
	}
	public String getConsigneeInfo() {
		return consigneeInfo;
	}
	public void setConsigneeInfo(String consigneeInfo) {
		this.consigneeInfo = consigneeInfo;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public Long getPiece() {
		return piece;
	}
	public void setPiece(Long piece) {
		this.piece = piece;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getSourceData() {
		return sourceData;
	}
	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}
	public Long getRealSourceNo() {
		return realSourceNo;
	}
	public void setRealSourceNo(Long realSourceNo) {
		this.realSourceNo = realSourceNo;
	}
	public Long getTotalPiece() {
		return totalPiece;
	}
	public void setTotalPiece(Long totalPiece) {
		this.totalPiece = totalPiece;
	}
	public Double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getPrintDateString() {
		return printDateString;
	}
	public void setPrintDateString(String printDateString) {
		this.printDateString = printDateString;
	}


}
