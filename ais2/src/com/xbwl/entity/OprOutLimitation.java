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
 * OprOutLimitation entity.
 * 
 * @author MyEclipse Persistence Tools 出库时效标准实体类
 */
@Entity
@Table(name = "OPR_OUT_LIMITATION")
public class OprOutLimitation implements java.io.Serializable, AuditableEntity {

	// Fields

	private Long id;
	private String operationType;// 操作方式
	private String remark;// 备注
	private String countTime;// 自动统计时间
	private String deptName;// 部门名称
	private Long deptId;// 部门编号
	private String createName;// 创建人
	private Date createTime;// 创建时间
	private String updateName;// 修改人
	private Date updateTime;// 修改时间
	private String ts;// 时间戳
	private String countRange;// 统计维度（年/月/周/日）

	private String outStandardTime;
	private Long outStandardDay;
	private String outStartTime;
	private String outEndTime;
	private String timeStage;//时间段

	// Constructors

	/** default constructor */
	public OprOutLimitation() {
	}

	/** minimal constructor */
	public OprOutLimitation(Long id, String deptName, Long deptId) {
		this.id = id;
		this.deptName = deptName;
		this.deptId = deptId;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_OUT_LIMITATION")
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

	@JSON(format = "yyyy-MM-dd hh:mm")
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

	@JSON(format = "yyyy-MM-dd hh:mm")
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

	@Column(name = "COUNT_RANGE", length = 10)
	public String getCountRange() {
		return this.countRange;
	}

	public void setCountRange(String countRange) {
		this.countRange = countRange;
	}

	@Column(name = "OUT_STANDARD_TIME", length = 5)
	public String getOutStandardTime() {
		return this.outStandardTime;
	}

	public void setOutStandardTime(String outStandardTime) {
		this.outStandardTime = outStandardTime;
	}

	@Column(name = "OUT_START_TIME", length = 5)
	public String getOutStartTime() {
		return this.outStartTime;
	}

	public void setOutStartTime(String outStartTime) {
		this.outStartTime = outStartTime;
	}

	@Column(name = "OUT_END_TIME", length = 5)
	public String getOutEndTime() {
		return this.outEndTime;
	}

	public Long getOutStandardDay() {
		return outStandardDay;
	}

	@Column(name = "OUT_STANDARD_DAY", length = 5)
	public void setOutStandardDay(Long outStandardDay) {
		this.outStandardDay = outStandardDay;
	}

	public void setOutEndTime(String outEndTime) {
		this.outEndTime = outEndTime;
	}

	@Column(name = "TIME_STAGE", length = 20)
	public String getTimeStage() {
		return timeStage;
	}

	public void setTimeStage(String timeStage) {
		this.timeStage = timeStage;
	}
}