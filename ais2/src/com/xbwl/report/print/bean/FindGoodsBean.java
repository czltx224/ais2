package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * author CaoZhili time Nov 3, 2011 8:52:06 AM
 * 找货单实体类
 */
@XBlinkAlias("billLading")
public class FindGoodsBean extends PrintBean {

	private String traceTo;// 实际去向
	private String dno;// 配送单号
	private String cqName;// 发货代理
	private String flightInfo;// 航班信息
	private String reachDate;// 到达日期
	private String takeMode;// 提货方式
	private String flightMainNo;// 主单号
	private String subNo;// 分单号
	private String consigneeInfo;// 收货人信息
	private String weight;// 重量
	private String piece;// 开单件数
	private String consigneeFee;// 提送货费（到付提送费consigneeFee+到付增值服务费cpValueAddFee）
	private String paymentCollection;// 到付运费(代收货款)
	private String countFee;// 合计
	private String distributionMode;// 配送方式
	private String receiptType;// 回单类型
	private String stockPiece;// 库存件数
	private String goodsStatus;// 货物状态
	private String requestString;// 个性化要求
	private String exceptionString;// 异常
	private String stockArea;//库存区域
	private String printTitle;//当前打印部门
	private String remarkString;//备注

	private String printName;// 打印人
	private Long printNum;// 打印次数
	private String printTime;// 打印时间
	private String printId;// 打印记录表ID
	private String sourceNo;// 来源ID

	public FindGoodsBean(){
		this.printNum=0l;
	}
	
	public String getTraceTo() {
		return traceTo;
	}

	public void setTraceTo(String traceTo) {
		this.traceTo = traceTo;
	}

	public String getDno() {
		return dno;
	}

	public void setDno(String dno) {
		this.dno = dno;
	}

	public String getCqName() {
		return cqName;
	}

	public void setCqName(String cqName) {
		this.cqName = cqName;
	}

	public String getFlightInfo() {
		return flightInfo;
	}

	public void setFlightInfo(String flightInfo) {
		this.flightInfo = flightInfo;
	}

	public String getReachDate() {
		return reachDate;
	}

	public void setReachDate(String reachDate) {
		this.reachDate = reachDate;
	}

	public String getTakeMode() {
		return takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
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

	public String getConsigneeFee() {
		return consigneeFee;
	}

	public void setConsigneeFee(String consigneeFee) {
		this.consigneeFee = consigneeFee;
	}

	public String getPaymentCollection() {
		return paymentCollection;
	}

	public void setPaymentCollection(String paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

	public String getCountFee() {
		return countFee;
	}

	public void setCountFee(String countFee) {
		this.countFee = countFee;
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

	public String getDistributionMode() {
		return distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public String getStockPiece() {
		return stockPiece;
	}

	public void setStockPiece(String stockPiece) {
		this.stockPiece = stockPiece;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public String getRequestString() {
		return requestString;
	}

	public void setRequestString(String requestString) {
		this.requestString = requestString;
	}

	public String getExceptionString() {
		return exceptionString;
	}

	public void setExceptionString(String exceptionString) {
		this.exceptionString = exceptionString;
	}

	public String getStockArea() {
		return stockArea;
	}

	public void setStockArea(String stockArea) {
		this.stockArea = stockArea;
	}

	public String getPrintTitle() {
		return printTitle;
	}

	public void setPrintTitle(String printTitle) {
		this.printTitle = printTitle;
	}

	public String getRemarkString() {
		return remarkString;
	}

	public void setRemarkString(String remarkString) {
		this.remarkString = remarkString;
	}

}
