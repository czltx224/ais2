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
 * 资金交接单.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_FUNDSTRANSFER")
public class FiFundstransfer implements java.io.Serializable,AuditableEntity {
	private Long id;
	private Long paymentaccountId; //付款账号ID
	private Long receivablesaccountId; //收款账号ID
	private Long receivablesaccountDeptid;//收款部门ID
	private String receivablesaccountDept;//收款部门
	private Double amount;//结算金额
	private String remark;//备注
	private Long status=1L;//状态：1:未核销、2:已核销、0:已作废
	private Long departId; //创建业务部门id(必输参数)
	private String departName;//创建业务部门
	private Date createTime;
	private String createName;
	private String updateDept;
	private Date updateTime;
	private String updateName;
	private String ts;
	private Long createDepartId;
	private String createDepartName;
	private Long capitalTypeId;//交接单类型(40250:现金交接单,40251:银行转账单)
	private String sourceData; //数据来源
	private Long sourceNo; //来源单号
	private Long paymentStatus=0L;//付款确认状态：0:未确认、1:已确认
	private Date paymentTime;//付款确认时间
	private Long receivablesStatus=0L;//收款确认状态：0:未确认、1:已确认
	private Date receivablesTime;//收款确认时间
	//private String verificationType;//核销类型(实收单)
	//private Long verificationNo; //核销单号
	

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_FUNDSTRANSFER")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PAYMENTACCOUNT_ID", precision = 22, scale = 0)
	public Long getPaymentaccountId() {
		return this.paymentaccountId;
	}

	public void setPaymentaccountId(Long paymentaccountId) {
		this.paymentaccountId = paymentaccountId;
	}

	@Column(name = "RECEIVABLESACCOUNT_ID", precision = 22, scale = 0)
	public Long getReceivablesaccountId() {
		return this.receivablesaccountId;
	}

	public void setReceivablesaccountId(Long receivablesaccountId) {
		this.receivablesaccountId = receivablesaccountId;
	}

	@Column(name = "RECEIVABLESACCOUNT_DEPTID", precision = 22, scale = 0)
	public Long getReceivablesaccountDeptid() {
		return this.receivablesaccountDeptid;
	}

	public void setReceivablesaccountDeptid(Long receivablesaccountDeptid) {
		this.receivablesaccountDeptid = receivablesaccountDeptid;
	}

	@Column(name = "RECEIVABLESACCOUNT_DEPT", length = 50)
	public String getReceivablesaccountDept() {
		return this.receivablesaccountDept;
	}

	public void setReceivablesaccountDept(String receivablesaccountDept) {
		this.receivablesaccountDept = receivablesaccountDept;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	@Column(name = "CREATE_DEPART_ID")
	public Long getCreateDepartId() {
		return createDepartId;
	}

	public void setCreateDepartId(Long createDepartId) {
		this.createDepartId = createDepartId;
	}
	
	@Column(name = "CREATE_DEPART_NAME")
	public String getCreateDepartName() {
		return createDepartName;
	}

	public void setCreateDepartName(String createDepartName) {
		this.createDepartName = createDepartName;
	}

	@Column(name = "CAPITALTYPE_ID")
	public Long getCapitalTypeId() {
		return capitalTypeId;
	}

	public void setCapitalTypeId(Long capitalTypeId) {
		this.capitalTypeId = capitalTypeId;
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

	@Column(name = "PAYMENT_STATUS", precision = 22, scale = 0)
	public Long getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Long paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "PAYMENT_TIME", length = 7)
	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
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

	/*
	@Column(name = "VERIFICATION_TYPE", length = 20)
	public String getVerificationType() {
		return verificationType;
	}

	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}

	@Column(name = "VERIFICATION_NO", precision = 22, scale = 0)
	public Long getVerificationNo() {
		return verificationNo;
	}

	public void setVerificationNo(Long verificationNo) {
		this.verificationNo = verificationNo;
	}*/
	
	


}