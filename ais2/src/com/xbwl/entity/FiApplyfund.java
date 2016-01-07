package com.xbwl.entity;
// default package

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
 * 资金申请.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_APPLYFUND")
public class FiApplyfund implements java.io.Serializable ,AuditableEntity {
	private Long id;
	private Long fundType; //申请类型
	private Long isSit=0L;//是否坐支(1:是、2:否)
	private Long paymentAccountId;//拨付账号id
	private String paymentAccountName;//拨付账号
	private Double amount;//申请金额
	private Double paymentAmount;//审批金额
	private String appRemark;//申请备注
	private String paymentRemark;//审批备注
	private Long appAccountId;//申请账号ID
	private String appAccountName;//申请账号
	private Long status=2L;//状态：0:已作废、1:已否决、2:已申请\3已确收,4已坐支
	private String departName;
	private Long departId;
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String auditName;//审核人
	private Date auditTime;
	private String ts;
	private Long capitalTypeId;//交接单类型(40250:现金交接单,40251:银行转账单)
	private Long receivablesStatus=1L;//收款确认状态：1:未确认、2:已确认
	private Date receivablesTime;//收款确认时间
	private Long auditStatus=1L; // 审核状态(1:未审核\2:已审核)


	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_APPLYFUND")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "FUND_TYPE", precision = 22, scale = 0)
	public Long getFundType() {
		return this.fundType;
	}

	public void setFundType(Long fundType) {
		this.fundType = fundType;
	}

	@Column(name = "IS_SIT", precision = 22, scale = 0)
	public Long getIsSit() {
		return this.isSit;
	}

	public void setIsSit(Long isSit) {
		this.isSit = isSit;
	}

	@Column(name = "PAYMENT_ACCOUNT_ID", precision = 22, scale = 0)
	public Long getPaymentAccountId() {
		return this.paymentAccountId;
	}

	public void setPaymentAccountId(Long paymentAccountId) {
		this.paymentAccountId = paymentAccountId;
	}

	@Column(name = "PAYMENT_ACCOUNT_NAME", length = 50)
	public String getPaymentAccountName() {
		return this.paymentAccountName;
	}

	public void setPaymentAccountName(String paymentAccountName) {
		this.paymentAccountName = paymentAccountName;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "PAYMENT_AMOUNT", precision = 10)
	public Double getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	@Column(name = "APP_REMARK", length = 500)
	public String getAppRemark() {
		return this.appRemark;
	}

	public void setAppRemark(String appRemark) {
		this.appRemark = appRemark;
	}

	@Column(name = "PAYMENT_REMARK", length = 500)
	public String getPaymentRemark() {
		return this.paymentRemark;
	}

	public void setPaymentRemark(String paymentRemark) {
		this.paymentRemark = paymentRemark;
	}

	@Column(name = "APP_ACCOUNT_ID", precision = 22, scale = 0)
	public Long getAppAccountId() {
		return this.appAccountId;
	}

	public void setAppAccountId(Long appAccountId) {
		this.appAccountId = appAccountId;
	}

	@Column(name = "APP_ACCOUNT_NAME", length = 50)
	public String getAppAccountName() {
		return this.appAccountName;
	}

	public void setAppAccountName(String appAccountName) {
		this.appAccountName = appAccountName;
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

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", length = 7)
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

	@Temporal(TemporalType.DATE)
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

	@Column(name = "AUDIT_NAME", length = 20)
	public String getAuditName() {
		return this.auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "AUDIT_TIME", length = 7)
	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "CAPITALTYPE_ID")
	public Long getCapitalTypeId() {
		return capitalTypeId;
	}

	public void setCapitalTypeId(Long capitalTypeId) {
		this.capitalTypeId = capitalTypeId;
	}

	@Column(name = "RECEIVABLES_STATUS", precision = 22, scale = 0)
	public Long getReceivablesStatus() {
		return receivablesStatus;
	}

	public void setReceivablesStatus(Long receivablesStatus) {
		this.receivablesStatus = receivablesStatus;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "RECEIVABLES_TIME", length = 7)
	public Date getReceivablesTime() {
		return receivablesTime;
	}

	public void setReceivablesTime(Date receivablesTime) {
		this.receivablesTime = receivablesTime;
	}

	@Column(name = "AUDIT_STATUS", precision = 22, scale = 0)
	public Long getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Long auditStatus) {
		this.auditStatus = auditStatus;
	}
	
}