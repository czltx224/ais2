package com.xbwl.sys.vo;
// default package

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Formula;

import com.xbwl.rbac.entity.SysUser;
import com.xbwl.sys.web.StationAction;

/**
 * SysStation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class SysStationVo implements java.io.Serializable {

	// Fields

	private Long stationId;
	private String stationName;//岗位名称
	private Long parentStationId;//上级岗位ID
	private String parentStationName;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private boolean isLeaf;

	// Constructors

	/** default constructor */
	public SysStationVo() {
	}

	/** minimal constructor */
	public SysStationVo(Long stationId) {
		this.stationId = stationId;
	}


	// Property accessors
	public Long getStationId() {
		return this.stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	public String getStationName() {
		return this.stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Long getParentStationId() {
		return this.parentStationId;
	}

	public void setParentStationId(Long parentStationId) {
		this.parentStationId = parentStationId;
	}

	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getUpdateTime() {
		return this.updateTime;
	}
	

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	
	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Formula("(select decode(count(*),0,1,0) from sys_station ar where ar.parent_Station_Id=station_Id ) ")
	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * @return the parentStationName
	 */
	public String getParentStationName() {
		return parentStationName;
	}

	/**
	 * @param parentStationName the parentStationName to set
	 */
	public void setParentStationName(String parentStationName) {
		this.parentStationName = parentStationName;
	}
	
	
}