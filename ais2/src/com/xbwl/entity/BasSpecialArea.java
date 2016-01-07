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

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * BasSpecialArea entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BAS_SPECIAL_AREA")
public class BasSpecialArea implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id; 
	private String areaName;  //区名
	private Long areaId;
	private String develpMode;  //s配送方式
	private Long cusId;
	private String distriDepartName;  //配送部门
	private Long distriDepartId;
	private String endDepartName;  //终端部门
	private Long endDepartId;  
	private String createName;   
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long departId;  //操作人业务部门
	private String departName;
	private String cusName;  //供应商
	private String areaType; //地区类型
	private String areaRank;//地区等级
	
	// Constructors
	/** default constructor */
	public BasSpecialArea() {
	}

	/** minimal constructor */
	public BasSpecialArea(Long id, String areaName) {
		this.id = id;
		this.areaName = areaName;
	}

	/** full constructor */
	public BasSpecialArea(Long id, String areaName, Long areaId,
			String develpMode, Long cusId, String distriDepartName,
			Long distriDepartId, String endDepartName, Long endDepartId,
			String createName, Date createTime, String updateName,
			Date updateTime, String ts, Long departId, String departName) {
		this.id = id;
		this.areaName = areaName;
		this.areaId = areaId;
		this.develpMode = develpMode;
		this.cusId = cusId;
		this.distriDepartName = distriDepartName;
		this.distriDepartId = distriDepartId;
		this.endDepartName = endDepartName;
		this.endDepartId = endDepartId;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.departId = departId;
		this.departName = departName;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_BAS_SPECIAL_AREA ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "AREA_NAME", nullable = false, length = 20)
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "AREA_ID", precision = 22, scale = 0)
	public Long getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	@Column(name = "DEVELP_MODE", length = 20)
	public String getDevelpMode() {
		return this.develpMode;
	}

	public void setDevelpMode(String develpMode) {
		this.develpMode = develpMode;
	}

	@Column(name = "CUS_ID", precision = 22, scale = 0)
	public Long getCusId() {
		return this.cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	@Column(name = "DISTRI_DEPART_NAME", length =50)
	public String getDistriDepartName() {
		return this.distriDepartName;
	}

	public void setDistriDepartName(String distriDepartName) {
		this.distriDepartName = distriDepartName;
	}

	@Column(name = "DISTRI_DEPART_ID", precision = 22, scale = 0)
	public Long getDistriDepartId() {
		return this.distriDepartId;
	}

	public void setDistriDepartId(Long distriDepartId) {
		this.distriDepartId = distriDepartId;
	}

	@Column(name = "END_DEPART_NAME", length = 50)
	public String getEndDepartName() {
		return this.endDepartName;
	}

	public void setEndDepartName(String endDepartName) {
		this.endDepartName = endDepartName;
	}

	@Column(name = "END_DEPART_ID", precision = 22, scale = 0)
	public Long getEndDepartId() {
		return this.endDepartId;
	}

	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
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

	@Column(name = "CUS_NAME", length =100)
	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	

	@Column(name = "AREA_TYPE", length =50)
	/**
	 * @return the areaType
	 */
	public String getAreaType() {
		return areaType;
	}

	/**
	 * @param areaType the areaType to set
	 */
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	/**
	 * @return the areaRank
	 */
	@Column(name = "AREA_RANK", length =50)
	public String getAreaRank() {
		return areaRank;
	}

	/**
	 * @param areaRank the areaRank to set
	 */
	public void setAreaRank(String areaRank) {
		this.areaRank = areaRank;
	}
	
}