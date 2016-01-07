package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * author CaoZhili time Nov 3, 2011 5:07:30 PM
 * 理货清单实体类
 */
@XBlinkAlias("billLading")
public class ReachGroupReportBean extends PrintBean {

	private String carCode;// 车牌号
	private String overmemoNo;// 交接单号
	private String dno;// 配送单号
	private String flightMainNo;// 主单号
	private String subNo;// 分单号
	private String cqName;// 代理 和去向
	private String consigneeInfo;// 收货人信息
	private String weight;// 重量
	private String piece;// 应到件数
	private String realPiece;// 实到件数
	private String isNotReceipt;// 是否原件签单
	private String receiptType;// 回单类型
	private String downCarArea;// 下车区
	private String exceptionRemark;// 异常/备注
	private String cqArea;//代理地区
	private String request;//个性化要求
	private String isOpr;//个性化要求执行情况
	private String printTitle;//打印标题
	private String flightNo;//航班号
	
	private String totalPiece;// 总件数
	private String totalWeight;// 总重量
	private String totalTicket;// 总票数

	private String printName;// 打印人
	private Long printNum;// 打印次数
	private String printTime;// 打印时间
	private String printId;// 打印记录表ID
	private String sourceNo;// 来源ID
	
	public ReachGroupReportBean(){
		this.carCode="";
		this.printNum=0l;
		this.isNotReceipt="";
		this.request="";
		this.isOpr="";
		this.cqArea="";
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getOvermemoNo() {
		return overmemoNo;
	}

	public void setOvermemoNo(String overmemoNo) {
		this.overmemoNo = overmemoNo;
	}

	public String getDno() {
		return dno;
	}

	public void setDno(String dno) {
		this.dno = dno;
	}

	public String getFlightMainNo() {
		return flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	public String getSubNo() {
		return subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

	public String getCqName() {
		return cqName;
	}

	public void setCqName(String cqName) {
		this.cqName = cqName;
	}

	public String getConsigneeInfo() {
		return consigneeInfo;
	}

	public void setConsigneeInfo(String consigneeInfo) {
		this.consigneeInfo = consigneeInfo;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPiece() {
		return piece;
	}

	public void setPiece(String piece) {
		this.piece = piece;
	}

	public String getRealPiece() {
		return realPiece;
	}

	public void setRealPiece(String realPiece) {
		this.realPiece = realPiece;
	}

	public String getIsNotReceipt() {
		return isNotReceipt;
	}

	public void setIsNotReceipt(String isNotReceipt) {
		this.isNotReceipt = isNotReceipt;
	}

	public String getDownCarArea() {
		return downCarArea;
	}

	public void setDownCarArea(String downCarArea) {
		this.downCarArea = downCarArea;
	}

	public String getExceptionRemark() {
		return exceptionRemark;
	}

	public void setExceptionRemark(String exceptionRemark) {
		this.exceptionRemark = exceptionRemark;
	}

	public String getCqArea() {
		return cqArea;
	}

	public void setCqArea(String cqArea) {
		this.cqArea = cqArea;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getIsOpr() {
		return isOpr;
	}

	public void setIsOpr(String isOpr) {
		this.isOpr = isOpr;
	}

	public String getTotalPiece() {
		return totalPiece;
	}

	public void setTotalPiece(String totalPiece) {
		this.totalPiece = totalPiece;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getTotalTicket() {
		return totalTicket;
	}

	public void setTotalTicket(String totalTicket) {
		this.totalTicket = totalTicket;
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

	public String getPrintTitle() {
		return printTitle;
	}

	public void setPrintTitle(String printTitle) {
		this.printTitle = printTitle;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}
}
