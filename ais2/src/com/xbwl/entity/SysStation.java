package com.xbwl.entity;
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

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;
import com.xbwl.rbac.entity.SysUser;
import com.xbwl.sys.web.StationAction;

/**
 * SysStation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_STATION")
public class SysStation implements java.io.Serializable ,AuditableEntity {

	// Fields

	private Long stationId;
	private String stationName;//岗位名称
	private Long parentStationId;//上级岗位ID
	private String parentStationName;
	//private SysStation parentStation;//上级岗位
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private boolean isLeaf;

	// Constructors

	/** default constructor */
	public SysStation() {
	}

	/** minimal constructor */
	public SysStation(Long stationId) {
		this.stationId = stationId;
	}


	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_SYS_STATION")
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "STATION_ID", unique = true, nullable = false, precision = 22, scale = 0)
	@JSON(name="id")
	public Long getStationId() {
		return this.stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	@JSON(name="text")
	@Column(name = "STATION_NAME", length = 50)
	public String getStationName() {
		return this.stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	@Column(name = "PARENT_STATION_ID", precision = 22, scale = 0)
	public Long getParentStationId() {
		return this.parentStationId;
	}

	public void setParentStationId(Long parentStationId) {
		this.parentStationId = parentStationId;
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

	
	
	
	@Formula("(select decode(count(*),0,1,0) from sys_station ar where ar.parent_Station_Id=station_Id ) ")
	public boolean isLeaf() {
		return isLeaf;
	}
	/**
	 * @return the ts
	 */
	@Column(name = "TS", length = 20)
	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
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