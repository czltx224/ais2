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
 * 应收应付表.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_PAYMENT")
public class FiPayment implements java.io.Serializable,AuditableEntity {
	private Long id;
	private Long paymentType; //收付类型(1:收款单/2:付款单)(必输参数)
	private Long paymentStatus=0L; //收付状态：0作废、1未收款、2已收款、3部分收款、4未付款、5已付款、6部分付款、7到付转欠款、8异常、9挂账
	private String costType; //费用类型:代收货款/到付提送费/到付增值费/预付提送费/预付增值费/其它收入/对账(必输参数)
	private String documentsType; //单据大类:收入\成本\对账\预存款\代收货款(必输参数)
	private String documentsSmalltype; //单据小类：配送单/对账单/配送单/预存款单(必输参数)
	private Long documentsNo; //单据小类对应的单号(必输参数)
	private String penyType; //结算类型(现结、月结)(必输参数)
	private String penyJenis; //结算方式(现金、银行、POS机、支票、预付冲销、应付冲应收)
	private Double amount=0.0; //应收付金额(必输参数)
	private Double settlementAmount=0.0; //实收付金额
	private Double abnormalAmount=0.0;//异常到付款金额
	private Double eliminationAmount=0.0; //冲销金额
	private String workflowNo;//流程号
	private String contacts; //往来单位:没在客商档案中的客商，内部客户。
	private Long customerId; //客商id(必输参数)
	private String customerName; //客商表名称(必输参数)
	private String sourceData; //数据来源(必输参数)
	private Long sourceNo; //来源单号(必输参数)
	private String collectionUser; //收款责任人:自提：创建人，送货：送货员，外发：外发员(必输参数)
	private Long paymentMark; //收付级别:收银员:0/财务:1
	private String entrustDept; //委托部门
	private Date entrustTime; //委托时间
	private String entrustUser; //委托人
	private String entrustRemark; //委托备注
	private String createName; 
	private Date createTime; 
	private String createRemark; 
	
	@XbwlInt(autoDepart=false)
	private Long departId; //创建业务部门id(必输参数)
	
	@XbwlInt(autoDepart=false)
	private String departName;//创建业务部门
	private Long entrustDeptid;  //委托部门ID
	private String updateDept; 
	private Date updateTime; 
	private String updateName; 
	private String ts; 
	private Long status=1l;//状态(0:作废,1:正常)
	private Long incomeDepartId;//收入部门
	private Long reviewStatus=1L;//审核状态(0未审核,1已审核)
	private String reviewUser;//审核人
	private Date reviewDate;//审核时间

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_PAYMENT")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PAYMENT_TYPE", precision = 22, scale = 0)
	public Long getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(Long paymentType) {
		this.paymentType = paymentType;
	}

	@Column(name = "PAYMENT_STATUS", precision = 22, scale = 0)
	public Long getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(Long paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Column(name = "COST_TYPE", length = 20)
	public String getCostType() {
		return this.costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	@Column(name = "DOCUMENTS_TYPE", length = 20)
	public String getDocumentsType() {
		return this.documentsType;
	}

	public void setDocumentsType(String documentsType) {
		this.documentsType = documentsType;
	}

	@Column(name = "DOCUMENTS_SMALLTYPE", length = 20)
	public String getDocumentsSmalltype() {
		return this.documentsSmalltype;
	}

	public void setDocumentsSmalltype(String documentsSmalltype) {
		this.documentsSmalltype = documentsSmalltype;
	}

	@Column(name = "DOCUMENTS_NO", precision = 22, scale = 0)
	public Long getDocumentsNo() {
		return this.documentsNo;
	}

	public void setDocumentsNo(Long documentsNo) {
		this.documentsNo = documentsNo;
	}

	@Column(name = "PENY_TYPE", length = 20)
	public String getPenyType() {
		return this.penyType;
	}

	public void setPenyType(String penyType) {
		this.penyType = penyType;
	}

	@Column(name = "PENY_JENIS", length = 20)
	public String getPenyJenis() {
		return this.penyJenis;
	}

	public void setPenyJenis(String penyJenis) {
		this.penyJenis = penyJenis;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "SETTLEMENT_AMOUNT", precision = 10, scale = 0)
	public Double getSettlementAmount() {
		return this.settlementAmount;
	}

	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	
	@Column(name = "ELIMINATION_AMOUNT", precision = 10, scale = 0)
	public Double getEliminationAmount() {
		return eliminationAmount;
	}

	public void setEliminationAmount(Double eliminationAmount) {
		this.eliminationAmount = eliminationAmount;
	}

	@Column(name = "WORKFLOW_NO", length = 20)
	public String getWorkflowNo() {
		return workflowNo;
	}

	public void setWorkflowNo(String workflowNo) {
		this.workflowNo = workflowNo;
	}

	@Column(name = "CONTACTS", length = 50)
	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
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

	@Column(name = "SOURCE_DATA", length = 20)
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

	@Column(name = "COLLECTION_USER", length = 20)
	public String getCollectionUser() {
		return this.collectionUser;
	}

	public void setCollectionUser(String collectionUser) {
		this.collectionUser = collectionUser;
	}

	@Column(name = "PAYMENT_MARK", precision = 22, scale = 0)
	public Long getPaymentMark() {
		return this.paymentMark;
	}

	public void setPaymentMark(Long paymentMark) {
		this.paymentMark = paymentMark;
	}

	@Column(name = "ENTRUST_DEPT", length = 50)
	public String getEntrustDept() {
		return this.entrustDept;
	}

	public void setEntrustDept(String entrustDept) {
		this.entrustDept = entrustDept;
	}

	@Column(name = "ENTRUST_TIME", length = 7)
	public Date getEntrustTime() {
		return this.entrustTime;
	}

	public void setEntrustTime(Date entrustTime) {
		this.entrustTime = entrustTime;
	}

	@Column(name = "ENTRUST_USER", length = 20)
	public String getEntrustUser() {
		return this.entrustUser;
	}

	public void setEntrustUser(String entrustUser) {
		this.entrustUser = entrustUser;
	}

	@Column(name = "ENTRUST_REMARK", length = 500)
	public String getEntrustRemark() {
		return this.entrustRemark;
	}

	public void setEntrustRemark(String entrustRemark) {
		this.entrustRemark = entrustRemark;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return createName;
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

	@Column(name = "CREATE_REMARK", length = 500)
	public String getCreateRemark() {
		return this.createRemark;
	}

	public void setCreateRemark(String createRemark) {
		this.createRemark = createRemark;
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

	@Column(name = "ENTRUST_DEPTID", precision = 22, scale = 0)
	public Long getEntrustDeptid() {
		return this.entrustDeptid;
	}

	public void setEntrustDeptid(Long entrustDeptid) {
		this.entrustDeptid = entrustDeptid;
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
	
	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "ABNORMAL_AMOUNT", precision = 10, scale = 0)
	public Double getAbnormalAmount() {
		return abnormalAmount;
	}

	public void setAbnormalAmount(Double abnormalAmount) {
		this.abnormalAmount = abnormalAmount;
	}

	@Column(name = "INCOME_DEPART_ID", precision = 22, scale = 0)
	public Long getIncomeDepartId() {
		return incomeDepartId;
	}

	public void setIncomeDepartId(Long incomeDepartId) {
		this.incomeDepartId = incomeDepartId;
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

	public Date getReviewDate() {
		return reviewDate;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "REVIEW_DATE", length = 7)
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	
	
	

}