package com.xbwl.oper.stock.vo;
/**
 * author shuw
 * time Jul 27, 2011 4:22:07 PM
 */

public class OprPrewiredVo {
	
	private String receiptType;//�ص�����
	private Long  dno;//���͵���
	private Long  piece;//������
	private Long reachNum;//�ص��㵽����
	private Long realPiece;//ʵ�����
	private Long wId;//Ԥ�䵥��
	private Double weight;//����
	private Double consigneeFee;//�������ͷ�
	private Double cpFee;//Ԥ�����ͷ�
	private Double traFee;//��ת��
	private Double paymentCollection;//���ջ���
	private String departName;//��������
	private Long departId;//���ű��
	private String autostowMode;//���ͷ�ʽ
	private String telPhone;  //�绰����
	
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
