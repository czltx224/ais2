package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * author CaoZhili time Oct 25, 2011 3:18:15 PM
 * 预配清单实体类
 */
@XBlinkAlias("billLading")
public class PrewiredListBean extends PrintBean {

	private String startDepartName;// 出发部门
	private String prewiredId;// 预配单号
	private String dno;// 配送单号
	private String consigneeInfo;// 收货人信息
	private String toWhere;// 预配去向
	private String autostowMode;// 配载方式
	private String piece;// 件数
	private String weight;// 重量
	private String votes;// 票数
	private String cqName;// 发货代理
	private String flightMainNo;// 主单号
	private String subNo;// 分单号
	private String flightInfo;// 航班信息（航班号+始发城市）
	private String goodsStatus;// 货物状态
	private String stockCountTime;// 在库时间
	private String stockArea;// 库存区域
	private String receiptNum;// 原件份数2l
	private String totalWeight;// 总重量
	private String totalPiece;// 总件数
	private String totalTicket;// 总票数

	private String printName;// 打印人
	private Long printNum;// 打印次数
	private String printTime;// 打印时间
	private String printId;// 打印记录表ID
	private String sourceNo;// 来源ID
	
	public String getStartDepartName() {
		return startDepartName;
	}
	public void setStartDepartName(String startDepartName) {
		this.startDepartName = startDepartName;
	}
	public String getPrewiredId() {
		return prewiredId;
	}
	public void setPrewiredId(String prewiredId) {
		this.prewiredId = prewiredId;
	}
	public String getDno() {
		return dno;
	}
	public void setDno(String dno) {
		this.dno = dno;
	}
	public String getConsigneeInfo() {
		return consigneeInfo;
	}
	public void setConsigneeInfo(String consigneeInfo) {
		this.consigneeInfo = consigneeInfo;
	}
	public String getToWhere() {
		return toWhere;
	}
	public void setToWhere(String toWhere) {
		this.toWhere = toWhere;
	}
	public String getAutostowMode() {
		return autostowMode;
	}
	public void setAutostowMode(String autostowMode) {
		this.autostowMode = autostowMode;
	}
	
	public String getPiece() {
		return piece;
	}
	public void setPiece(String piece) {
		this.piece = piece;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getVotes() {
		return votes;
	}
	public void setVotes(String votes) {
		this.votes = votes;
	}
	public String getCqName() {
		return cqName;
	}
	public void setCqName(String cqName) {
		this.cqName = cqName;
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
	public String getFlightInfo() {
		return flightInfo;
	}
	public void setFlightInfo(String flightInfo) {
		this.flightInfo = flightInfo;
	}
	public String getGoodsStatus() {
		return goodsStatus;
	}
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	public String getStockCountTime() {
		return stockCountTime;
	}
	public void setStockCountTime(String stockCountTime) {
		this.stockCountTime = stockCountTime;
	}
	public String getStockArea() {
		return stockArea;
	}
	public void setStockArea(String stockArea) {
		this.stockArea = stockArea;
	}
	public String getReceiptNum() {
		return receiptNum;
	}
	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	public String getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}
	public String getTotalPiece() {
		return totalPiece;
	}
	public void setTotalPiece(String totalPiece) {
		this.totalPiece = totalPiece;
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

	
}
