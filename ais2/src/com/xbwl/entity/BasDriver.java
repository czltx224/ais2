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

/**
 * BasDriver entity.
 * Caozhili
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BAS_DRIVER")
public class BasDriver implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;//1
	private String userCode;//工号2
	private String driverName;//姓名3
	private Long sex;//性别（0:女,1:男）4
	private Long driverAge;//驾龄5
	private String address;//住址6
	private String cityCode;//城市7
	private String postalCode;//邮编8
	private String phone;//电话9
	private String identityCard;//驾驶证号码10
	private Long departId;//部门11
	private Date startDate;//雇佣日期12
	private Date stopDate;//解雇日期13
	private String stopFlag;//0:正常,1:解雇14
	private String createName;//创建人15
	private Date createTime;//创建日期16
	private String updateName;//最后更新人17
	private Date updateTime;//最后更新日期18
	private String ts;//时间戳19

	// Constructors

	/** default constructor */
	public BasDriver() {
	}

	/** minimal constructor */
	public BasDriver(Long id, String driverName, Long driverAge,
			String address, String phone, String identityCard, Long departId,
			Date startDate, Date stopDate) {
		this.id = id;
		this.driverName = driverName;
		this.driverAge = driverAge;
		this.address = address;
		this.phone = phone;
		this.identityCard = identityCard;
		this.departId = departId;
		this.startDate = startDate;
		this.stopDate = stopDate;
	}

	/** full constructor */
	public BasDriver(Long id, String userCode, String driverName, Long sex,
			Long driverAge, String address, String cityCode, String postalCode,
			String phone, String identityCard, Long departId, Date startDate,
			Date stopDate, String stopFlag, String createName, Date createTime,
			String updateName, Date updateTime, String ts) {
		this.id = id;
		this.userCode = userCode;
		this.driverName = driverName;
		this.sex = sex;
		this.driverAge = driverAge;
		this.address = address;
		this.cityCode = cityCode;
		this.postalCode = postalCode;
		this.phone = phone;
		this.identityCard = identityCard;
		this.departId = departId;
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.stopFlag = stopFlag;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName="SEQ_BAS_DRIVER ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "USER_CODE", length = 5)
	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(name = "DRIVER_NAME", nullable = false, length = 30)
	public String getDriverName() {
		return this.driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	@Column(name = "SEX", precision = 22, scale = 0)
	public Long getSex() {
		return this.sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	@Column(name = "DRIVER_AGE", nullable = false, precision = 22, scale = 0)
	public Long getDriverAge() {
		return this.driverAge;
	}

	public void setDriverAge(Long driverAge) {
		this.driverAge = driverAge;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "CITY_CODE", length = 20)
	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(name = "POSTAL_CODE", length = 20)
	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "PHONE", nullable = false, length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "IDENTITY_CARD", nullable = false, length = 20)
	public String getIdentityCard() {
		return this.identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	@Column(name = "DEPART_ID", nullable = false, precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "START_DATE", nullable = false, length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "STOP_DATE", length = 7)
	public Date getStopDate() {
		return this.stopDate;
	}

	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}

	@Column(name = "STOP_FLAG", length = 1)
	public String getStopFlag() {
		return this.stopFlag;
	}

	public void setStopFlag(String stopFlag) {
		this.stopFlag = stopFlag;
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

	@Column(name = "TS")
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

}