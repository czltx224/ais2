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
 * 欠款核算
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_REPAYMENT")
public class FiRepayment implements java.io.Serializable,AuditableEntity {

	private Long id;
	private Long batchNo;//交账单号
	private Date accountData;//交账日期
	private Long customerId;//客商ID
	private String customerName;//客商ID
	private Double accountsBalance;//欠款金额
	private Double eliminationAccounts;//还款金额
	private Double eliminationCope;//代收货款金额
	private Double problemAmount;//问题账款金额
	private String sourceData;//数据来源
	private Long sourceNo;//来源单号
	private Long accountStatus=0L;//交账状态(0：未交账,1：已交账)
	private Long status=1L;//状态(0：作废,1：正常)
	private Long departId;//申请部门id
	private String departName;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;

	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_REPAYMENT",allocationSize=1)
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "ACCOUNT_DATA", length = 7)
	public Date getAccountData() {
		return this.accountData;
	}

	public void setAccountData(Date accountData) {
		this.accountData = accountData;
	}

	@Column(name = "CUSTOMER_ID", precision = 22, scale = 0)
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CUSTOMER_NAME", length = 200)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "ACCOUNTS_BALANCE", precision = 10)
	public Double getAccountsBalance() {
		return this.accountsBalance;
	}

	public void setAccountsBalance(Double accountsBalance) {
		this.accountsBalance = accountsBalance;
	}

	@Column(name = "ELIMINATION_ACCOUNTS", precision = 10)
	public Double getEliminationAccounts() {
		return this.eliminationAccounts;
	}

	public void setEliminationAccounts(Double eliminationAccounts) {
		this.eliminationAccounts = eliminationAccounts;
	}

	@Column(name = "ELIMINATION_COPE", precision = 10)
	public Double getEliminationCope() {
		return this.eliminationCope;
	}

	public void setEliminationCope(Double eliminationCope) {
		this.eliminationCope = eliminationCope;
	}

	@Column(name = "PROBLEM_AMOUNT", precision = 10)
	public Double getProblemAmount() {
		return this.problemAmount;
	}

	public void setProblemAmount(Double problemAmount) {
		this.problemAmount = problemAmount;
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

	@Column(name = "ACCOUNT_STATUS", precision = 22, scale = 0)
	public Long getAccountStatus() {
		return this.accountStatus;
	}

	public void setAccountStatus(Long accountStatus) {
		this.accountStatus = accountStatus;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
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

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	
	@Column(name = "BATCH_NO", precision = 22, scale = 0)
	public Long getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

}