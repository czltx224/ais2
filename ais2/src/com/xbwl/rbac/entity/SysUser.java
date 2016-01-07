package com.xbwl.rbac.entity;

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
import com.xbwl.common.orm.hibernate.pojo.NotAutoDepart;
import com.xbwl.common.utils.XbwlInt;
import com.xbwl.entity.SysDepart;
import com.xbwl.entity.SysStation;


/**
 * SysUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_USER", schema = "AISUSER")
public class SysUser implements java.io.Serializable,AuditableEntity{

	// Fields

	private Long id;
	private String userCode;  //工号
	private String loginName; // 登录名称
	private String userName;// 用户名称
	private String password;// 密码
	private Long birthdayType;  // 生日类型
	private Date birthday; //生日
	private Long workstatus;  //工作状态 0 离职 1:正常 
	private Long hrstatus;  //    人事状态 0 试用期 1:正常 
	private String manCode;  // 身份证号码
	private String offTel;  //  办公电话
	private String telPhone;  // 联系电话
	private Long sex;  // 性别
	private Long stationId;   //  从岗
	private String stationIds;  //  从岗Id
	private String duty;  // 岗位职责 
	private String status;  // 状态 0：删除 1：正常
	private Long userLevel;   //用户权限ID
	  @XbwlInt(autoDepart=false)
	private Long departId;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String stationNames;  //  从岗名称 
	private Long bussDepart;
	private String rightDepart;
	private String ts;
	// Constructors

	/** default constructor */
	public SysUser() {
	}

	/** minimal constructor */
	public SysUser(Long id, String status) {
		this.id = id;
		this.status = status;
	}

	/** full constructor */
	public SysUser(Long id, String userCode, String loginName, String userName,
			String password, Long birthdayType, Date birthday, Long workstatus,
			Long hrstatus, String manCode, String offTel, String telPhone,
			Long sex, Long stationId, String stationIds, String duty,
			String status, Long userLevel, Long departId, String createName,
			Date createTime, String updateName, Date updateTime,
			String stationNames, Long bussDepart, String rightDepart) {
		this.id = id;
		this.userCode = userCode;
		this.loginName = loginName;
		this.userName = userName;
		this.password = password;
		this.birthdayType = birthdayType;
		this.birthday = birthday;
		this.workstatus = workstatus;
		this.hrstatus = hrstatus;
		this.manCode = manCode;
		this.offTel = offTel;
		this.telPhone = telPhone;
		this.sex = sex;
		this.stationId = stationId;
		this.stationIds = stationIds;
		this.duty = duty;
		this.status = status;
		this.userLevel = userLevel;
		this.departId = departId;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.stationNames = stationNames;
		this.bussDepart = bussDepart;
		this.rightDepart = rightDepart;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName="SEQ_SYS_USER ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "USER_CODE", length = 10)
	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(name = "LOGIN_NAME", length = 20)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "USER_NAME", length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "PASSWORD", length = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "BIRTHDAY_TYPE", precision = 22, scale = 0)
	public Long getBirthdayType() {
		return this.birthdayType;
	}

	public void setBirthdayType(Long birthdayType) {
		this.birthdayType = birthdayType;
	}

	@JSON(format="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY", length = 7)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "WORKSTATUS", precision = 22, scale = 0)
	public Long getWorkstatus() {
		return this.workstatus;
	}

	public void setWorkstatus(Long workstatus) {
		this.workstatus = workstatus;
	}

	@Column(name = "HRSTATUS", precision = 22, scale = 0)
	public Long getHrstatus() {
		return this.hrstatus;
	}

	public void setHrstatus(Long hrstatus) {
		this.hrstatus = hrstatus;
	}

	@Column(name = "MAN_CODE", length = 20)
	public String getManCode() {
		return this.manCode;
	}

	public void setManCode(String manCode) {
		this.manCode = manCode;
	}

	@Column(name = "OFF_TEL", length = 20)
	public String getOffTel() {
		return this.offTel;
	}

	public void setOffTel(String offTel) {
		this.offTel = offTel;
	}

	@Column(name = "TEL_PHONE", length = 20)
	public String getTelPhone() {
		return this.telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	@Column(name = "SEX", precision = 22, scale = 0)
	public Long getSex() {
		return this.sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	@Column(name = "STATION_ID", precision = 22, scale = 0)
	public Long getStationId() {
		return this.stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	@Column(name = "STATION_IDS", length = 100)
	public String getStationIds() {
		return this.stationIds;
	}

	public void setStationIds(String stationIds) {
		this.stationIds = stationIds;
	}

	@Column(name = "DUTY", length = 500)
	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "USER_LEVEL", precision = 22, scale = 0)
	public Long getUserLevel() {
		return this.userLevel;
	}

	public void setUserLevel(Long userLevel) {
		this.userLevel = userLevel;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
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
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "STATION_NAMES", length = 100)
	public String getStationNames() {
		return this.stationNames;
	}

	public void setStationNames(String stationNames) {
		this.stationNames = stationNames;
	}

	@Column(name = "BUSS_DEPART", precision = 22, scale = 0)
	public Long getBussDepart() {
		return this.bussDepart;
	}

	public void setBussDepart(Long bussDepart) {
		this.bussDepart = bussDepart;
	}

	@Column(name = "RIGHT_DEPART", length = 200)
	public String getRightDepart() {
		return this.rightDepart;
	}

	public void setRightDepart(String rightDepart) {
		this.rightDepart = rightDepart;
	}

	@Column(name = "TS", length = 20)
	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

}