package com.xbwl.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * OprOverweight entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_OVERWEIGHT")
public class OprOverweight implements java.io.Serializable , AuditableEntity{

	// Fields

	private Long id;
	private Long customerId;//客商ID
	private String customerName;//客商名称
	private String flightMainNo;//主单号
	private Double weight;//超出重量
	private Double rate;//费率
	private Double amount;//金额
	private String remark;//备注
	private Long status=1L;//状态：0作废，1正常
	private Long auditStatus=1L;//审核状态：1未审核，2：已审核
	private String auditName;//审核人
	private Date auditTime;//审核时间
	private String rejectName;//否决人
	private Date rejectTime;//否决时间
	private String auditRemark;//审核备注
	private String customerService;//客服员
	private String cusDepartCode;//客服部门CODE
	private Long departId;//创建部门id
	private String departName;//创建部门
	private Double faxWeight;//主单重量
	private Double flightWeight;//黄单重量
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String ts;

	// Constructors

	/** default constructor */
	public OprOverweight() {
	}

	/** minimal constructor */
	public OprOverweight(Long id, Long status, Long auditStatus) {
		this.id = id;
		this.status = status;
		this.auditStatus = auditStatus;
	}
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_OVERWEIGHT")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUSTOMER_ID", precision = 22, scale = 0)
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CUSTOMER_NAME", length = 50)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "FLIGHT_MAIN_NO", length = 200)
	public String getFlightMainNo() {
		return this.flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	@Column(name = "WEIGHT", precision = 10)
	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Column(name = "RATE", length = 50)
	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "AMOUNT", length = 50)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "STATUS", nullable = false, precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "AUDIT_STATUS", nullable = false, precision = 22, scale = 0)
	public Long getAuditStatus() {
		return this.auditStatus;
	}

	public void setAuditStatus(Long auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Column(name = "AUDIT_NAME", length = 20)
	public String getAuditName() {
		return this.auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "AUDIT_TIME", length = 7)
	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "AUDIT_REMARK", length = 250)
	public String getAuditRemark() {
		return this.auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	@Column(name = "CUSTOMER_SERVICE", length = 20)
	public String getCustomerService() {
		return this.customerService;
	}

	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}

	@Column(name = "CUS_DEPART_CODE", length = 20)
	public String getCusDepartCode() {
		return this.cusDepartCode;
	}

	public void setCusDepartCode(String cusDepartCode) {
		this.cusDepartCode = cusDepartCode;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	/**
	 * @return the faxWeight
	 */
	@Column(name = "FAX_WEIGHT", length = 15)
	public Double getFaxWeight() {
		return faxWeight;
	}

	/**
	 * @param faxWeight the faxWeight to set
	 */
	public void setFaxWeight(Double faxWeight) {
		this.faxWeight = faxWeight;
	}

	/**
	 * @return the flightWeight
	 */
	@Column(name = "FLIGHT_WEIGHT", length = 15)
	public Double getFlightWeight() {
		return flightWeight;
	}

	/**
	 * @param flightWeight the flightWeight to set
	 */
	public void setFlightWeight(Double flightWeight) {
		this.flightWeight = flightWeight;
	}

	/**
	 * @return the rejectName
	 */
	@Column(name = "REJECT_NAME")
	public String getRejectName() {
		return rejectName;
	}

	/**
	 * @param rejectName the rejectName to set
	 */
	public void setRejectName(String rejectName) {
		this.rejectName = rejectName;
	}

	/**
	 * @return the rejectTime
	 */
	@JSON(format = "yyyy-MM-dd")
	@Column(name = "REJECT_TIME")
	public Date getRejectTime() {
		return rejectTime;
	}

	/**
	 * @param rejectTime the rejectTime to set
	 */
	public void setRejectTime(Date rejectTime) {
		this.rejectTime = rejectTime;
	}
	
}