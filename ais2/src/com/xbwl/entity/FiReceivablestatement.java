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
import com.xbwl.common.utils.XbwlInt;

/**
 * 往来对账单：FiReceivablestatement entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_RECEIVABLESTATEMENT")
public class FiReceivablestatement implements java.io.Serializable,AuditableEntity {

	private Long id;
	private Long customerId; // 客商ID
	private String customerName; // 客商名称
	private Date stateDate; // 开始日期
	private Date endDate; // 结束日期
	//private Double amount; //账单总额
	private Double accountsAmount=0.0; // 应收金额
	private Double copeAmount=0.0; // 应付金额
	private Double openingBalance=0.0; // 期初余额
	private Double accountsBalance=0.0; // 应收余额
	private Double problemAmount=0.0; // 问题账款金额
	private String reconciliationUser; // 对账员
	private String reviewUser; // 审核人
	private String reviewDept; // 审核部门
	private Date reviewDate; // 审核时间
	private String reviewRemark; // 审核备注
	private Long reconciliationStatus=1l; // 对账状态(1:未对账\2:未审核\3:已审核\0:已作废)
	private Long mailtoNum; // 邮件发送次数
	private Long printNum;// 打印次数
	private Long exportNum; // 导出次数
	//private String createDept;
	@XbwlInt(autoDepart=false)
	private Long departId; //所属业务部门id(必输参数)
	@XbwlInt(autoDepart=false)
	private String departName;//所属业务部门
	private String createName;
	private Date createTime;
	private String updateDept;
	private Date updateTime;
	private String updateName;
	private String ts;
	private String orderfields;
	private Double eliminationAmount=0.0; //冲销金额
	
	private Long verificationStatus=1L;//核销状态(0:空,1:未核销,2:部分核销,3:已核销)
	private Double verificationAmount=0.0;//核销金额

	// Constructors

	/** default constructor */
	public FiReceivablestatement() {
	}

	/** minimal constructor */
	public FiReceivablestatement(Long id) {
		this.id = id;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_RECEIVABLESTATEMENT",allocationSize=1)
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUSTOMER_ID", precision = 22, scale = 0)
	public Long getCustomerId() {
		return customerId;
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

	@JSON(format="yyyy-MM-dd")
	@Column(name = "STATE_DATE", length = 7)
	public Date getStateDate() {
		return this.stateDate;
	}

	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/*
	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
*/
	@Column(name = "COPEAMOUNT", precision = 10)
	public Double getCopeAmount() {
		return copeAmount;
	}

	public void setCopeAmount(Double copeAmount) {
		this.copeAmount = copeAmount;
	}

	@Column(name = "OPENING_BALANCE", precision = 10)
	public Double getOpeningBalance() {
		return this.openingBalance;
	}

	public void setOpeningBalance(Double openingBalance) {
		this.openingBalance = openingBalance;
	}

	@Column(name = "ACCOUNTS_AMOUNT", precision = 10)
	public Double getAccountsAmount() {
		return this.accountsAmount;
	}

	public void setAccountsAmount(Double accountsAmount) {
		this.accountsAmount = accountsAmount;
	}

	
	
	@Column(name = "ACCOUNTS_BALANCE", precision = 10)
	public Double getAccountsBalance() {
		return accountsBalance;
	}

	public void setAccountsBalance(Double accountsBalance) {
		this.accountsBalance = accountsBalance;
	}


	@Column(name = "PROBLEM_AMOUNT", precision = 10)
	public Double getProblemAmount() {
		return this.problemAmount;
	}

	public void setProblemAmount(Double problemAmount) {
		this.problemAmount = problemAmount;
	}

	@Column(name = "RECONCILIATION_USER", length = 20)
	public String getReconciliationUser() {
		return this.reconciliationUser;
	}

	public void setReconciliationUser(String reconciliationUser) {
		this.reconciliationUser = reconciliationUser;
	}

	@Column(name = "REVIEW_USER", length = 20)
	public String getReviewUser() {
		return this.reviewUser;
	}

	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}

	@Column(name = "REVIEW_DEPT", length = 50)
	public String getReviewDept() {
		return this.reviewDept;
	}

	public void setReviewDept(String reviewDept) {
		this.reviewDept = reviewDept;
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

	@Column(name = "RECONCILIATION_STATUS", precision = 22, scale = 0)
	public Long getReconciliationStatus() {
		return this.reconciliationStatus;
	}

	public void setReconciliationStatus(Long reconciliationStatus) {
		this.reconciliationStatus = reconciliationStatus;
	}

	@Column(name = "MAILTO_NUM", precision = 22, scale = 0)
	public Long getMailtoNum() {
		return this.mailtoNum;
	}

	public void setMailtoNum(Long mailtoNum) {
		this.mailtoNum = mailtoNum;
	}

	@Column(name = "PRINT_NUM", precision = 22, scale = 0)
	public Long getPrintNum() {
		return this.printNum;
	}

	public void setPrintNum(Long printNum) {
		this.printNum = printNum;
	}

	@Column(name = "EXPORT_NUM", precision = 22, scale = 0)
	public Long getExportNum() {
		return this.exportNum;
	}

	public void setExportNum(Long exportNum) {
		this.exportNum = exportNum;
	}

	/*@Column(name = "CREATE_DEPT", length = 50)
	public String getCreateDept() {
		return this.createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}*/
	

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

	@Column(name = "UPDATE_DEPT", length = 50)
	public String getUpdateDept() {
		return this.updateDept;
	}

	public void setUpdateDept(String updateDept) {
		this.updateDept = updateDept;
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

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "ORDERFIELDS", length = 200)
	public String getOrderfields() {
		return this.orderfields;
	}

	public void setOrderfields(String orderfields) {
		this.orderfields = orderfields;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return departName;
	}
	
	
	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "VERIFICATION_STATUS", precision = 22, scale = 0)
	public Long getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(Long verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	@Column(name = "VERIFICATION_AMOUNT", precision = 10)
	public Double getVerificationAmount() {
		return verificationAmount;
	}

	public void setVerificationAmount(Double verificationAmount) {
		this.verificationAmount = verificationAmount;
	}
	
	
	@Column(name = "ELIMINATION_AMOUNT", precision = 10, scale = 0)
	public Double getEliminationAmount() {
		return eliminationAmount;
	}

	public void setEliminationAmount(Double eliminationAmount) {
		this.eliminationAmount = eliminationAmount;
	}
	
	
}