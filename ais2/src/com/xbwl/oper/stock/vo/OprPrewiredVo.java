package com.xbwl.oper.stock.vo;
/**
 * author shuw
 * time Jul 27, 2011 4:22:07 PM
 */

public class OprPrewiredVo {
	
	private String receiptType;//回单类型
	private Long  dno;//配送单号
	private Long  piece;//库存件数
	private Long reachNum;//回单点到份数
	private Long realPiece;//实配件数
	private Long wId;//预配单号
	private Double weight;//重量
	private Double consigneeFee;//到付提送费
	private Double cpFee;//预付提送费
	private Double traFee;//中转费
	private Double paymentCollection;//代收货款
	private String departName;//部门名称
	private Long departId;//部门编号
	private String autostowMode;//配送方式
	private String telPhone;  //电话号码
	
	public String getReceiptType() {
		return receiptType;
	}
	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}
	public Long getDno() {
		return dno;
	}
	public void setDno(Long dno) {
		this.dno = dno;
	}
	public Long getPiece() {
		return piece;
	}
	public void setPiece(Long piece) {
		this.piece = piece;
	}
	public Long getRealPiece() {
		return realPiece;
	}
	public void setRealPiece(Long realPiece) {
		this.realPiece = realPiece;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getConsigneeFee() {
		return consigneeFee;
	}
	public void setConsigneeFee(Double consigneeFee) {
		this.consigneeFee = consigneeFee;
	}
	public Double getCpFee() {
		return cpFee;
	}
	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}
	public Double getTraFee() {
		return traFee;
	}
	public void setTraFee(Double traFee) {
		this.traFee = traFee;
	}
	public Double getPaymentCollection() {
		return paymentCollection;
	}
	public void setPaymentCollection(Double paymentCollection) {
		this.paymentCollection = paymentCollection;
	}
	public Long getWId() {
		return wId;
	}
	public void setWId(Long id) {
		wId = id;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	public Long getReachNum() {
		return reachNum;
	}
	public void setReachNum(Long reachNum) {
		this.reachNum = reachNum;
	}
	public String getAutostowMode() {
		return autostowMode;
	}
	public void setAutostowMode(String autostowMode) {
		this.autostowMode = autostowMode;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	 
}
