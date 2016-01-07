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
 * BasSpecialTrainLineDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BAS_SPECIAL_TRAIN_LINE_DETAIL")
public class BasSpecialTrainLineDetail implements java.io.Serializable,AuditableEntity{

	// Fields

	private Long id;
	private Long specialTrainLineId;
	private String areaName;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;

	private Long departId;
	// Constructors

	/** default constructor */
	public BasSpecialTrainLineDetail() {
	}

	/** minimal constructor */
	public BasSpecialTrainLineDetail(Long id) {
		this.id = id;
	}

	/** full constructor */
	public BasSpecialTrainLineDetail(Long id, Long specialTrainLineId,
			String areaName, String createName, Date createTime,
			String updateName, Date updateTime, String ts) {
		this.id = id;
		this.specialTrainLineId = specialTrainLineId;
		this.areaName = areaName;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_BAS_SPECIAL_LINE_DETAIL")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SPECIAL_TRAIN_LINE_ID", precision = 10, scale = 0)
	public Long getSpecialTrainLineId() {
		return this.specialTrainLineId;
	}

	public void setSpecialTrainLineId(Long specialTrainLineId) {
		this.specialTrainLineId = specialTrainLineId;
	}

	@Column(name = "AREA_NAME", length = 50)
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd")
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

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
}