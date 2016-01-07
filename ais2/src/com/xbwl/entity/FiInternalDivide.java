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
 * FiInternalDivide entity.
 * 
 * @author MyEclipse Persistence Tools 
 * 
 * 内部结算划分实体类
 */
@Entity
@Table(name = "FI_INTERNAL_DIVIDE")
public class FiInternalDivide implements java.io.Serializable, AuditableEntity {

	// Fields

	private Long id;
	private Long dno;// 配送单号
	private Long bearDepartId;// 承担部门id
	private String bearDepartName;// 承担部门
	private Long benefitDepartId;// 受益部门id
	private String benefitDepartName;// 受益部门id
	private Double amount;// 金额
	private String remark;// 备注
	private Long auditStatus;// 审核状态：0作废，1未审核，2：已审核
	private String auditName;// 审核人
	private Date auditTime;// 审核时间
	private String auditRemark;// 审核备注
	private Long departId;// 创建部门id
	private String departName;// 创建部门
	private Date createTime;// 创建时间
	private String createName;// 创建人
	private Date updateTime;// 修改时间
	private String updateName;// 修改人
	private String ts;// 时间戳

	// Constructors

	/** default constructor */
	public FiInternalDivide() {
		this.auditStatus=1l;
	}

	/** minimal constructor */
	public FiInternalDivide(Long id, Long auditStatus) {
		this.id = id;
		this.auditStatus = auditStatus;
	}

	/** full constructor */
	public FiInternalDivide(Long id, Long dno, Long bearDepartId,
			String bearDepartName, Long benefitDepartId,
			String benefitDepartName, Double amount, String remark,
			Long auditStatus, String auditName, Date auditTime,
			String auditRemark, Long departId, String departName,
			Date createTime, String createName, Date updateTime,
			String updateName, String ts) {
		this.id = id;
		this.dno = dno;
		this.bearDepartId = bearDepartId;
		this.bearDepartName = bearDepartName;
		this.benefitDepartId = benefitDepartId;
		this.benefitDepartName = benefitDepartName;
		this.amount = amount;
		this.remark = remark;
		this.auditStatus = auditStatus;
		this.auditName = auditName;
		this.auditTime = auditTime;
		this.auditRemark = auditRemark;
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
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_INTERNAL_DIVIDE")
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

	@Column(name = "BEAR_DEPART_ID", precision = 22, scale = 0)
	public Long getBearDepartId() {
		return this.bearDepartId;
	}

	public void setBearDepartId(Long bearDepartId) {
		this.bearDepartId = bearDepartId;
	}

	@Column(name = "BEAR_DEPART_NAME", length = 50)
	public String getBearDepartName() {
		return this.bearDepartName;
	}

	public void setBearDepartName(String bearDepartName) {
		this.bearDepartName = bearDepartName;
	}

	@Column(name = "BENEFIT_DEPART_ID", precision = 22, scale = 0)
	public Long getBenefitDepartId() {
		return this.benefitDepartId;
	}

	public void setBenefitDepartId(Long benefitDepartId) {
		this.benefitDepartId = benefitDepartId;
	}

	@Column(name = "BENEFIT_DEPART_NAME", length = 50)
	public String getBenefitDepartName() {
		return this.benefitDepartName;
	}

	public void setBenefitDepartName(String benefitDepartName) {
		this.benefitDepartName = benefitDepartName;
	}

	@Column(name = "AMOUNT", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "AUDIT_STATUS", nullable = false, precision = 22, scale = 0)
	public Long getAuditStatus() {
		return this.auditStatus;
	}

	public void setAuditStatus(Long auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Column(name = "AUDIT_NAME", length = 20)
	public String getAuditName() {
		return this.auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "AUDIT_TIME", length = 7)
	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "AUDIT_REMARK", length = 250)
	public String getAuditRemark() {
		return this.auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
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

}