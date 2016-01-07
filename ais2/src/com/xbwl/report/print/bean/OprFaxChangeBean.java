package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * @author czl
 * @createTime 2012-03-16 03:19 AM
 * 
 * ����֪ͨ����ӡʵ����
 */
@XBlinkAlias("billLading")
public class OprFaxChangeBean extends PrintBean{

	private String changeTitle;//����֪ͨ������
	private String changeDepart;//���Ĳ���
	private String changeName;//������
	private String changeDate;//��������
	private String changeNo;//���ĵ���
	
	private String cusName;//����˾
	private String dno;//���͵���
	private String subNo;//�ֵ���
	private String flightMainNo;//������
	private String consignee;//�ջ�������
	private String piece;//����
	private String weight;//����
	private String weightFee;//�Ʒ�����
	private String payWay;//���ʽ
	private String goWhere;//ȥ��
	private String distributionMode;//���ͷ�ʽ
	private String takeMode;//�����ʽ
	private String changeContent;//��������
	private String xbwlImagePath;//ͼƬ·��
	
	private String printName;// ��ӡ��
	private Long printNum;// ��ӡ����
	private String printTime;// ��ӡʱ��
	private String printId;// ��ӡ��¼��ID
	private String sourceNo;// ��ԴID
	
	public String getChangeTitle() {
		return changeTitle;
	}
	public void setChangeTitle(String changeTitle) {
		this.changeTitle = changeTitle;
	}
	public String getChangeDepart() {
		return changeDepart;
	}
	public void setChangeDepart(String changeDepart) {
		this.changeDepart = changeDepart;
	}
	public String getChangeName() {
		return changeName;
	}
	public void setChangeName(String changeName) {
		this.changeName = changeName;
	}
	public String getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}
	public String getChangeNo() {
		return changeNo;
	}
	public void setChangeNo(String changeNo) {
		this.changeNo = changeNo;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getDno() {
		return dno;
	}
	public void setDno(String dno) {
		this.dno = dno;
	}
	public String getSubNo() {
		return subNo;
	}
	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}
	public String getFlightMainNo() {
		return flightMainNo;
	}
	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
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
	public String getWeightFee() {
		return weightFee;
	}
	public void setWeightFee(String weightFee) {
		this.weightFee = weightFee;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getGoWhere() {
		return goWhere;
	}
	public void setGoWhere(String goWhere) {
		this.goWhere = goWhere;
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
	public String getChangeContent() {
		return changeContent;
	}
	public void setChangeContent(String changeContent) {
		this.changeContent = changeContent;
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
	public String getXbwlImagePath() {
		return xbwlImagePath;
	}
	public void setXbwlImagePath(String xbwlImagePath) {
		this.xbwlImagePath = xbwlImagePath;
	}
}
