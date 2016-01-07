package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 *  ��ת�ɱ���ӡ
 * author shuw
 * time May 14, 2012 10:01:42 AM
 */
@XBlinkAlias("billLading")
public class FiTrainCostBean  extends PrintBean {
	
	
	private String batchNo;   //���κ�
	private Long dno;  //���͵���
	private String consigneeInfo;    //�ջ�����Ϣ
	private String customer;  //��ת����
	private Long piece;// ����
	private Double  weight;//����
	private Double amount;  // ���
	private String sourceData ;// ���ݲ�Դ
	private Long realSourceNo;  //       ��Դ����
	
	private Long totalPiece;           //�ܼ���
	private Double totalWeight;        //������
	private Double totalAmount;

	private String printDateString;  //��ӡʱ��
	private String printName;// ��ӡ��
	private Long printNum=0l;// ��ӡ����
	private String printTime;// ��ӡʱ��
	private String printId;// ��ӡ��¼��ID
	private String sourceNo;// ��ԴID
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
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Long getDno() {
		return dno;
	}
	public void setDno(Long dno) {
		this.dno = dno;
	}
	public String getConsigneeInfo() {
		return consigneeInfo;
	}
	public void setConsigneeInfo(String consigneeInfo) {
		this.consigneeInfo = consigneeInfo;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public Long getPiece() {
		return piece;
	}
	public void setPiece(Long piece) {
		this.piece = piece;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getSourceData() {
		return sourceData;
	}
	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}
	public Long getRealSourceNo() {
		return realSourceNo;
	}
	public void setRealSourceNo(Long realSourceNo) {
		this.realSourceNo = realSourceNo;
	}
	public Long getTotalPiece() {
		return totalPiece;
	}
	public void setTotalPiece(Long totalPiece) {
		this.totalPiece = totalPiece;
	}
	public Double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getPrintDateString() {
		return printDateString;
	}
	public void setPrintDateString(String printDateString) {
		this.printDateString = printDateString;
	}


}
