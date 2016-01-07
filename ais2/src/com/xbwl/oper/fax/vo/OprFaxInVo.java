package com.xbwl.oper.fax.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.entity.OprFaxIn;

public class OprFaxInVo extends OprFaxIn{

	private Long storePiece;       //库存件数
	private Date storeDate;  //入库时间
	private Date noticeDate;  // 通知时间
	private Long storeTime;//在库时长
	private Double storeFee;  //库存费用
	private Double totalPaymentCollection;  // 总费用
	private Double totalCpValueAddFee; //总增值服费(已经包括仓储费)
	private Double sysTotalPaymentCollection; //系统默认总费用，没有修改过的
	private String iDNumber;  // 身份证号
	private String  iDNumberTwo;  //代签人身份证号
	private String consigneeTwo;  // 代签人姓名
	
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
