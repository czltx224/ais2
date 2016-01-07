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
 * OprSignLimitation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_SIGN_LIMITATION")
public class OprSignLimitation implements java.io.Serializable ,AuditableEntity{

	// Fields

	private Long id;
	private String operationType;
	private String remark;
	private String countTime;
	private String countRange;
	private String deptName;
	private Long deptId;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private String signStartTime;//配车出库时间
	private String signEndTime;//签收录入时间
	private Long signStandardDay;
	private String countStartTime;

	// Constructors

	/** default constructor */
	public OprSignLimitation() {
	}

	/** minimal constructor */
	public OprSignLimitation(Long id, String deptName, Long deptId) {
		this.id = id;
		this.deptName = deptName;
		this.deptId = deptId;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_SIGN_LIMITATION")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OPERATION_TYPE", length = 20)
	public String getOperationType() {
		return this.operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "COUNT_TIME", length = 5)
	public String getCountTime() {
		return this.countTime;
	}

	public void setCountTime(String countTime) {
		this.countTime = countTime;
	}

	@Column(name = "COUNT_RANGE", length = 10)
	public String getCountRange() {
		return this.countRange;
	}

	public void setCountRange(String countRange) {
		this.countRange = countRange;
	}

	@Column(name = "DEPT_NAME", nullable = false, length = 50)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "DEPT_ID", nullable = false, precision = 22, scale = 0)
	public Long getDeptId() {
		return this.deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Column(name = "CREATE_NAME", length = 50)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME", length = 50)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS", length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "SIGN_START_TIME", length = 5)
	public String getSignStartTime() {
		return this.signStartTime;
	}

	public void setSignStartTime(String signStartTime) {
		this.signStartTime = signStartTime;
	}

	@Column(name = "SIGN_END_TIME", length = 5)
	public String getSignEndTime() {
		return this.signEndTime;
	}

	public void setSignEndTime(String signEndTime) {
		this.signEndTime = signEndTime;
	}

	@Column(name = "SIGN_STANDARD_DAY")
	public Long getSignStandardDay() {
		return signStandardDay;
	}

	public void setSignStandardDay(Long signStandardDay) {
		this.signStandardDay = signStandardDay;
	}

	@Column(name = "COUNT_START_TIME", length = 5)
	public String getCountStartTime() {
		return countStartTime;
	}

	public void setCountStartTime(String countStartTime) {
		this.countStartTime = countStartTime;
	}
	
}