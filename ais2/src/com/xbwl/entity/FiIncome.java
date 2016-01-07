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
 * FiIncome entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_INCOME", schema = "AISUSER")
public class FiIncome implements java.io.Serializable,AuditableEntity{

	// Fields

	private Long id;
	private Long sourceNo;//来源单号
	private String whoCash;//付款方
	private Double amount;//金额
	private String sourceData;//数据来源
	private String incomeDepart;//收入部门
	private String amountType;//费用类型
	private String customerService;//客服员
	private Date accounting;//会计日期
	private String admDepart;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long customerId;
	private String customerName;//客商名称(发货代理)
	private Long departId;//创建业务部门id
	private String departName;
	private Long certiStatus=1L;//凭证状态(0:撤消,1:未生成,2:传输中,3:已生成)
	private String certiNo;//凭证号
	private Long dno;//配送单号
	private Long admDepartId;//客服员所在行政部门ID
	private Long incomeDepartId;
	private Long accountId;//交账单号
	private Long accountStatus=0L;//交账状态(0未交账,1已交账)

	// Constructors

	/** default constructor */
	public FiIncome() {
	}

	/** minimal constructor */
	public FiIncome(Long id) {
		this.id = id;
	}

	/** full constructor */
	public FiIncome(Long id, Long sourceNo, String whoCash, Double amount,
			String sourceData, String incomeDepart, String amountType,
			String customerService, Date accounting, String admDepart,
			String crateName, Date createTime, String updateName,
			Date updateTime, String ts, Long customerId, String customerName,
			Long departId, String departName, Long certiStatus, String certiNo,
			Long DNo) {
		this.id = id;
		this.sourceNo = sourceNo;
		this.whoCash = whoCash;
		this.amount = amount;
		this.sourceData = sourceData;
		this.incomeDepart = incomeDepart;
		this.amountType = amountType;
		this.customerService = customerService;
		this.accounting = accounting;
		this.admDepart = admDepart;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.customerId = customerId;
		this.customerName = customerName;
		this.departId = departId;
		this.departName = departName;
		this.certiStatus = certiStatus;
		this.certiNo = certiNo;
	}

	// Property accessors
	@Id  
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_INCOME",allocationSize=1)
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SOURCE_NO", precision = 22, scale = 0)
	public Long getSourceNo() {
		return this.sourceNo;
	}

	public void setSourceNo(Long sourceNo) {
		this.sourceNo = sourceNo;
	}

	@Column(name = "WHO_CASH", length = 20)
	public String getWhoCash() {
		return this.whoCash;
	}

	public void setWhoCash(String whoCash) {
		this.whoCash = whoCash;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "SOURCE_DATA", length = 50)
	public String getSourceData() {
		return this.sourceData;
	}

	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}

	@Column(name = "INCOME_DEPART", length = 50)
	public String getIncomeDepart() {
		return this.incomeDepart;
	}

	public void setIncomeDepart(String incomeDepart) {
		this.incomeDepart = incomeDepart;
	}

	@Column(name = "AMOUNT_TYPE", length = 20)
	public String getAmountType() {
		return this.amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	@Column(name = "CUSTOMER_SERVICE", length = 20)
	public String getCustomerService() {
		return this.customerService;
	}

	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}

	@Column(name = "ACCOUNTING", length = 7)
	public Date getAccounting() {
		return this.accounting;
	}

	public void setAccounting(Date accounting) {
		this.accounting = accounting;
	}

	@Column(name = "ADM_DEPART", length = 50)
	public String getAdmDepart() {
		return this.admDepart;
	}

	public void setAdmDepart(String admDepart) {
		this.admDepart = admDepart;
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

	@Column(name = "CERTI_STATUS", precision = 22, scale = 0)
	public Long getCertiStatus() {
		return this.certiStatus;
	}

	public void setCertiStatus(Long certiStatus) {
		this.certiStatus = certiStatus;
	}

	@Column(name = "CERTI_NO", length = 50)
	public String getCertiNo() {
		return this.certiNo;
	}

	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	@Column(name = "D_NO", precision = 22, scale = 0)
	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName=createName;
	}

	
	@Column(name = "ADM_DEPART_ID", precision = 22, scale = 0)
	public Long getAdmDepartId() {
		return admDepartId;
	}

	public void setAdmDepartId(Long admDepartId) {
		this.admDepartId = admDepartId;
	}

	@Column(name = "INCOME_DEPART_ID", precision = 22, scale = 0)
	public Long getIncomeDepartId() {
		return incomeDepartId;
	}

	public void setIncomeDepartId(Long incomeDepartId) {
		this.incomeDepartId = incomeDepartId;
	}

	@Column(name = "ACCOUNT_ID", precision = 22, scale = 0)
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	@Column(name = "ACCOUNT_STATUS", precision = 22, scale = 0)
	public Long getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Long accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	

	
}