package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * 库存盘点清仓单实体Bean
 * @project ais
 * @author czl
 * @Time Feb 25, 2012 11:39:09 AM
 */
@XBlinkAlias("billLading")
public class OprStockInventoryBean extends PrintBean{

	private String dno;//配送单号
	private String flightMainNo;// 主单号
	private String subNo;// 分单号
	private String cpName;//发货代理
	private String consigneeInfo;// 收货人信息
	private String weight;// 重量
	private String piece;// 录单件数
	private String stockPiece;//库存件数
	private String inventoryPiece;// 盘点件数
	private String flightNo;//航班号
	private String distributionMode;//配送方式
	private String takeMode;//配送方式
	private String printTitle;//库存盘点清仓单打印标题
	private String inventoryNum;//清仓单号
	private String stockArea;//库存区域
	
	private String printName;// 打印人
	private Long printNum;// 打印次数
	private String printTime;// 打印时间
	private String printId;// 打印记录表ID
	private String sourceNo;// 来源ID
	
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
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
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
	public String getStockPiece() {
		return stockPiece;
	}
	public void setStockPiece(String stockPiece) {
		this.stockPiece = stockPiece;
	}
	public String getInventoryPiece() {
		return inventoryPiece;
	}
	public void setInventoryPiece(String inventoryPiece) {
		this.inventoryPiece = inventoryPiece;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getDistributionMode() {
		return distributionMode;
	}
	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}
	public String getTakeMode() {
		return takeMode;
	}
	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
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
	public String getInventoryNum() {
		return inventoryNum;
	}
	public void setInventoryNum(String inventoryNum) {
		this.inventoryNum = inventoryNum;
	}
	public String getStockArea() {
		return stockArea;
	}
	public void setStockArea(String stockArea) {
		this.stockArea = stockArea;
	}
}
