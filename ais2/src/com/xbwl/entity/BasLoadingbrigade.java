package com.xbwl.entity;

import static javax.persistence.GenerationType.SEQUENCE;

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
 * BasLoadingbrigade entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BAS_LOADINGBRIGADE")
public class BasLoadingbrigade implements java.io.Serializable, AuditableEntity {

	// Fields

	private Long id;
	private String loadingName;
	private Long departId;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private Long manCount;
	private String ts;
	private Long type;

	// Constructors

	/** default constructor */
	public BasLoadingbrigade() {
	}

	/** minimal constructor */
	public BasLoadingbrigade(Long id) {
		this.id = id;
	}

	public BasLoadingbrigade(Long id, String loadingName, Long departId,
			String createName, Date createTime, String updateName,
			Date updateTime, Long manCount, String ts) {
		super();
		this.id = id;
		this.loadingName = loadingName;
		this.departId = departId;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.manCount = manCount;
		this.ts = ts;
	}

	@Column(name = "MAN_COUNT", precision = 22, scale = 0)
	public Long getManCount() {
		return manCount;
	}

	public void setManCount(Long manCount) {
		this.manCount = manCount;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_BAS_LOADINGBRIGADE")
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "LOADING_NAME", length = 20)
	public String getLoadingName() {
		return this.loadingName;
	}

	public void setLoadingName(String loadingName) {
		this.loadingName = loadingName;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "CREATE_NAME")
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME")
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS")
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "TYPE")
	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

}