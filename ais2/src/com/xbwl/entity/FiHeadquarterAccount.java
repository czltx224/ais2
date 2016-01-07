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
 * 总部交账管理.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_HEADQUARTER_ACCOUNT")
public class FiHeadquarterAccount implements java.io.Serializable,AuditableEntity {

	private Long id;
	private String receiptNo;//收据号
	private Date receiptDate;// 收据日期
	private Double amount;//收据金额
	private String remark;//备注
	private Long verificationCapId;//核销账号ID
	private String verificationUser;//核销人
	private Date verificationDate;//核销日期
	private String verificationRemark;//核销备注
	private Long verificationStatus=1L;//核销状态(1未核销,2已核销)
	private Long status=1L;//状态(0作废,1正常)
	private String updateName;
	private Date updateTime;
	private String createName;
	private Date createTime;
	private Long departId;
	private String departName;
	private String ts;
	
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_HEADQUARTER_ACCOUNT")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "RECEIPT_NO", length = 50)
	public String getReceiptNo() {
		return this.receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "RECEIPT_DATE", length = 7)
	public Date getReceiptDate() {
		return this.receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
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

	@Column(name = "VERIFICATION_USER", length = 20)
	public String getVerificationUser() {
		return this.verificationUser;
	}

	public void setVerificationUser(String verificationUser) {
		this.verificationUser = verificationUser;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VERIFICATION_DATE", length = 7)
	public Date getVerificationDate() {
		return this.verificationDate;
	}

	public void setVerificationDate(Date verificationDate) {
		this.verificationDate = verificationDate;
	}

	@Column(name = "VERIFICATION_REMARK", length = 500)
	public String getVerificationRemark() {
		return this.verificationRemark;
	}

	public void setVerificationRemark(String verificationRemark) {
		this.verificationRemark = verificationRemark;
	}

	@Column(name = "VERIFICATION_STATUS", precision = 22, scale = 0)
	public Long getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(Long verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Temporal(TemporalType.DATE)
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

	@Column(name = "VERIFICATION_CAP_ID", precision = 22, scale = 0)
	public Long getVerificationCapId() {
		return verificationCapId;
	}

	public void setVerificationCapId(Long verificationCapId) {
		this.verificationCapId = verificationCapId;
	}
	
	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

}