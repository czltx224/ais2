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
 * 
 * @ClassName: FiInvoice entity.
 * @Description: 发票管理
 * @author oysz
 * @date Jul 14, 2011 5:22:46 PM
 */

@Entity
@Table(name = "FI_INVOICE")
public class FiInvoice implements java.io.Serializable, AuditableEntity {

	// Fields

	private Long id;
	@XbwlInt(autoDepart=false)
	private Long departId;// 部门ID
	private Long customerId;// 客商ID
	private String sourceData; //数据来源(必输参数)
	private Long sourceNo; //来源单号(必输参数)
	private Long status=1L;// 状态(0作废1正常,2已寄出，3已收到)
	private Long amount;// 开票金额
	private Long taxes;// 税金
	private String invoiceType;// 发票类型
	private String paymentType;// 收付类型
	private String applicant;// 申请人
	private String handled;// 经手人
	private String drawer;// 开票人
	private String remark;// 备注
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String ts;
	private String orderfields;
	private Long reviewStatus=1L; // 审核状态(1未审核，2已审核)
	private String reviewUser;
	private String reviewRemark;
	private Date reviewTime;
	private String expressNo;
	// Constructors

	/** default constructor */
	public FiInvoice() {
	}

	/** minimal constructor */
	public FiInvoice(Long id) {
		this.id = id;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_INVOICE")
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

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "AMOUNT", precision = 22, scale = 0)
	public Long getAmount() {
		return this.amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	@Column(name = "TAXES", precision = 22, scale = 0)
	public Long getTaxes() {
		return this.taxes;
	}

	public void setTaxes(Long taxes) {
		this.taxes = taxes;
	}

	@Column(name = "INVOICE_TYPE", length = 10)
	public String getInvoiceType() {
		return this.invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	@Column(name = "PAYMENT_TYPE", length = 10)
	public String getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Column(name = "APPLICANT", length = 10)
	public String getApplicant() {
		return this.applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	@Column(name = "HANDLED", length = 10)
	public String getHandled() {
		return this.handled;
	}

	public void setHandled(String handled) {
		this.handled = handled;
	}

	@Column(name = "DRAWER", length = 10)
	public String getDrawer() {
		return this.drawer;
	}

	public void setDrawer(String drawer) {
		this.drawer = drawer;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "TS")
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

	@Column(name = "REVIEW_REMARK", length = 500)
	public String getReviewRemark() {
		return reviewRemark;
	}

	public void setReviewRemark(String reviewRemark) {
		this.reviewRemark = reviewRemark;
	}

	@Column(name = "REVIEW_TIME", length = 7)
	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	@Column(name = "EXPRESS_NO", length = 50)
	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	
}