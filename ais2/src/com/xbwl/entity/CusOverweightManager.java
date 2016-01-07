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
import javax.persistence.UniqueConstraint;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * CusOverweightManager entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CUS_OVERWEIGHT_MANAGER", uniqueConstraints = @UniqueConstraint(columnNames = "CUS_ID"))
public class CusOverweightManager implements java.io.Serializable, AuditableEntity {

	// Fields

	private Long id;
	private Long cusId;
	private String cusName;//客商名称
	private Double lowWeight;//最低允许超重重量
	private Double overweightRate;//超重费率
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;

	// Constructors

	/** default constructor */
	public CusOverweightManager() {
	}

	/** minimal constructor */
	public CusOverweightManager(Long id) {
		this.id = id;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_CUS_OVERWEIGHT_MANAGER ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUS_ID", unique = true, precision = 22, scale = 0)
	public Long getCusId() {
		return this.cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	@Column(name = "CUS_NAME", length = 200)
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	@Column(name = "LOW_WEIGHT", precision = 22, scale = 0)
	public Double getLowWeight() {
		return this.lowWeight;
	}

	public void setLowWeight(Double lowWeight) {
		this.lowWeight = lowWeight;
	}

	@Column(name = "OVERWEIGHT_RATE", precision = 22, scale = 0)
	public Double getOverweightRate() {
		return this.overweightRate;
	}

	public void setOverweightRate(Double overweightRate) {
		this.overweightRate = overweightRate;
	}

	@Column(name = "CREATE_NAME", length = 200)
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

}