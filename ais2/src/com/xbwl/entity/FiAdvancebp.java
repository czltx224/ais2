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

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * 预存款收支.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_ADVANCEBP")
public class FiAdvancebp  implements java.io.Serializable,AuditableEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long fiAdvanceId;//账号ID
	private Long customerId;//客商ID
	private String customerName;//客商名称
	private Long settlementType=1L;//1:存款、2:取款
	private Double amount=0.0;//金额
	private Double settlementAmount=0.0;//结算多少
	private String sourceData;//数据来源
	private Long sourceNo;//来源单号
	private String remark;
	private String updateName;
	private Date updateTime;
	private String createName;
	private Date createTime;
	private Long createDeptid;
	private String createDept;
	private Long payStatus=1L;//收银状态（1：未支付，2：已支付）
	private Long status=1L;//状态(0:作废,1:正常)
	private String reviewUser; // 审核人
	private Date reviewDate; // 审核时间
	private String reviewRemark; // 审核备注
	private Long reviewStatus=1L;//审核状态(1:未审核,2:已审核)
	private String ts;
	

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_ADVANCEBP")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "FIADVANCEID", precision = 22, scale = 0)
	public Long getFiAdvanceId() {
		return this.fiAdvanceId;
	}

	public void setFiAdvanceId(Long fiAdvanceId) {
		this.fiAdvanceId = fiAdvanceId;
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

	@Column(name = "SETTLEMENT_TYPE", precision = 22, scale = 0)
	public Long getSettlementType() {
		return this.settlementType;
	}

	public void setSettlementType(Long settlementType) {
		this.settlementType = settlementType;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "SETTLEMENT_AMOUNT", precision = 10)
	public Double getSettlementAmount() {
		return this.settlementAmount;
	}

	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	@Column(name = "SOURCE_DATA", length = 10)
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

	@Column(name = "REMARK", length = 500)
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

	@Temporal(TemporalType.DATE)
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

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_DEPTID", precision = 22, scale = 0)
	public Long getCreateDeptid() {
		return this.createDeptid;
	}

	public void setCreateDeptid(Long createDeptid) {
		this.createDeptid = createDeptid;
	}

	@Column(name = "CREATE_DEPT", length = 50)
	public String getCreateDept() {
		return this.createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	@Column(name = "PAY_STATUS", precision = 22, scale = 0)
	public Long getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(Long payStatus) {
		this.payStatus = payStatus;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	@Column(name = "REVIEW_USER", length = 20)
	public String getReviewUser() {
		return this.reviewUser;
	}

	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}

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

	@Column(name = "REVIEW_STATUS", precision = 22, scale = 0)
	public Long getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Long reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	
	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
}