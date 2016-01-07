package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * �˵���ǩ��ӡʵ����
 * @project ais2.0
 * @author czl
 * @Time Feb 17, 2012 9:43:54 AM
 */
@XBlinkAlias("billLading")
public class OprWaybillLabelBean extends PrintBean {

	private String flightMainNo;//������
	private String addr;//�ջ��˵�ַ
	private String consignee;//�ջ�������
	private String startDepart;//ʼ������
	private String transMode;//���䷽ʽ
	private String dno;//���͵���
	private String piece;//¼������
	private String faxInTime;//¼��ʱ��
	private String isRound;//�Ƿ���ҪȦ����
	
	private String printName;// ��ӡ��
	private Long printNum;// ��ӡ����
	private String printTime;// ��ӡʱ��
	private String printId;// ��ӡ��¼��ID
	private String sourceNo;// ��ԴID
	
	public String getFlightMainNo() {
		return flightMainNo;
	}
	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getStartDepart() {
		return startDepart;
	}
	public void setStartDepart(String startDepart) {
		this.startDepart = startDepart;
	}
	public String getTransMode() {
		return transMode;
	}
	public void setTransMode(String transMode) {
		this.transMode = transMode;
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
	public String getDno() {
		return dno;
	}
	public void setDno(String dno) {
		this.dno = dno;
	}
	public String getPiece() {
		return piece;
	}
	public void setPiece(String piece) {
		this.piece = piece;
	}
	public String getFaxInTime() {
		return faxInTime;
	}
	public void setFaxInTime(String faxInTime) {
		this.faxInTime = faxInTime;
	}
	public String getIsRound() {
		return isRound;
	}
	public void setIsRound(String isRound) {
		this.isRound = isRound;
	}
}
