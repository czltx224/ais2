package com.xbwl.sys.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Formula;

/**
 * BasArea entity. @author MyEclipse Persistence Tools
 */
public class BasAreaVo implements java.io.Serializable {

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
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private boolean isLeaf;
	private String areaRank;
	private Long cusId;//供应商Id
	
	
	/**
	 * @return the createName
	 */
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
	 * @return the createTime
	 */
	@JSON(format="yyyy-MM-dd")
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
	 * @return the updateName
	 */
	public String getUpdateName() {
		return updateName;
	}

	/**
	 * @param updateName the updateName to set
	 */
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	/**
	 * @return the updateTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the isLeaf
	 */
	@Formula("(select decode(count(*),0,1,0) from sys_station ar where ar.parent_Station_Id=station_Id ) ")
	public boolean isLeaf() {
		return isLeaf;
	}

	/**
	 * @param isLeaf the isLeaf to set
	 */
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	// Constructors
	/** default constructor */
	public BasAreaVo() {
	}

	/** minimal constructor */
	public BasAreaVo(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

		public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getDistriDepartName() {
		return this.distriDepartName;
	}

	public void setDistriDepartName(String distriDepartName) {
		this.distriDepartName = distriDepartName;
	}

	public String getEndDepartName() {
		return this.endDepartName;
	}

	public void setEndDepartName(String endDepartName) {
		this.endDepartName = endDepartName;
	}

		public String getDevelpMode() {
		return develpMode;
	}

	public void setDevelpMode(String develpMode) {
		this.develpMode = develpMode;
	}
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public Long getDistriDepartId() {
		return distriDepartId;
	}

	public void setDistriDepartId(Long distriDepartId) {
		this.distriDepartId = distriDepartId;
	}
	public Long getEndDepartId() {
		return endDepartId;
	}

	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
	}

	public String getAreaRank() {
		return areaRank;
	}

	public void setAreaRank(String areaRank) {
		this.areaRank = areaRank;
	}

	/**
	 * @return the cusId
	 */
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