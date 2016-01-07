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

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * OprStoreArea entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_STORE_AREA",uniqueConstraints = @UniqueConstraint(columnNames = "AREA_NAME"))
public class OprStoreArea implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String areaName;//区域名称
	private String tranMode;//运输方式
	private String cpArea;//代理区域
	private String towhere;//去向
	private Date createTime;//创建时间
	private String createName;//创建人
	private Date updateTime;//修改时间
	private String updateName;//修改人
	private String ts;//时间戳
	private Long departId;//部门编号
	private String takeMoke;//提货方式
	private String distributionMode;//配送方式
	private String overmemoDepart;//内部交接终端部门

	// Constructors

	/** default constructor */
	public OprStoreArea() {
	}

	/** minimal constructor */
	public OprStoreArea(Long id, String areaName) {
		this.id = id;
		this.areaName = areaName;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_STORE_AREA")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "AREA_NAME", unique = true, nullable = false, length = 20)
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "TRAN_MODE", length = 20)
	public String getTranMode() {
		return this.tranMode;
	}

	public void setTranMode(String tranMode) {
		this.tranMode = tranMode;
	}

	@Column(name = "CP_AREA", length = 50)
	public String getCpArea() {
		return cpArea;
	}

	public void setCpArea(String cpArea) {
		this.cpArea = cpArea;
	}

	@Column(name = "TOWHERE", length = 20)
	public String getTowhere() {
		return this.towhere;
	}

	public void setTowhere(String towhere) {
		this.towhere = towhere;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Temporal(TemporalType.DATE)
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

	@Column(name = "TS")
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

	@Column(name = "TAKE_MOKE", length = 20)
	public String getTakeMoke() {
		return this.takeMoke;
	}

	public void setTakeMoke(String takeMoke) {
		this.takeMoke = takeMoke;
	}
	@Column(name = "DISTRIBUTION_MODE", length = 20)
	public String getDistributionMode() {
		return distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}
	@Column(name = "OVERMEMO_DEPART", length = 20)
	public String getOvermemoDepart() {
		return overmemoDepart;
	}

	public void setOvermemoDepart(String overmemoDepart) {
		this.overmemoDepart = overmemoDepart;
	}
	
}