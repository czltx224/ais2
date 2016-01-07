package com.xbwl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * OprInformAppointmentDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_INFORM_APPOINTMENT_DETAIL")
public class OprInformAppointmentDetail implements java.io.Serializable,
		AuditableEntity {

	// Fields

	private Long id;
	private OprInformAppointment oprInformAppointment;
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

	// Constructors

	/** default constructor */
	public OprInformAppointmentDetail() {
	}

	/** minimal constructor */
	public OprInformAppointmentDetail(Long id) {
		this.id = id;
	}

	/** full constructor */
	public OprInformAppointmentDetail(Long id,
			OprInformAppointment oprInformAppointment, String serviceName,
			Date informTime, String cusRequest, String cusOptions,
			String informType, Long informResult, String cusName,
			String cusAddr, String cusTel, String cusMobile,
			Double inpaymentcollection, Double cpFee, Double deliveryFee,
			String remark, String createName, Date createTime,
			String updateName, Date updateTime, String ts) {
		this.id = id;
		this.oprInformAppointment = oprInformAppointment;
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

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_INFORM_DETAIL")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INFORM_ID")
	public OprInformAppointment getOprInformAppointment() {
		return this.oprInformAppointment;
	}

	public void setOprInformAppointment(
			OprInformAppointment oprInformAppointment) {
		this.oprInformAppointment = oprInformAppointment;
	}

	@Column(name = "SERVICE_NAME", precision = 22, scale = 0)
	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "INFORM_TIME")
	public Date getInformTime() {
		return this.informTime;
	}

	public void setInformTime(Date informTime) {
		this.informTime = informTime;
	}

	@Column(name = "CUS_REQUEST", length = 100)
	public String getCusRequest() {
		return this.cusRequest;
	}

	public void setCusRequest(String cusRequest) {
		this.cusRequest = cusRequest;
	}

	@Column(name = "CUS_OPTIONS", length = 100)
	public String getCusOptions() {
		return this.cusOptions;
	}

	public void setCusOptions(String cusOptions) {
		this.cusOptions = cusOptions;
	}

	@Column(name = "INFORM_TYPE", length = 20)
	public String getInformType() {
		return this.informType;
	}

	public void setInformType(String informType) {
		this.informType = informType;
	}

	@Column(name = "INFORM_RESULT", precision = 22, scale = 0)
	public Long getInformResult() {
		return this.informResult;
	}

	public void setInformResult(Long informResult) {
		this.informResult = informResult;
	}

	@Column(name = "CUS_NAME", length = 20)
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	@Column(name = "CUS_ADDR", length = 100)
	public String getCusAddr() {
		return this.cusAddr;
	}

	public void setCusAddr(String cusAddr) {
		this.cusAddr = cusAddr;
	}

	@Column(name = "CUS_TEL", length = 20)
	public String getCusTel() {
		return this.cusTel;
	}

	public void setCusTel(String cusTel) {
		this.cusTel = cusTel;
	}

	@Column(name = "CUS_MOBILE", length = 20)
	public String getCusMobile() {
		return this.cusMobile;
	}

	public void setCusMobile(String cusMobile) {
		this.cusMobile = cusMobile;
	}

	@Column(name = "INPAYMENTCOLLECTION", precision = 10)
	public Double getInpaymentcollection() {
		return this.inpaymentcollection;
	}

	public void setInpaymentcollection(Double inpaymentcollection) {
		this.inpaymentcollection = inpaymentcollection;
	}

	@Column(name = "CP_FEE", precision = 10)
	public Double getCpFee() {
		return this.cpFee;
	}

	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}

	@Column(name = "DELIVERY_FEE", precision = 10)
	public Double getDeliveryFee() {
		return this.deliveryFee;
	}

	public void setDeliveryFee(Double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS")
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

}