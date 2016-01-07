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

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * EnterPortKpi entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_ENTER_PORT_KPI")
public class OprEnterPortKpi implements java.io.Serializable, AuditableEntity {

	// Fields

	private Long id;
	private String kpiName;// KPI名称
	private Double managerTarget;// 管理指标
	private String countRange;// 统计维度
	private String kpiParent;// 上级KPI
	private String kpiType;// KPI类型
	private Double warningPercent;// 预警偏差
	private Long kpiYear;// 年度
	private String kpiRole;// KPI作用
	private String deptName;// 部门名称
	private Long deptId;// 部门编号
	private String createName;// 创建人
	private Date createTime;// 创建时间
	private String updateName;// 修改人
	private Date updateTime;// 修改时间
	private String ts;// 时间戳

	private String parentDepartName;// 上级部门名称
	private Long parentDepartId;// 上级部门ID
	private String operateType;// 操作类型

	// Constructors

	/** default constructor */
	public OprEnterPortKpi() {
	}

	/** minimal constructor */
	public OprEnterPortKpi(Long id) {
		this.id = id;
	}

	/** full constructor */
	public OprEnterPortKpi(Long id, String kpiName, Double managerTarget,
			String countRange, String kpiParent, String kpiType,
			Double warningPercent, Long kpiYear, String kpiRole,
			String deptName, Long deptId, String createName, Date createTime,
			String updateName, Date updateTime, String ts) {
		this.id = id;
		this.kpiName = kpiName;
		this.managerTarget = managerTarget;
		this.countRange = countRange;
		this.kpiParent = kpiParent;
		this.kpiType = kpiType;
		this.warningPercent = warningPercent;
		this.kpiYear = kpiYear;
		this.kpiRole = kpiRole;
		this.deptName = deptName;
		this.deptId = deptId;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_ENTER_PORT_KPI ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "KPI_NAME", length = 20)
	public String getKpiName() {
		return this.kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	@Column(name = "MANAGER_TARGET")
	public Double getManagerTarget() {
		return this.managerTarget;
	}

	public void setManagerTarget(Double managerTarget) {
		this.managerTarget = managerTarget;
	}

	@Column(name = "KPI_PARENT", length = 20)
	public String getKpiParent() {
		return this.kpiParent;
	}

	public void setKpiParent(String kpiParent) {
		this.kpiParent = kpiParent;
	}

	@Column(name = "KPI_TYPE", length = 20)
	public String getKpiType() {
		return this.kpiType;
	}

	public void setKpiType(String kpiType) {
		this.kpiType = kpiType;
	}

	@Column(name = "WARNING_PERCENT", precision = 3)
	public Double getWarningPercent() {
		return this.warningPercent;
	}

	public void setWarningPercent(Double warningPercent) {
		this.warningPercent = warningPercent;
	}

	@Column(name = "KPI_YEAR", precision = 22, scale = 0)
	public Long getKpiYear() {
		return this.kpiYear;
	}

	public void setKpiYear(Long kpiYear) {
		this.kpiYear = kpiYear;
	}

	@Column(name = "KPI_ROLE", length = 200)
	public String getKpiRole() {
		return this.kpiRole;
	}

	public void setKpiRole(String kpiRole) {
		this.kpiRole = kpiRole;
	}

	@Column(name = "DEPT_NAME", length = 50)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "DEPT_ID", precision = 22)
	public Long getDeptId() {
		return deptId;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME")
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

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_TIME")
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

	@Column(name = "PARENT_DEPART_NAME", length = 50)
	public String getParentDepartName() {
		return parentDepartName;
	}

	public void setParentDepartName(String parentDepartName) {
		this.parentDepartName = parentDepartName;
	}

	@Column(name = "PARENT_DEPART_ID")
	public Long getParentDepartId() {
		return parentDepartId;
	}

	public void setParentDepartId(Long parentDepartId) {
		this.parentDepartId = parentDepartId;
	}

	@Column(name = "OPERATE_TYPE", length = 20)
	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	@Column(name = "COUNT_RANGE", length = 10)
	public String getCountRange() {
		return countRange;
	}

	public void setCountRange(String countRange) {
		this.countRange = countRange;
	}
}