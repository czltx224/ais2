package com.xbwl.finance.vo;

import com.xbwl.entity.FiPaid;

public class FiPaidVo extends FiPaid {
	private Long paymentType; //收付类型(1收款单/2付款单)
	private Long paymentStatus; //收付状态：0作废、1未收款、2已收款、3部分收款、4未付款、5已付款、6部分付款、7到付转欠款、8异常
	private String costType; //费用类型:代收货款/到付提送费/到付增值费/预付提送费/预付增值费/其它收入/对账
	private String documentsType; //单据大类:收入\成本\对账\预存款\代收货款
	private String documentsSmalltype; //单据小类：配送单/对账单/配送单/预存款单
	private Long documentsNo; //单据小类对应的单号
	private String penyType; //结算类型(现结、月结)
	private String contacts; //往来单位:没在客商档案中的客商，内部客户。
	private Long customerId; //客商id
	private String customerName; //客商表名称
	private String sourceData; //数据来源
	private Long sourceNo; //来源单号


	public Long getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Long paymentType) {
		this.paymentType = paymentType;
	}

	public Long getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Long paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public String getDocumentsType() {
		return documentsType;
	}

	public void setDocumentsType(String documentsType) {
		this.documentsType = documentsType;
	}

	public String getDocumentsSmalltype() {
		return documentsSmalltype;
	}

	public void setDocumentsSmalltype(String documentsSmalltype) {
		this.documentsSmalltype = documentsSmalltype;
	}

	public Long getDocumentsNo() {
		return documentsNo;
	}

	public void setDocumentsNo(Long documentsNo) {
		this.documentsNo = documentsNo;
	}

	public String getPenyType() {
		return penyType;
	}

	public void setPenyType(String penyType) {
		this.penyType = penyType;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSourceData() {
		return sourceData;
	}

	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}

	public Long getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(Long sourceNo) {
		this.sourceNo = sourceNo;
	}
	
	
}
