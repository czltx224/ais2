package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * @author czl
 * @createTime 2012-03-17 04:14 AM
 * 
 * �������ķ�������ʵ����
 */
@XBlinkAlias("billLading")
public class OprReturnReceiptBean extends PrintBean {

	private String printTitle;//��ӡ����
	private String returnName;//��������/����/��
	private String dno;//���͵���
	private String flightMainNo;//������
	private String subNo;//�ֵ���
	private String consigneeInfo;//�ջ�����Ϣ
	private String piece;//����
	private String weight;//����
	private String signNum;//ǩ�յ�����
	private String returnNum;//��������
	private String confirmMan;//ȴ����
	private String confirmTime;//ȷ��ʱ��
	
	private String printName;// ��ӡ��
	private Long printNum;// ��ӡ����
	private String printTime;// ��ӡʱ��
	private String printId;// ��ӡ��¼��ID
	private String sourceNo;// ��ԴID
	
	public String getPrintTitle() {
		return printTitle;
	}
	public void setPrintTitle(String printTitle) {
		this.printTitle = printTitle;
	}
	public String getReturnName() {
		return returnName;
	}
	public void setReturnName(String returnName) {
		this.returnName = returnName;
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
	public String getConsigneeInfo() {
		return consigneeInfo;
	}
	public void setConsigneeInfo(String consigneeInfo) {
		this.consigneeInfo = consigneeInfo;
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
	public String getSignNum() {
		return signNum;
	}
	public void setSignNum(String signNum) {
		this.signNum = signNum;
	}
	public String getReturnNum() {
		return returnNum;
	}
	public void setReturnNum(String returnNum) {
		this.returnNum = returnNum;
	}
	public String getConfirmMan() {
		return confirmMan;
	}
	public void setConfirmMan(String confirmMan) {
		this.confirmMan = confirmMan;
	}
	public String getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
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
