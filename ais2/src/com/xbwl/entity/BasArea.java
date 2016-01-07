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
import org.hibernate.annotations.Formula;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * BasArea entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BAS_AREA")
public class BasArea implements java.io.Serializable,AuditableEntity{

	// Fields    
	private Long id;
	private String areaName;//地区名称
	private String areaType;//地区类型
	private Long distriDepartId;//配送部门ID
	private String distriDepartName;//配送部门名称
	private Long endDepartId;//终端配送部门ID
	private String endDepartName;//终端配送部门名称
	private String develpMode;//配送方式
	private String cusName;//客商名称
	private Long parentId;//上级部门ID
	private String parentName;
	private String createName;//创建人
	private Date createTime;//创建时间
	private String updateName;//修改人
	private Date updateTime;//修改时间
	private String ts;
	private String areaRank;//地址级别
	private Long cusId;//供应商ID
	
	private boolean isLeaf;

	// Constructors
	/** default constructor */
	public BasArea() {
	}

	/** minimal constructor */
	public BasArea(Long id) {
		this.id = id;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_BAS_AREA ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JSON(name="text")
	@Column(name = "AREA_NAME", length = 20)
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "AREA_TYPE", precision = 22, scale = 0)
		public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	@Column(name = "DISTRI_DEPART_NAME", length = 20)
	public String getDistriDepartName() {
		return this.distriDepartName;
	}

	public void setDistriDepartName(String distriDepartName) {
		this.distriDepartName = distriDepartName;
	}

	@Column(name = "END_DEPART_NAME", length = 20)
	public String getEndDepartName() {
		return this.endDepartName;
	}

	public void setEndDepartName(String endDepartName) {
		this.endDepartName = endDepartName;
	}

	@Column(name = "DEVELP_MODE", precision = 22, scale = 0)
		public String getDevelpMode() {
		return develpMode;
	}

	public void setDevelpMode(String develpMode) {
		this.develpMode = develpMode;
	}
	@Column(name = "CUS_NAME", length = 20)
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	@Column(name = "PARENT_ID", precision = 22, scale = 0)
	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "PARENT_NAME", length = 20)
	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	/**
	 * @return the createName
	 */
	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return createName;
	}

	/**
	 * @param createName the createName to set
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}

	/**
	 * @return the updateName
	 */
	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return updateName;
	}

	/**
	 * @param updateName the updateName to set
	 */
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	@Column(name = "TS")
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	@Column(name = "DISTRI_DEPART_ID", length = 20)
	public Long getDistriDepartId() {
		return distriDepartId;
	}

	public void setDistriDepartId(Long distriDepartId) {
		this.distriDepartId = distriDepartId;
	}
	@Column(name = "END_DEPART_ID", length = 20)
	public Long getEndDepartId() {
		return endDepartId;
	}

	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
	}

	@Formula("(select decode(count(*),0,1,0) from bas_area ar where ar.parent_id=ID ) ")
	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * @return the createTime
	 */
	@JSON(format="yyyy-MM-dd")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	@JSON(format="yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name = "AREA_RANK", length = 10)
	public String getAreaRank() {
		return areaRank;
	}

	public void setAreaRank(String areaRank) {
		this.areaRank = areaRank;
	}

	/**
	 * @return the cusId
	 */
	@Column(name = "CUS_ID")
	public Long getCusId() {
		return cusId;
	}

	/**
	 * @param cusId the cusId to set
	 */
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}
	
	


	
}