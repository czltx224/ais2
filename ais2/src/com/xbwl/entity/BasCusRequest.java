package com.xbwl.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * BasCusRequest entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BAS_CUS_REQUEST")
public class BasCusRequest implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String cpName;//代理名称
	private Long cpId;//代理ID
	private String cusName;//客户名称
	private String cusTel;//客户电话
	private String requestStage;//需求阶段
	private String requestType;//需求类型
	private String request;//客户需求
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;

	// Constructors

	/** default constructor */
	public BasCusRequest() {
	}

	/** minimal constructor */
	public BasCusRequest(Long id, String cpName, String cusName, String cusTel,
			String requestStage, String requestType, String request) {
		this.id = id;
		this.cpName = cpName;
		this.cusName = cusName;
		this.cusTel = cusTel;
		this.requestStage = requestStage;
		this.requestType = requestType;
		this.request = request;
	}

	/** full constructor */
	public BasCusRequest(Long id, String cpName, String cusName, String cusTel,
			String requestStage, String requestType, String request,
			String createName, Date createtime, String updateName,
			Date updateTime, String ts) {
		this.id = id;
		this.cpName = cpName;
		this.cusName = cusName;
		this.cusTel = cusTel;
		this.requestStage = requestStage;
		this.requestType = requestType;
		this.request = request;
		this.createName = createName;
		this.createTime = createtime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_BAS_CUS_REQUEST")
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CP_NAME", nullable = false, length = 20)
	public String getCpName() {
		return this.cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	@Column(name = "CUS_NAME", nullable = false, length = 20)
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	@Column(name = "CUS_TEL", nullable = false, length = 20)
	public String getCusTel() {
		return this.cusTel;
	}

	public void setCusTel(String cusTel) {
		this.cusTel = cusTel;
	}

	@Column(name = "REQUEST_STAGE", nullable = false, length = 10)
	public String getRequestStage() {
		return this.requestStage;
	}

	public void setRequestStage(String requestStage) {
		this.requestStage = requestStage;
	}

	@Column(name = "REQUEST_TYPE", nullable = false, length = 10)
	public String getRequestType() {
		return this.requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	@Column(name = "REQUEST", nullable = false, length = 200)
	public String getRequest() {
		return this.request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATETIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createtime) {
		this.createTime = createtime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Temporal(TemporalType.DATE)
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

	/**
	 * @return the cpId
	 */
	@Column(name = "CP_ID")
	public Long getCpId() {
		return cpId;
	}

	/**
	 * @param cpId the cpId to set
	 */
	public void setCpId(Long cpId) {
		this.cpId = cpId;
	}
	
}