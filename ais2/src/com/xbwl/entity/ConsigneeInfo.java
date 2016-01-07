package com.xbwl.entity;
// default package

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
 * ConsigneeInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CONSIGNEE_INFO")
public class ConsigneeInfo implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String consigneeName;
	private String consigneeTel;
	private String consigneeAddr;
	private String addrType;
	private String distributionMode;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private String city;
	private String town;
	private String street;
	private Long endDepartId;
	private String endDepart;
	private String goWhere;
	private String distributionDepart;
	private Long distributionDepartId;
	private Long cusRecordId;
	private Long goWhereId;
	

	// Constructors

	/** default constructor */
	public ConsigneeInfo() {
	}


	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_CONSIGNEE_INFO ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CONSIGNEE_NAME", nullable = false, length = 100)
	public String getConsigneeName() {
		return this.consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	@Column(name = "CONSIGNEE_TEL", length = 50)
	public String getConsigneeTel() {
		return this.consigneeTel;
	}

	public void setConsigneeTel(String consigneeTel) {
		this.consigneeTel = consigneeTel;
	}

	@Column(name = "CONSIGNEE_ADDR", length = 100)
	public String getConsigneeAddr() {
		return this.consigneeAddr;
	}

	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	@Column(name = "ADDR_TYPE", length = 20)
	public String getAddrType() {
		return this.addrType;
	}

	public void setAddrType(String addrType) {
		this.addrType = addrType;
	}

	@Column(name = "DISTRIBUTION_MODE", length = 20)
	public String getDistributionMode() {
		return this.distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "CREATE_TIME")
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
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name = "ts")
	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	@Column(name = "CITY")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	@Column(name = "TOWN")
	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}
	@Column(name = "STREET")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	@Column(name = "END_DEPART")
	public String getEndDepart() {
		return endDepart;
	}

	public void setEndDepart(String endDepart) {
		this.endDepart = endDepart;
	}

	/**
	 * @return the goWhere
	 */
	@Column(name = "GO_WHERE")
	public String getGoWhere() {
		return goWhere;
	}

	/**
	 * @param goWhere the goWhere to set
	 */
	public void setGoWhere(String goWhere) {
		this.goWhere = goWhere;
	}

	/**
	 * @return the endDepartId
	 */
	@Column(name = "END_DEPART_ID")
	public Long getEndDepartId() {
		return endDepartId;
	}

	/**
	 * @param endDepartId the endDepartId to set
	 */
	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
	}

	/**
	 * @return the distributionDepart
	 */
	@Column(name = "DISTRIBUTION_DEPART")
	public String getDistributionDepart() {
		return distributionDepart;
	}

	/**
	 * @param distributionDepart the distributionDepart to set
	 */
	public void setDistributionDepart(String distributionDepart) {
		this.distributionDepart = distributionDepart;
	}

	/**
	 * @return the distributionDepartId
	 */
	@Column(name = "DISTRIBUTION_DEPART_ID")
	public Long getDistributionDepartId() {
		return distributionDepartId;
	}

	/**
	 * @param distributionDepartId the distributionDepartId to set
	 */
	public void setDistributionDepartId(Long distributionDepartId) {
		this.distributionDepartId = distributionDepartId;
	}


	/**
	 * @return the cusRecordId
	 */
	@Column(name = "CUS_RECORD_ID")
	public Long getCusRecordId() {
		return cusRecordId;
	}


	/**
	 * @param cusRecordId the cusRecordId to set
	 */
	public void setCusRecordId(Long cusRecordId) {
		this.cusRecordId = cusRecordId;
	}


	/**
	 * @return the goWhereId
	 */
	@Column(name = "GO_WHERE_ID")
	public Long getGoWhereId() {
		return goWhereId;
	}


	/**
	 * @param goWhereId the goWhereId to set
	 */
	public void setGoWhereId(Long goWhereId) {
		this.goWhereId = goWhereId;
	}
	
}