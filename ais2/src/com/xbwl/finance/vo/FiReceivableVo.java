package com.xbwl.finance.vo;

import java.util.Date;

import javax.persistence.Column;

import org.apache.struts2.json.annotations.JSON;

/**
 * author shuw
 * time Oct 17, 2011 9:45:27 AM
 */

public class FiReceivableVo {
	private String departName;
	private Long departId;
	
	private String cusName;
	private Long cusId;
	
	private Long extendsionLimit;   //延期天数
	
	private Double arrearsFee;  //欠款金额
	private Long dayNum;         //欠款天数
	private Long extendedDayNum;  //超期天数
	
	private Double creditAmount;  //信用额度 
	private Double lsCreditAmount; // 临时信用额度
	private Long creditDay;   //信用期限
	
	private String serviceName;    //客服员
	private String serviceDepartName;  //客服部门
	private Long serviceDepartCode;  //客服部门
	private Date createTime;   //欠款初始日期
	private Long status;  // 1:正常，2：超期，3：超额，4：超期超额
	private Double openingBalance; // 期初余额
	
	private Double useFee;
	
	public Double getUseFee() {
		return useFee;
	}
	public void setUseFee(Double useFee) {
		this.useFee = useFee;
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
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public Long getCusId() {
		return cusId;
	}
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}
	public Double getArrearsFee() {
		return arrearsFee;
	}
	public void setArrearsFee(Double arrearsFee) {
		this.arrearsFee = arrearsFee;
	}
	public Long getDayNum() {
		return dayNum;
	}
	public void setDayNum(Long dayNum) {
		this.dayNum = dayNum;
	}
	public Long getExtendedDayNum() {
		return extendedDayNum;
	}
	public void setExtendedDayNum(Long extendedDayNum) {
		this.extendedDayNum = extendedDayNum;
	}
	public Double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}
	public Long getCreditDay() {
		return creditDay;
	}
	public void setCreditDay(Long creditDay) {
		this.creditDay = creditDay;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceDepartName() {
		return serviceDepartName;
	}
	public void setServiceDepartName(String serviceDepartName) {
		this.serviceDepartName = serviceDepartName;
	}
	public Long getServiceDepartCode() {
		return serviceDepartCode;
	}
	public void setServiceDepartCode(Long serviceDepartCode) {
		this.serviceDepartCode = serviceDepartCode;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Double getLsCreditAmount() {
		return lsCreditAmount;
	}
	public void setLsCreditAmount(Double lsCreditAmount) {
		this.lsCreditAmount = lsCreditAmount;
	}
	public Long getExtendsionLimit() {
		return extendsionLimit;
	}
	public void setExtendsionLimit(Long extendsionLimit) {
		this.extendsionLimit = extendsionLimit;
	}
	
	public Double getOpeningBalance() {
		return this.openingBalance;
	}

	public void setOpeningBalance(Double openingBalance) {
		this.openingBalance = openingBalance;
	}
	
	
}
