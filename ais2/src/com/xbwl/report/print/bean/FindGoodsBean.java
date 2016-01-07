package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * author CaoZhili time Nov 3, 2011 8:52:06 AM
 * �һ���ʵ����
 */
@XBlinkAlias("billLading")
public class FindGoodsBean extends PrintBean {

	private String traceTo;// ʵ��ȥ��
	private String dno;// ���͵���
	private String cqName;// ��������
	private String flightInfo;// ������Ϣ
	private String reachDate;// ��������
	private String takeMode;// �����ʽ
	private String flightMainNo;// ������
	private String subNo;// �ֵ���
	private String consigneeInfo;// �ջ�����Ϣ
	private String weight;// ����
	private String piece;// ��������
	private String consigneeFee;// ���ͻ��ѣ��������ͷ�consigneeFee+������ֵ�����cpValueAddFee��
	private String paymentCollection;// �����˷�(���ջ���)
	private String countFee;// �ϼ�
	private String distributionMode;// ���ͷ�ʽ
	private String receiptType;// �ص�����
	private String stockPiece;// ������
	private String goodsStatus;// ����״̬
	private String requestString;// ���Ի�Ҫ��
	private String exceptionString;// �쳣
	private String stockArea;//�������
	private String printTitle;//��ǰ��ӡ����
	private String remarkString;//��ע

	private String printName;// ��ӡ��
	private Long printNum;// ��ӡ����
	private String printTime;// ��ӡʱ��
	private String printId;// ��ӡ��¼��ID
	private String sourceNo;// ��ԴID

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
