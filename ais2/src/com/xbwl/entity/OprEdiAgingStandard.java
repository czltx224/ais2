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
 * 中转时效标准实体类
 * OprEdiAgingStandard entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_EDI_AGING_STANDARD")
public class OprEdiAgingStandard implements java.io.Serializable,
		AuditableEntity {

	// Fields

	private Long id;
	private Double transitHour;
	private String deptName;
	private Long deptId;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Double caroutHour;
	private Double signhour;

	// Constructors

	/** default constructor */
	public OprEdiAgingStandard() {
	}

	/** minimal constructor */
	public OprEdiAgingStandard(Long id, Double transitHour, String deptName,
			Long deptId, Double caroutHour, Double signhour) {
		this.id = id;
		this.transitHour = transitHour;
		this.deptName = deptName;
		this.deptId = deptId;
		this.caroutHour = caroutHour;
		this.signhour = signhour;
	}

	/** full constructor */
	public OprEdiAgingStandard(Long id, Double transitHour, String deptName,
			Long deptId, String createName, Date createTime, String updateName,
			Date updateTime, String ts, Double caroutHour, Double signhour) {
		this.id = id;
		this.transitHour = transitHour;
		this.deptName = deptName;
		this.deptId = deptId;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.caroutHour = caroutHour;
		this.signhour = signhour;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_EDI_AGING_STANDARD")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TRANSIT_HOUR", nullable = false, precision = 8)
	public Double getTransitHour() {
		return this.transitHour;
	}

	public void setTransitHour(Double transitHour) {
		this.transitHour = transitHour;
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

	@Column(name = "CAROUT_HOUR", nullable = false, precision = 8)
	public Double getCaroutHour() {
		return this.caroutHour;
	}

	public void setCaroutHour(Double caroutHour) {
		this.caroutHour = caroutHour;
	}

	@Column(name = "SIGNHOUR", nullable = false, precision = 8)
	public Double getSignhour() {
		return this.signhour;
	}

	public void setSignhour(Double signhour) {
		this.signhour = signhour;
	}

}