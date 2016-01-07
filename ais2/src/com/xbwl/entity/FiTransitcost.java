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
 * 中转成本表
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_TRANSITCOST", schema = "AISUSER")
public class FiTransitcost implements java.io.Serializable,AuditableEntity{

	// Fields

	private Long id;   
	private Long dno;   //配送单号
	private Long status;   //  审核状态(0：未审核,1：已审核)
	private String departName;   //业务部门名称
	private String reviewUser;   // 审核人
	private Date reviewDate;   // 审核时间
	private String reviewRemark;   //审核备注
	private String ts;   //
	private String createName;
	private Date createTime;
	private Date updateTime;
	private String updateName;
	private Long departId;
	private Long batchNo;
	private Long payStatus=0L;  //是否已支付（0：未支付，1：已支付）
	
	private Double amount=0.0;// 金额(必输参数)
	private String sourceData; //数据来源(必输参数)  更改申请、传真录入、返货登记
	private Long sourceNo; //来源单号(必输参数)
	private Long customerId; //客商id(必输参数)
	private String customerName; //客商表名称(必输参数)
	private Date payTime; //付款时间
	private String payUser;//付款人
	
	// Constructors
	
	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId= departId;
	}

	/** full constructor */
	public FiTransitcost(Long id, Long DNo, Long status, String reviewDept,
			String reviewUser, Date reviewDate, String reviewRemark, String ts) {
		this.id = id;
		this.dno = DNo;
		this.status = status;
		this.reviewUser = reviewUser;
		this.reviewDate = reviewDate;
		this.reviewRemark = reviewRemark;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_TRANSITCOST",allocationSize=1)
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}


	/** default constructor */
	public FiTransitcost() {
	}

	/** minimal constructor */
	public FiTransitcost(Long id) {
		this.id = id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "D_NO", precision = 22, scale = 0)
	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "REVIEW_USER", length = 20)
	public String getReviewUser() {
		return this.reviewUser;
	}

	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REVIEW_DATE", length = 7)
	public Date getReviewDate() {
		return this.reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	@Column(name = "REVIEW_REMARK", length = 500)
	public String getReviewRemark() {
		return this.reviewRemark;
	}

	public void setReviewRemark(String reviewRemark) {
		this.reviewRemark = reviewRemark;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATE_TIME", length = 7)
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

	@Column(name = "PAY_STATUS", precision = 22, scale = 0)
	public Long getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Long payStatus) {
		this.payStatus = payStatus;
	}

	@Column(name = "BATCH_NO", precision = 22, scale = 0)
	public Long getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "SOURCE_DATA", length = 20)
	public String getSourceData() {
		return this.sourceData;
	}

	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}

	@Column(name = "SOURCE_NO", precision = 22, scale = 0)
	public Long getSourceNo() {
		return this.sourceNo;
	}

	public void setSourceNo(Long sourceNo) {
		this.sourceNo = sourceNo;
	}
	
	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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
	
	
	@JSON(format="yyyy-MM-dd hh:mm:ss")
	@Column(name = "PAY_TIME")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime= payTime;
	}

	@Column(name = "PAY_USER", length = 20)
	public String getPayUser() {
		return payUser;
	}

	public void setPayUser(String payUser) {
		this.payUser = payUser;
	}
}