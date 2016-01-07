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

/**
 * BasCusService entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BAS_CUS_SERVICE")
public class BasCusService implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long cusId;//客商编号
	private String serviceName;//客服员名称
	private Long serviceId;//客服员ID
	private String serviceDepart;//客服部门名称
	private Date createTime;
	private String serviceDepartCode;//客服部门编码
	private String createName;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long departId;//业务部门ID
	private String departName;//业务部门名称

	// Constructors

	/** default constructor */
	public BasCusService() {
	}

	/** minimal constructor */
	public BasCusService(Long id, Long cusId, String serviceName,
			Long serviceId, String serviceDepart, Date createTime,
			String createName, String updateName, Date updateTime) {
		this.id = id;
		this.cusId = cusId;
		this.serviceName = serviceName;
		this.serviceId = serviceId;
		this.serviceDepart = serviceDepart;
		this.createTime = createTime;
		this.createName = createName;
		this.updateName = updateName;
		this.updateTime = updateTime;
	}

	/** full constructor */
	public BasCusService(Long id, Long cusId, String serviceName,
			Long serviceId, String serviceDepart, Date createTime,
			String createName, String updateName, Date updateTime, String ts) {
		this.id = id;
		this.cusId = cusId;
		this.serviceName = serviceName;
		this.serviceId = serviceId;
		this.serviceDepart = serviceDepart;
		this.createTime = createTime;
		this.createName = createName;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_BAS_CUS_SERVICE ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUS_ID", nullable = false, precision = 10, scale = 0)
	public Long getCusId() {
		return this.cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	@Column(name = "SERVICE_NAME")
	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Column(name = "SERVICE_ID", nullable = false, precision = 10, scale = 0)
	public Long getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	@Column(name = "SERVICE_DEPART", nullable = false, length = 50)
	public String getServiceDepart() {
		return this.serviceDepart;
	}

	public void setServiceDepart(String serviceDepart) {
		this.serviceDepart = serviceDepart;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME")
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "UPDATE_NAME")
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

	@Column(name = "TS")
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	/**
	 * @return the serviceDepartCode
	 */
	@Column(name = "SERVICE_DEPART_CODE")
	public String getServiceDepartCode() {
		return serviceDepartCode;
	}

	/**
	 * @param serviceDepartCode the serviceDepartCode to set
	 */
	public void setServiceDepartCode(String serviceDepartCode) {
		this.serviceDepartCode = serviceDepartCode;
	}

	/**
	 * @return the departId
	 */
	@Column(name="DEPART_ID")
	public Long getDepartId() {
		return departId;
	}

	/**
	 * @param departId the departId to set
	 */
	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	/**
	 * @return the departName
	 */
	@Column(name="DEPART_NAME")
	public String getDepartName() {
		return departName;
	}

	/**
	 * @param departName the departName to set
	 */
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	
}