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
 * FiCheck entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_CHECK", schema = "AISUSER")
public class FiCheck implements java.io.Serializable,AuditableEntity{

	// Fields

	private Long id;
	private Long departId;       //收支票部门ID
	private Long customerId;    //客商ID
	private Double amount;         //支票金额
	private String checkUser;         //支票出票人
	private String checkNo;           // 支票号
	private Date checkDate;          //出票时间
	private String paymentRemark;        // 付款摘要
	private String remark;                            // 备注
	private String updateName;                // 修改人
	private Date updateTime;                     // 修改时间
	private String createName;                           
	private Date createTime;
	private String createDept;        //到达部门
	private Long createDeptid;
	private Long fipaidId;                                  // 实收单号
	private String invalidUser;             //作废
	private Date invalidDate;
	private Long invalidStatus;
	private String reviewUser;           // 审核
	private Date reviewDate;
	private Long reviewStatus;
	private String submitUser;         // 上缴
	private Date submitDate;
	private Long submitStatus;
	private String reachUser;            // 点到
	private Date reachDate;
	private Long reachStatus;
	private String todepositUser;                 //送存
	private Date todepositDate;
	private Long todepositStatus=0L;
	private Long todepositFiCapitaaccountset;//送存账号ID
	private String confirmUser;                 ///到账确认人
	private Date confirmDate;
	private Long confirmStatus=0L;
	private String returnUser;                      //退票
	private Date returnDate;
	private Long returnStatus;
	private String ts ;
	private String departName;
	private String customerName;
	private Long fiPaymentId; // 应收付单号
	

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_CHECK")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "CUSTOMER_ID", precision = 22, scale = 0)
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "CHECK_USER", length = 50)
	public String getCheckUser() {
		return this.checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	@Column(name = "CHECK_NO", length = 50)
	public String getCheckNo() {
		return this.checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "CHECK_DATE", length = 7)
	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name = "PAYMENT_REMARK", length = 500)
	public String getPaymentRemark() {
		return this.paymentRemark;
	}

	public void setPaymentRemark(String paymentRemark) {
		this.paymentRemark = paymentRemark;
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

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
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

	public void setCreateName(String createName ) {
		this.createName = createName ;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_DEPT", length = 50)
	public String getCreateDept() {
		return this.createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	@Column(name = "CREATE_DEPTID", precision = 22, scale = 0)
	public Long getCreateDeptid() {
		return this.createDeptid;
	}

	public void setCreateDeptid(Long createDeptid) {
		this.createDeptid = createDeptid;
	}

	@Column(name = "FIPAID_ID", precision = 22, scale = 0)
	public Long getFipaidId() {
		return this.fipaidId;
	}

	public void setFipaidId(Long fipaidId) {
		this.fipaidId = fipaidId;
	}

	@Column(name = "INVALID_USER", length = 20)
	public String getInvalidUser() {
		return this.invalidUser;
	}

	public void setInvalidUser(String invalidUser) {
		this.invalidUser = invalidUser;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "INVALID_DATE", length = 7)
	public Date getInvalidDate() {
		return this.invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	@Column(name = "INVALID_STATUS", precision = 1, scale = 0)
	public Long getInvalidStatus() {
		return this.invalidStatus;
	}

	public void setInvalidStatus(Long invalidStatus) {
		this.invalidStatus = invalidStatus;
	}

	@Column(name = "REVIEW_USER", length = 20)
	public String getReviewUser() {
		return this.reviewUser;
	}

	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "REVIEW_DATE", length = 7)
	public Date getReviewDate() {
		return this.reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	@Column(name = "REVIEW_STATUS", precision = 1, scale = 0)
	public Long getReviewStatus() {
		return this.reviewStatus;
	}

	public void setReviewStatus(Long reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	@Column(name = "SUBMIT_USER", length = 20)
	public String getSubmitUser() {
		return this.submitUser;
	}

	public void setSubmitUser(String submitUser) {
		this.submitUser = submitUser;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "SUBMIT_DATE", length = 7)
	public Date getSubmitDate() {
		return this.submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	@Column(name = "SUBMIT_STATUS", precision = 1, scale = 0)
	public Long getSubmitStatus() {
		return this.submitStatus;
	}

	public void setSubmitStatus(Long submitStatus) {
		this.submitStatus = submitStatus;
	}

	@Column(name = "REACH_USER", length = 20)
	public String getReachUser() {
		return this.reachUser;
	}

	public void setReachUser(String reachUser) {
		this.reachUser = reachUser;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "REACH_DATE", length = 7)
	public Date getReachDate() {
		return this.reachDate;
	}

	public void setReachDate(Date reachDate) {
		this.reachDate = reachDate;
	}

	@Column(name = "REACH_STATUS", precision = 1, scale = 0)
	public Long getReachStatus() {
		return this.reachStatus;
	}

	public void setReachStatus(Long reachStatus) {
		this.reachStatus = reachStatus;
	}

	@Column(name = "TODEPOSIT_USER", length = 20)
	public String getTodepositUser() {
		return this.todepositUser;
	}

	public void setTodepositUser(String todepositUser) {
		this.todepositUser = todepositUser;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "TODEPOSIT_DATE", length = 7)
	public Date getTodepositDate() {
		return this.todepositDate;
	}

	public void setTodepositDate(Date todepositDate) {
		this.todepositDate = todepositDate;
	}

	@Column(name = "TODEPOSIT_STATUS", precision = 1, scale = 0)
	public Long getTodepositStatus() {
		return this.todepositStatus;
	}

	public void setTodepositStatus(Long todepositStatus) {
		this.todepositStatus = todepositStatus;
	}

	@Column(name = "FI_CAPITAACCOUNTSET_ID", precision = 22, scale = 0)
	public Long getTodepositFiCapitaaccountset() {
		return this.todepositFiCapitaaccountset;
	}

	public void setTodepositFiCapitaaccountset(
			Long todepositFiCapitaaccountset) {
		this.todepositFiCapitaaccountset = todepositFiCapitaaccountset;
	}

	@Column(name = "CONFIRM_USER", length = 20)
	public String getConfirmUser() {
		return this.confirmUser;
	}

	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CONFIRM_DATE", length = 7)
	public Date getConfirmDate() {
		return this.confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	@Column(name = "CONFIRM_STATUS", precision = 1, scale = 0)
	public Long getConfirmStatus() {
		return this.confirmStatus;
	}

	public void setConfirmStatus(Long confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	@Column(name = "RETURN_USER", length = 20)
	public String getReturnUser() {
		return this.returnUser;
	}

	public void setReturnUser(String returnUser) {
		this.returnUser = returnUser;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "RETURN_DATE", length = 7)
	public Date getReturnDate() {
		return this.returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	@Column(name = "RETURN_STATUS", precision = 1, scale = 0)
	public Long getReturnStatus() {
		return this.returnStatus;
	}

	public void setReturnStatus(Long returnStatus) {
		this.returnStatus = returnStatus;
	}

	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "TS", length = 50)
	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	
	@Column(name = "CUSTOMER_NAME", length = 50)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "FI_PAYMENT_ID", nullable = false, precision = 22, scale = 0)
	public Long getFiPaymentId() {
		return this.fiPaymentId;
	}

	public void setFiPaymentId(Long fiPaymentId) {
		this.fiPaymentId = fiPaymentId;
	}

}