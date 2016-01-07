package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * author CaoZhili time Nov 3, 2011 5:07:30 PM
 * ����嵥ʵ����
 */
@XBlinkAlias("billLading")
public class ReachGroupReportBean extends PrintBean {

	private String carCode;// ���ƺ�
	private String overmemoNo;// ���ӵ���
	private String dno;// ���͵���
	private String flightMainNo;// ������
	private String subNo;// �ֵ���
	private String cqName;// ���� ��ȥ��
	private String consigneeInfo;// �ջ�����Ϣ
	private String weight;// ����
	private String piece;// Ӧ������
	private String realPiece;// ʵ������
	private String isNotReceipt;// �Ƿ�ԭ��ǩ��
	private String receiptType;// �ص�����
	private String downCarArea;// �³���
	private String exceptionRemark;// �쳣/��ע
	private String cqArea;//�������
	private String request;//���Ի�Ҫ��
	private String isOpr;//���Ի�Ҫ��ִ�����
	private String printTitle;//��ӡ����
	private String flightNo;//�����
	
	private String totalPiece;// �ܼ���
	private String totalWeight;// ������
	private String totalTicket;// ��Ʊ��

	private String printName;// ��ӡ��
	private Long printNum;// ��ӡ����
	private String printTime;// ��ӡʱ��
	private String printId;// ��ӡ��¼��ID
	private String sourceNo;// ��ԴID
	
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
