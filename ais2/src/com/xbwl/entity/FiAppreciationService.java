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
 * author shuw
 * time Oct 26, 2011 1:39:13 PM
 */
@Entity
@Table(name = "FI_APPRECIATION_SERVICE", schema = "AISUSER")
public class FiAppreciationService implements java.io.Serializable,AuditableEntity{

	// Fields

	private Long id;
	private Long dno;  //配送单号
  	private Long customerId;  //客商
	private String customerName;  
	private String appreciationType;   // 增服务费类型
	private Double incomeAmount;  // 收入金额
	private Double costAmount;  //  成本金额
	private String recipientsUser;    //    领款人
	private String confirmUser;//   客户确认人
	private String phome;   //   联系方式
	private String handleUser;  // 经手人
 	private String remark;   //备注
	private Long paymentStatus=1l;   //付款状态(1：未付款,2：已付款)
	private Long serviceAuditStatus=1l;   //客服审核状态(1:未审核,2:已审核)
	private String serviceAuditUser;   //客服审核人
  	private Date serviceAuditDate;   //客服审核时间
	private String accountAuditUser;   //会计审核人
	private Date accountAuditDate;   //会计审核时间
	private Long accountAuditStatus=1l;   //会计审核状态
	private Long departId;  //
	private String departName;  //
	private Date createTime;  //
	private String createName;   //
	private Date updateTime;  //
	private String updateName;   //
	private String ts;  //
	private String paymentType;  //付款方
	private String paymentMode;  //付款方式 （到付/月结）
	
	// Constructors

	/** default constructor */
	public FiAppreciationService() {
	}

	/** minimal constructor */
	public FiAppreciationService(Long id) {
		this.id = id;
	}

	/** full constructor */
	public FiAppreciationService(Long id, Long dno, Long customerId,
			String customerName, String appreciationType, Double incomeAmount,
			Double costAmount, String recipientsUser, String confirmUser,
			String phome, String handleUser, String remark, Long paymentStatus,
			Long serviceAuditStatus, String serviceAuditUser,
			Date serviceAuditDate, String accountAuditUser,
			Date accountAuditDate, Long accountAuditStatus, Long departId,
			String departName, Date createTime, String createName,
			Date updateTime, String updateName, String ts) {
		this.id = id;
		this.dno = dno;
		this.customerId = customerId;
		this.customerName = customerName;
		this.appreciationType = appreciationType;
		this.incomeAmount = incomeAmount;
		this.costAmount = costAmount;
		this.recipientsUser = recipientsUser;
		this.confirmUser = confirmUser;
		this.phome = phome;
		this.handleUser = handleUser;
		this.remark = remark;
		this.paymentStatus = paymentStatus;
		this.serviceAuditStatus = serviceAuditStatus;
		this.serviceAuditUser = serviceAuditUser;
		this.serviceAuditDate = serviceAuditDate;
		this.accountAuditUser = accountAuditUser;
		this.accountAuditDate = accountAuditDate;
		this.accountAuditStatus = accountAuditStatus;
		this.departId = departId;
		this.departName = departName;
		this.createTime = createTime;
		this.createName = createName;
		this.updateTime = updateTime;
		this.updateName = updateName;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_APPRECIATION_SERVICE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
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

	@Column(name = "APPRECIATION_TYPE", length = 20)
	public String getAppreciationType() {
		return this.appreciationType;
	}

	public void setAppreciationType(String appreciationType) {
		this.appreciationType = appreciationType;
	}

	@Column(name = "INCOME_AMOUNT", precision = 10)
	public Double getIncomeAmount() {
		return this.incomeAmount;
	}

	public void setIncomeAmount(Double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	@Column(name = "COST_AMOUNT", precision = 10)
	public Double getCostAmount() {
		return this.costAmount;
	}

	public void setCostAmount(Double costAmount) {
		this.costAmount = costAmount;
	}

	@Column(name = "RECIPIENTS_USER", length = 20)
	public String getRecipientsUser() {
		return this.recipientsUser;
	}

	public void setRecipientsUser(String recipientsUser) {
		this.recipientsUser = recipientsUser;
	}

	@Column(name = "CONFIRM_USER", length = 20)
	public String getConfirmUser() {
		return this.confirmUser;
	}

	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	@Column(name = "PHOME", length = 100)
	public String getPhome() {
		return this.phome;
	}

	public void setPhome(String phome) {
		this.phome = phome;
	}

	@Column(name = "HANDLE_USER", length = 20)
	public String getHandleUser() {
		return this.handleUser;
	}

	public void setHandleUser(String handleUser) {
		this.handleUser = handleUser;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "PAYMENT_STATUS", precision = 1, scale = 0)
	public Long getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(Long paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Column(name = "SERVICE_AUDIT_STATUS", precision = 1, scale = 0)
	public Long getServiceAuditStatus() {
		return this.serviceAuditStatus;
	}

	public void setServiceAuditStatus(Long serviceAuditStatus) {
		this.serviceAuditStatus = serviceAuditStatus;
	}

	@Column(name = "SERVICE_AUDIT_USER", length = 20)
	public String getServiceAuditUser() {
		return this.serviceAuditUser;
	}

	public void setServiceAuditUser(String serviceAuditUser) {
		this.serviceAuditUser = serviceAuditUser;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "SERVICE_AUDIT_DATE", length = 7)
	public Date getServiceAuditDate() {
		return this.serviceAuditDate;
	}

	public void setServiceAuditDate(Date serviceAuditDate) {
		this.serviceAuditDate = serviceAuditDate;
	}

	@Column(name = "ACCOUNT_AUDIT_USER", length = 20)
	public String getAccountAuditUser() {
		return this.accountAuditUser;
	}

	public void setAccountAuditUser(String accountAuditUser) {
		this.accountAuditUser = accountAuditUser;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "ACCOUNT_AUDIT_DATE", length = 7)
	public Date getAccountAuditDate() {
		return this.accountAuditDate;
	}

	public void setAccountAuditDate(Date accountAuditDate) {
		this.accountAuditDate = accountAuditDate;
	}

	@Column(name = "ACCOUNT_AUDIT_STATUS", precision = 1, scale = 0)
	public Long getAccountAuditStatus() {
		return this.accountAuditStatus;
	}

	public void setAccountAuditStatus(Long accountAuditStatus) {
		this.accountAuditStatus = accountAuditStatus;
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

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
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

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
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

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "PAYMENT_TYPE", length = 50)
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Column(name = "PAYMENT_MODE", length = 50)
	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	
}
