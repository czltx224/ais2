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

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;
import com.xbwl.common.utils.XbwlInt;

/**
 * MarketingTarget entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MARKETING_TARGET")
public class MarketingTarget implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String targetType;//指标类型
	@XbwlInt(autoDepart=false)
	private Long departId;//部门ID
	@XbwlInt(autoDepart=false)
	private String departName;//部门名称
	private Double targetNum;//指标值
	private Date targetTime;//指标时间
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private String cusDepartCode;//客服部门Code
	private String cusDepartName;//客服部门名称
	private String customerService;//客服员名称
	// Constructors

	/** default constructor */
	public MarketingTarget() {
	}

	/** minimal constructor */
	public MarketingTarget(Long id) {
		this.id = id;
	}

	/** full constructor */
	public MarketingTarget(Long id, String targetType, Long departId,
			String departName, Double targetNum, Date targetTime,
			String createName, Date createTime, String updateName,
			Date updateTime) {
		this.id = id;
		this.targetType = targetType;
		this.departId = departId;
		this.departName = departName;
		this.targetNum = targetNum;
		this.targetTime = targetTime;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_MARKETING_TARGET",allocationSize=1)
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TARGET_TYPE", length = 50)
	public String getTargetType() {
		return this.targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "DEPART_NAME", length = 100)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "TARGET_NUM", precision = 10)
	public Double getTargetNum() {
		return this.targetNum;
	}

	public void setTargetNum(Double targetNum) {
		this.targetNum = targetNum;
	}

	@JSON(format="yyyy-MM")
	@Column(name = "TARGET_TIME", length = 7)
	public Date getTargetTime() {
		return this.targetTime;
	}

	public void setTargetTime(Date targetTime) {
		this.targetTime = targetTime;
	}

	@Column(name = "CREATE_NAME", length = 50)
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

	@Column(name = "UPDATE_NAME", length = 50)
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

	/**
	 * @return the ts
	 */
	@Column(name = "TS")
	public String getTs() {
		return ts;
	}

	/**
	 * @param ts the ts to set
	 */
	public void setTs(String ts) {
		this.ts = ts;
	}

	/**
	 * @return the cusDepartId
	 */
	@Column(name = "CUS_DEPART_CODE")
	public String getCusDepartCode() {
		return cusDepartCode;
	}

	/**
	 * @param cusDepartCode the cusDepartCode to set
	 */
	public void setCusDepartCode(String cusDepartCode) {
		this.cusDepartCode = cusDepartCode;
	}

	/**
	 * @return the cusDepartName
	 */
	@Column(name = "CUS_DEPART_NAME")
	public String getCusDepartName() {
		return cusDepartName;
	}

	/**
	 * @param cusDepartName the cusDepartName to set
	 */
	public void setCusDepartName(String cusDepartName) {
		this.cusDepartName = cusDepartName;
	}
	@Column(name = "CUSTOMER_SERVICE")
	public String getCustomerService() {
		return customerService;
	}

	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}
	
	
}