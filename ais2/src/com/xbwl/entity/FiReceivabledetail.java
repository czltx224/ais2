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
import com.xbwl.common.utils.XbwlInt;

/**
 * 往来账款明细：FiReceivabledetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_RECEIVABLEDETAIL")
public class FiReceivabledetail implements java.io.Serializable,
		AuditableEntity {

	// Fields

	private Long id;
	private String flightmainno;// 航空主单号(必输参数)
	private Long dno;// 配送单号(必输参数)
	private Double amount=0.0;// 金额(必输参数)
	private String problemType;// 问题账款类型
	private Double problemAmount=0.0;// 问题账款金额
	private Long problemStatus;// 问题账款状态(1:已登记/2:已审核/3:已销核/0:已取消)
	private String problemRemark;// 问题账款备注
	private Long workflowNo;// 流程号
	private String sourceData;// 数据来源
	private Long sourceNo;// 来源单号(必输参数)
	private Long reconciliationNo;// 对账单号
	private Long reconciliationStatus=1L;// 对账状态(1:未对账\2:未审核\3:已审核\0:已作废)
	//private Long createDeptId; //创建部门id
	//private String createDept;//创建部门
	private Date createTime;// 创建时间
	private String createName;// 创建人
	private Date updateTime;// 修改时间
	private String updateName;// 修改人
	private String ts;// 时间戳
	private String orderfields;//
	private Long customerId;// 客商ID(必输参数)
	private String customerName;// 客商名称(必输参数)
	private String costType;// 费用类型(必输参数)
	
	@XbwlInt(autoDepart=false)
	private Long departId; //创建业务部门id(必输参数)
	
	@XbwlInt(autoDepart=false)
	private String departName;//创建业务部门
	
	//private Long piece;// 件数
	//private Double cusWeight;// 计费重量
	//private Double bulk;// 体积
	
	private Long batch; //批次号
	
	private Double verificationAmount=0.0; //核销金额
	private Date verificationTime; //核销时间
	private Long verificationStatus=1L; //核销状态(1:未核销,2:部分核销,3:已核销)
	private Long paymentType; //收付类型(1收款/2付款)
	//private Long status=1L; //收付状态：0作废、1正常
	private String remark;// 备注
	private Long collectionStatus=1L;//代收货款收银状态(0:空,1:未收银,2:已收银)
	private Double eliminationAmount=0.0; //冲销金额
	
	private Long reviewStatus=1L;//审核状态(0未审核,1已审核)
	private String reviewUser;//审核人
	private Date reviewDate;//审核时间
	private String reviewRemark;//审核备注
	

	public FiReceivabledetail() {
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_RECEIVABLEDETAIL")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "FLIGHTMAINNO", length = 50)
	public String getFlightmainno() {
		return this.flightmainno;
	}

	public void setFlightmainno(String flightmainno) {
		this.flightmainno = flightmainno;
	}

	@Column(name = "D_NO", precision = 22, scale = 0)
	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "PROBLEM_TYPE", precision = 10)
	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	@Column(name = "PROBLEM_AMOUNT", precision = 10)
	public Double getProblemAmount() {
		return this.problemAmount;
	}

	public void setProblemAmount(Double problemAmount) {
		this.problemAmount = problemAmount;
	}

	@Column(name = "PROBLEM_STATUS", precision = 1, scale = 0)
	public Long getProblemStatus() {
		return this.problemStatus;
	}

	public void setProblemStatus(Long problemStatus) {
		this.problemStatus = problemStatus;
	}

	@Column(name = "PROBLEM_REMARK", length = 500)
	public String getProblemRemark() {
		return this.problemRemark;
	}

	public void setProblemRemark(String problemRemark) {
		this.problemRemark = problemRemark;
	}

	@Column(name = "WORKFLOW_NO", precision = 22, scale = 0)
	public Long getWorkflowNo() {
		return this.workflowNo;
	}

	public void setWorkflowNo(Long workflowNo) {
		this.workflowNo = workflowNo;
	}

	@Column(name = "SOURCE_DATA", length = 50)
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

	@Column(name = "RECONCILIATION_NO", precision = 22, scale = 0)
	public Long getReconciliationNo() {
		return this.reconciliationNo;
	}

	public void setReconciliationNo(Long reconciliationNo) {
		this.reconciliationNo = reconciliationNo;
	}

	@Column(name = "RECONCILIATION_STATUS", precision = 1, scale = 0)
	public Long getReconciliationStatus() {
		return this.reconciliationStatus;
	}

	public void setReconciliationStatus(Long reconciliationStatus) {
		this.reconciliationStatus = reconciliationStatus;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
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

	@Column(name = "COST_TYPE", length = 20)
	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
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

	@Column(name = "BATCH", precision = 22, scale = 0)
	public Long getBatch() {
		return batch;
	}

	public void setBatch(Long batch) {
		this.batch = batch;
	}

	
	@Column(name = "VERIFICATION_AMOUNT", precision = 10)
	public Double getVerificationAmount() {
		return verificationAmount;
	}

	public void setVerificationAmount(Double verificationAmount) {
		this.verificationAmount = verificationAmount;
	}

	
	@Column(name = "VERIFICATION_TIME", length = 7)
	public Date getVerificationTime() {
		return verificationTime;
	}

	public void setVerificationTime(Date verificationTime) {
		this.verificationTime = verificationTime;
	}

	@Column(name = "VERIFICATION_STATUS", precision = 22, scale = 0)
	public Long getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(Long verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	@Column(name = "PAYMENT_TYPE", precision = 22, scale = 0)
	public Long getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Long paymentType) {
		this.paymentType = paymentType;
	}

	/*@Column(name = "STATUS", precision = 1, scale = 0)
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}*/
	
	

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "COLLECTION_STATUS", precision = 1, scale = 0)
	public Long getCollectionStatus() {
		return collectionStatus;
	}

	public void setCollectionStatus(Long collectionStatus) {
		this.collectionStatus = collectionStatus;
	}
	
	@Column(name = "ELIMINATION_AMOUNT", precision = 10, scale = 0)
	public Double getEliminationAmount() {
		return eliminationAmount;
	}

	public void setEliminationAmount(Double eliminationAmount) {
		this.eliminationAmount = eliminationAmount;
	}
	
	@Column(name = "REVIEW_STATUS", precision = 22, scale = 0)
	public Long getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Long reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	@Column(name = "REVIEW_USER", length = 20)
	public String getReviewUser() {
		return reviewUser;
	}

	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "REVIEW_DATE", length = 7)
	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	
	@Column(name = "REVIEW_REMARK", length = 500)
	public String getReviewRemark() {
		return reviewRemark;
	}

	public void setReviewRemark(String reviewRemark) {
		this.reviewRemark = reviewRemark;
	}


}