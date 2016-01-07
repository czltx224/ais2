package com.xbwl.oper.fax.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.entity.OprFaxIn;

public class OprFaxInVo extends OprFaxIn{

	private Long storePiece;       //������
	private Date storeDate;  //���ʱ��
	private Date noticeDate;  // ֪ͨʱ��
	private Long storeTime;//�ڿ�ʱ��
	private Double storeFee;  //������
	private Double totalPaymentCollection;  // �ܷ���
	private Double totalCpValueAddFee; //����ֵ����(�Ѿ������ִ���)
	private Double sysTotalPaymentCollection; //ϵͳĬ���ܷ��ã�û���޸Ĺ���
	private String iDNumber;  // ���֤��
	private String  iDNumberTwo;  //��ǩ�����֤��
	private String consigneeTwo;  // ��ǩ������
	
	private Long outStatus;
	private Long signStatus;

	public String getIDNumber(){
		return iDNumber;
	}
	
	public void setIDNumber(String s) {
		this.iDNumber=s;
	}
	
	public String getIDNumberTwo(){
		return iDNumberTwo;
	}
	
	public void setIDNumberTwo(String s) {
		this.iDNumberTwo=s;
	}
	
	public String  getConsigneeTwo() {
		return consigneeTwo;
	}
	public void setConsigneeTwo(String  s) {
		this.consigneeTwo = s;
	}
	
	public Double getSysTotalPaymentCollection() {
		return sysTotalPaymentCollection;
	}
	public void setSysTotalPaymentCollection(Double sysTotalPaymentCollection) {
		this.sysTotalPaymentCollection = sysTotalPaymentCollection;
	}
	
	@JSON(format = "yyyy-MM-dd")
	public Date getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}
	public Double getTotalCpValueAddFee() {
		return totalCpValueAddFee;
	}
	public void setTotalCpValueAddFee(Double totalCpValueAddFee) {
		this.totalCpValueAddFee = totalCpValueAddFee;
	}
	public Long getStorePiece() {
		return storePiece;
	}
	public Long getStoreTime() {
		return storeTime;
	}
	public void setStoreTime(Long storeTime) {
		this.storeTime = storeTime;
	}
	public Double getStoreFee() {
		return storeFee;
	}
	public void setStoreFee(Double storeFee) {
		this.storeFee = storeFee;
	}
	public Double getTotalPaymentCollection() {
		return totalPaymentCollection;
	}
	public void setTotalPaymentCollection(Double totalPaymentCollection) {
		this.totalPaymentCollection = totalPaymentCollection;
	}
	public void setStorePiece(Long storePiece) {
		this.storePiece = storePiece;
	}
	
	@JSON(format = "yyyy-MM-dd")
	public Date getStoreDate() {
		return storeDate;
	}
	public void setStoreDate(Date storeDate) {
		this.storeDate = storeDate;
	}

	public Long getOutStatus() {
		return outStatus;
	}

	public void setOutStatus(Long outStatus) {
		this.outStatus = outStatus;
	}

	public Long getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(Long signStatus) {
		this.signStatus = signStatus;
	}
	
}
