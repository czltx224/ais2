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
 * OprScanningLimitation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_SCANNING_LIMITATION")
public class OprScanningLimitation implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String operationType;//操作方式
	private String remark;//备注
	private String countTime;//自动统计时间
	private String countRange;//统计维度（年/月/周/日）
	private String deptName;//部门名称
	private Long deptId;//部门ID
	private String createName;//创建人
	private Date createTime;//创建时间
	private String updateName;//修改人
	private Date updateTime;//修改时间
	private String ts;//时间戳
	
	private String scanStandardTime;
	private Long scanStandardDay;
	private String scanStartTime;
	private String scanEndTime;

	// Constructors

	/** default constructor */
	public OprScanningLimitation() {
	}

	/** minimal constructor */
	public OprScanningLimitation(Long id, String deptName, Long deptId) {
		this.id = id;
		this.deptName = deptName;
		this.deptId = deptId;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_SCANNING_LIMITATION")
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
	@Column(name = "SCAN_STANDARD_TIME", length = 5)
	public String getScanStandardTime() {
		return this.scanStandardTime;
	}

	public void setScanStandardTime(String scanStandardTime) {
		this.scanStandardTime = scanStandardTime;
	}

	@Column(name = "SCAN_START_TIME", length = 5)
	public String getScanStartTime() {
		return this.scanStartTime;
	}

	public void setScanStartTime(String scanStartTime) {
		this.scanStartTime = scanStartTime;
	}

	@Column(name = "SCAN_END_TIME", length = 5)
	public String getScanEndTime() {
		return this.scanEndTime;
	}

	@Column(name = "SCAN_STANDARD_DAY", precision = 22, scale = 0)
	public void setScanEndTime(String scanEndTime) {
		this.scanEndTime = scanEndTime;
	}

	public Long getScanStandardDay() {
		return scanStandardDay;
	}

	public void setScanStandardDay(Long scanStandardDay) {
		this.scanStandardDay = scanStandardDay;
	}
	
}