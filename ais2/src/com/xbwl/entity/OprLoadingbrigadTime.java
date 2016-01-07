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
 * OprLoadingbrigadTimeId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_LOADINGBRIGAD_TIME")
public class OprLoadingbrigadTime implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Date startDate;//排班开始时间
	private Date endDate;//排班开始时间
	private Long departId;//业务部门
	private String departName;//业务部门名字
	private String createName;
	private Date createTime;
	private Date updateTime;
	private String updateName;
	private String ts;
	private Long loadingbrigadId;//装卸组ID
	private Long groupId;//分拨组ID
	
	// Constructors
	@Column(name = "GROUP_ID", precision = 22, scale = 0)
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/** default constructor */
	public OprLoadingbrigadTime() {
	}

	/** full constructor */
	public OprLoadingbrigadTime(Long id, Date startDate, Date endDate,
			Long departId, String departName, String createName, Date createTime,
			Date updateTime, String updateName, String ts, Long loadingbrigadId) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.departId = departId;
		this.departName = departName;
		this.createName = createName;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.updateName = updateName;
		this.ts = ts;
		this.loadingbrigadId = loadingbrigadId;
	}

	// Property accessors

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_LOADINGBRIGAD_TIME")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JSON(format = "HH:mm")
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JSON(format = "HH:mm")
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	@Column(name = "CREATE_NAME", length = 20)
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

	@JSON(format = "yyyy-MM-dd HH:mm")
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

	@Column(name = "TS", length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "LOADINGBRIGAD_ID", precision = 22, scale = 0)
	public Long getLoadingbrigadId() {
		return this.loadingbrigadId;
	}

	public void setLoadingbrigadId(Long loadingbrigadId) {
		this.loadingbrigadId = loadingbrigadId;
	}


}