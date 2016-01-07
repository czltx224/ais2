package com.xbwl.oper.stock.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author CaoZhili time Jul 20, 2011 6:27:42 PM
 */
public class OprInformAppointmentDetailVo implements java.io.Serializable {

	private Long id;
	private Long dno;
	private Long oprInformAppointmentId;
	private String serviceName;
	private Date informTime;
	private String cusRequest;
	private String cusOptions;
	private String informType;
	private Long informResult;
	private String cusName;
	private String cusAddr;
	private String cusTel;
	private String cusMobile;
	private Double inpaymentcollection;
	private Double cpFee;
	private Double deliveryFee;
	private String remark;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	public Long getOprInformAppointmentId() {
		return oprInformAppointmentId;
	}

	public void setOprInformAppointmentId(Long oprInformAppointmentId) {
		this.oprInformAppointmentId = oprInformAppointmentId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	@JSON(format = "yyyy-MM-dd hh:mm:ss")
	public Date getInformTime() {
		return informTime;
	}

	public void setInformTime(Date informTime) {
		this.informTime = informTime;
	}

	public String getCusRequest() {
		return cusRequest;
	}

	public void setCusRequest(String cusRequest) {
		this.cusRequest = cusRequest;
	}

	public String getCusOptions() {
		return cusOptions;
	}

	public void setCusOptions(String cusOptions) {
		this.cusOptions = cusOptions;
	}

	public String getInformType() {
		return informType;
	}

	public void setInformType(String informType) {
		this.informType = informType;
	}

	public Long getInformResult() {
		return informResult;
	}

	public void setInformResult(Long informResult) {
		this.informResult = informResult;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusAddr() {
		return cusAddr;
	}

	public void setCusAddr(String cusAddr) {
		this.cusAddr = cusAddr;
	}

	public String getCusTel() {
		return cusTel;
	}

	public void setCusTel(String cusTel) {
		this.cusTel = cusTel;
	}

	public String getCusMobile() {
		return cusMobile;
	}

	public void setCusMobile(String cusMobile) {
		this.cusMobile = cusMobile;
	}

	public Double getInpaymentcollection() {
		return inpaymentcollection;
	}

	public void setInpaymentcollection(Double inpaymentcollection) {
		this.inpaymentcollection = inpaymentcollection;
	}

	public Double getCpFee() {
		return cpFee;
	}

	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}

	public Double getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(Double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
	@JSON(format = "yyyy-MM-dd")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	@JSON(format = "yyyy-MM-dd")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public OprInformAppointmentDetailVo(Long id, Long dno,
			Long oprInformAppointmentId, String serviceName, Date informTime,
			String cusRequest, String cusOptions, String informType,
			Long informResult, String cusName, String cusAddr, String cusTel,
			String cusMobile, Double inpaymentcollection, Double cpFee,
			Double deliveryFee, String remark, String createName,
			Date createTime, String updateName, Date updateTime, String ts) {
		super();
		this.id = id;
		this.dno = dno;
		this.oprInformAppointmentId = oprInformAppointmentId;
		this.serviceName = serviceName;
		this.informTime = informTime;
		this.cusRequest = cusRequest;
		this.cusOptions = cusOptions;
		this.informType = informType;
		this.informResult = informResult;
		this.cusName = cusName;
		this.cusAddr = cusAddr;
		this.cusTel = cusTel;
		this.cusMobile = cusMobile;
		this.inpaymentcollection = inpaymentcollection;
		this.cpFee = cpFee;
		this.deliveryFee = deliveryFee;
		this.remark = remark;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	public OprInformAppointmentDetailVo() {
		super();
	}

}
