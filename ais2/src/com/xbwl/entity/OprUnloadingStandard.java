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
import com.xbwl.common.utils.XbwlInt;

/**
 * OprUnloadingStandard entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_UNLOADING_STANDARD")
public class OprUnloadingStandard implements java.io.Serializable ,AuditableEntity{

	// Fields

	private Long id;//ID主键
	private Date createTime;//创建时间
	private String createName;//创建人
	private Date updateTime;//修改时间
	private String updateName;//修改人
	private String ts;//时间戳
	
	@XbwlInt(autoDepart=false)
	private Long departId;//部门编号
	
	@XbwlInt(autoDepart=false)
	private String departName;//部门名称
	
	private String carType;//车辆类型
	private Long unloadingStandardTime;//卸货标准时长

	// Constructors

	/** default constructor */
	public OprUnloadingStandard() {
	}

	/** full constructor */
	public OprUnloadingStandard(Long id, Date createTime, String createName,
			Date updateTime, String updateName, String ts, Long departId,
			String departName, String carType, Long unloadingStandardTime) {
		this.id = id;
		this.createTime = createTime;
		this.createName = createName;
		this.updateTime = updateTime;
		this.updateName = updateName;
		this.ts = ts;
		this.departId = departId;
		this.departName = departName;
		this.carType = carType;
		this.unloadingStandardTime = unloadingStandardTime;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_UNLOADING_STANDARD ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "CREATE_TIME", nullable = false, length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME", nullable = false, length = 50)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", nullable = false, length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATE_NAME", nullable = false, length = 50)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "TS", nullable = false, length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "DEPART_ID", nullable = false, precision = 20, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "DEPART_NAME", nullable = false, length = 50)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "CAR_TYPE", nullable = false, length = 20)
	public String getCarType() {
		return this.carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	@Column(name = "UNLOADING_STANDARD_TIME", nullable = false, precision = 22, scale = 0)
	public Long getUnloadingStandardTime() {
		return this.unloadingStandardTime;
	}

	public void setUnloadingStandardTime(Long unloadingStandardTime) {
		this.unloadingStandardTime = unloadingStandardTime;
	}

}