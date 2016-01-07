package com.xbwl.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * OprStocktake entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_STOCKTAKE", schema = "AISUSER")
public class OprStocktake implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String oprMan;  //盘点人	
	private Date createTime;   //创建时间	
	private String createName;
	private Date updateTime;
	private String updateName;
	private String storageArea;  //库存区域
	private String orderFields;   //排序字段
	private String ts;
	private Set<OprStocktakeDetail> oprStocktakeDetails = new HashSet<OprStocktakeDetail>(
			0);

	// Constructors

	@Column(name = "ORDERFIELDS", length = 50)
	public String getOrderFields() {
		return orderFields;
	}

	public void setOrderFields(String orderFields) {
		this.orderFields = orderFields;
	}

	/** default constructor */
	public OprStocktake() {
	}

	@Column(name = "STORAGE_AREA", length = 20)
	public String getStorageArea() {
		return storageArea;
	}

	public void setStorageArea(String storageArea) {
		this.storageArea = storageArea;
	}

	/** minimal constructor */
	public OprStocktake(Long id) {
		this.id = id;
	}


	public OprStocktake(Long id, String oprMan, Date createTime,
			String createName, Date updateTime, String updateName,
			String storageArea, String orderFields, String ts,
			Set<OprStocktakeDetail> oprStocktakeDetails) {
		super();
		this.id = id;
		this.oprMan = oprMan;
		this.createTime = createTime;
		this.createName = createName;
		this.updateTime = updateTime;
		this.updateName = updateName;
		this.storageArea = storageArea;
		this.orderFields = orderFields;
		this.ts = ts;
		this.oprStocktakeDetails = oprStocktakeDetails;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_STOCKTAKE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OPR_MAN", length = 20)
	public String getOprMan() {
		return this.oprMan;
	}

	public void setOprMan(String oprMan) {
		this.oprMan = oprMan;
	}

	@JSON(format = "yyyy-MM-dd")
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

	@JSON(format = "yyyy-MM-dd")
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "oprStocktake")
	public Set<OprStocktakeDetail> getOprStocktakeDetails() {
		return this.oprStocktakeDetails;
	}

	public void setOprStocktakeDetails(
			Set<OprStocktakeDetail> oprStocktakeDetails) {
		this.oprStocktakeDetails = oprStocktakeDetails;
	}

}