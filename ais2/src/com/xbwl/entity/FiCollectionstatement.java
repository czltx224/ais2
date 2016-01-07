package com.xbwl.entity;

// default package

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * FiPaymentcollectionstatement entity:代收货款对账单.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_COLLECTIONSTATEMENT")
public class FiCollectionstatement implements java.io.Serializable,
		AuditableEntity {
	private Long id;
	private Long customerId;//客商ID
	private String customerName;//客商
	private Date endDate;//对账结束时间
	private Date stateDate;//对账开始时间
	private Double amount;//金额
	private String reconciliationUser;//对账员
	private Long reconciliationStatus; //对账状态
	private Long mailtoNum;//发送邮件次数
	private Long printNum;//打印次数
	private Long exportNum;//导出次数
	private Long createDeptid;//所属部门
	private String createDept;
	private String createName;
	private Date createTime;
	private String reviewUser;//审核人
	private String reviewDept;//审核部门
	private Date reviewDate;//审核日期
	private String reviewRemark;//审核备注
	private String updateDept;
	private Date updateTime;
	private String updateName;
	private String ts;
	private String orderfields;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_COLLECTIONSTATEMENT")
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

	@Column(name = "CUSTOMER_NAME", length = 200)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "STATE_DATE", length = 7)
	public Date getStateDate() {
		return this.stateDate;
	}

	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "RECONCILIATION_USER", length = 20)
	public String getReconciliationUser() {
		return this.reconciliationUser;
	}

	public void setReconciliationUser(String reconciliationUser) {
		this.reconciliationUser = reconciliationUser;
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

	@Column(name = "CREATE_DEPT", length = 50)
	public String getCreateDept() {
		return this.createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
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
	
	@Column(name = "CREATE_DEPTID", precision = 22, scale = 0)
	public Long getCreateDeptid() {
		return this.createDeptid;
	}

	public void setCreateDeptid(Long createDeptid) {
		this.createDeptid = createDeptid;
	}
}