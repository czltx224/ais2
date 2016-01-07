package com.xbwl.sys.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author CaoZhili
 * time Jun 29, 2011 10:29:44 AM
 */
public class BasDriverVo implements java.io.Serializable {

	private Long id;//1
	private String userCode;//工号2
	private String driverName;//姓名3
	private Long sex;//性别（0:女,1:男）4
	private Long driverAge;//驾龄5
	private String address;//住址6
	private String cityCode;//城市7
	private String postalCode;//邮编8
	private String phone;//电话9
	private String identityCard;//省份证号码10
	private Long departId;//部门11
	private String departName;
	private Date startDate;//雇佣日期12
	private Date stopDate;//解雇日期13
	private String stopFlag;//0:正常,1:解雇14
	private String createName;//创始人15
	private Date createTime;//创建日期16
	private String updateName;//最后更新人17
	private Date updateTime;//最后更新日期18
	private String ts;//时间戳19
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public Long getSex() {
		return sex;
	}
	public void setSex(Long sex) {
		this.sex = sex;
	}
	public Long getDriverAge() {
		return driverAge;
	}
	public void setDriverAge(Long driverAge) {
		this.driverAge = driverAge;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getStopDate() {
		return stopDate;
	}
	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}
	public String getStopFlag() {
		return stopFlag;
	}
	public void setStopFlag(String stopFlag) {
		this.stopFlag = stopFlag;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public BasDriverVo(Long id, String userCode, String driverName, Long sex,
			Long driverAge, String address, String cityCode, String postalCode,
			String phone, String identityCard, Long departId,
			String departName, Date startDate, Date stopDate, String stopFlag,
			String createName, Date createTime, String updateName,
			Date updateTime, String ts) {
		super();
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
		this.departName = departName;
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.stopFlag = stopFlag;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}
	public BasDriverVo(Long id) {
		super();
		this.id = id;
	}
	public BasDriverVo() {
		super();
	}
	
	
}
