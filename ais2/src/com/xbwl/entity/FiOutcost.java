package com.xbwl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * FiOutcost entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "FI_OUTCOST")
public class FiOutcost implements java.io.Serializable ,AuditableEntity{

	// Fields

	private Long id;
	private Long dno;//配送单号
	private String  outcostNo;//外发单号
	private Long customerId;//客商ID
	private String customerName;//客商名称
	private Double amount;//外发成本
	private Long outuserId;//外发员
	private Long isdelete;//是否删除  //状态,默认0为不可用，1为可用
	private String remark;//备注
	private String updateName;
	private Date updateTime;
	private String createName;
	private Date createTime;
	private Long departId;//创建部门ID
	private String departName;//创建部门
	private String ts;
	private Long status=0l;//审核状态
	
	private Long sourceNo;   //来源单号
	private String sourceData;//  数据来源
	private Long payStatus=0L;  //是否已支付（0：未支付，1：已支付）
	private Date payTime; //付款时间
	private String payUser;//付款人
	
		// Constructors

	private Long batchNo ;                   // 批次号
	private Long submitPayStatus;   //提交应收应付
	
	private String reviewUser;//审核人
	private Date reviewDate;//审核时间
	
	/** default constructor */
	public FiOutcost() {
	}

	/** minimal constructor */
	public FiOutcost(Long id) {
		this.id = id;
	}
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_OUTCOST")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the dno
	 */
	@Column(name="D_NO")
	public Long getDno() {
		return dno;
	}

	/**
	 * @param dno the dno to set
	 */
	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "OUTCOST_NO")
	public String getOutcostNo() {
		return this.outcostNo;
	}

	public void setOutcostNo(String outcostNo) {
		this.outcostNo = outcostNo;
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

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "OUTUSER_ID", precision = 22, scale = 0)
	public Long getOutuserId() {
		return this.outuserId;
	}

	public void setOutuserId(Long outuserId) {
		this.outuserId = outuserId;
	}

	@Column(name = "ISDELETE", precision = 22, scale = 0)
	public Long getIsdelete() {
		return this.isdelete;
	}

	public void setIsdelete(Long isdelete) {
		this.isdelete = isdelete;
	}

	@Column(name = "REMARK", length = 50)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	/**
	 * @return the ts
	 */
	@Column(name = "TS", length = 15)
	public String getTs() {
		return ts;
	}

	/**
	 * @param ts the ts to set
	 */
	public void setTs(String ts) {
		this.ts = ts;
	}

	/**
	 * @return the status
	 */
	@Column(name="STATUS")
	public Long getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name="SOURCE_No")
	public Long getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(Long sourceNo) {
		this.sourceNo = sourceNo;
	}

	@Column(name="SOURCE_DATA")
	public String getSourceData() {
		return sourceData;
	}

	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}

/*	@Column(name="SOURCE_AMOUNT")
	public Double getSourceAmount() {
		return sourceAmount;
	}

	public void setSourceAmount(Double sourceAmount) {
		this.sourceAmount = sourceAmount;
	}*/
	
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

	@Column(name = "SUBMIT_PAY_STATUS", precision = 22, scale = 0)
	public Long getSubmitPayStatus() {
		return submitPayStatus;
	}

	public void setSubmitPayStatus(Long submitPayStatus) {
		this.submitPayStatus = submitPayStatus;
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

	@Column(name = "REVIEW_USER", length = 20)
	public String getReviewUser() {
		return this.reviewUser;
	}

	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "REVIEW_DATE", length = 7)
	public Date getReviewDate() {
		return this.reviewDate;
	}
	
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	
/*	@Column(name = "RETURN_STATUS", precision = 22, scale = 0)
	public Long getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(Long returnStatus) {
		this.returnStatus = returnStatus;
	}*/
	
}