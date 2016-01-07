package com.xbwl.rbac.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;


public class SysUserVo  implements java.io.Serializable {
	
	private Long id;
	private Long departId;
	private String departName;
	private Long stationId;
	private String stationName;
	private String userCode;
	private String loginName;
	private String userName;
	private String password;
	private Long birthdayType;
	private Date birthday;
	private Long workstatus;
	private Long hrstatus;
	private String manCode;
	private String offTel;
	private String telPhone;
	private Long sex;
	private String stationIds;
	private String duty;
	private String status="0";
	private Long userLevel;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private Long bussDepart;
	private String bussDepartName;
	private String stationNames;
	private String rightDepart;
	private String ts;
	

	public String getRightDepart(){
		return rightDepart;
	}
	
	public void setRightDepart(String s){
		this.rightDepart=s;
	}
	
	public String getBussDepartName(){
		return bussDepartName;
	}
	
	public void setBussDepartName(String s){
		this.bussDepartName=s;
	}
	
	
	public String getStationNames(){
		return stationNames;
	}
	
	public void setStationNames(String s){
		this.stationNames=s;
	}
	
	
	public Long  getBussDepart(){
		return bussDepart;
	}
	
	public void setBussDepart(Long s){
		this.bussDepart=s;
	}
	
	
	public SysUserVo(){
	}
	
	public SysUserVo(Long id, Long departId,Long  stationId,String departName,String stationName,
			String userCode, String loginName, String userName,
			String password, Long birthdayType, Date birthday, Long workstatus,
			Long hrstatus, String manCode, String offTel, String telPhone,
			Long sex, String stationIds, String duty, String status,
			Long userLevel, String createName, Date createTime,
			String updateName, Date updateTime,Long bussDepart,String rightDepart,String stationNames) {
		this.bussDepart=bussDepart;
		this.rightDepart=rightDepart;
		this.stationNames=stationNames;
		this.id = id;
		this.departId = departId;
		this.departName=departName;
		this.stationId=stationId;
		this.stationName = stationName;
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
		this.stationIds = stationIds;
		this.duty = duty;
		this.status = status;
		this.userLevel = userLevel;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
	}
	public Long getDepartId(){
		return this.departId;
	}
	public String getDepartName(){
		return this.departName;
	}
	
	public void setDepartName(String departId){
		this.departName=departId;
	}
	public Long getStationId(){
		return this.stationId;
	}
	
	public void setStationId(Long s){
		  this.stationId=s;
	}
	public String getStationName(){
		return this.stationName;
	}
	public void setStationName(String s){
		this.stationName=s;
	}
	
	public void setDepartId(Long departId){
		this.departId=departId;
	}
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getBirthdayType() {
		return this.birthdayType;
	}

	public void setBirthdayType(Long birthdayType) {
		this.birthdayType = birthdayType;
	}

	@JSON(format="yyyy-MM-dd")
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Long getWorkstatus() {
		return this.workstatus;
	}

	public void setWorkstatus(Long workstatus) {
		this.workstatus = workstatus;
	}

	public Long getHrstatus() {
		return this.hrstatus;
	}

	public void setHrstatus(Long hrstatus) {
		this.hrstatus = hrstatus;
	}

	public String getManCode() {
		return this.manCode;
	}

	public void setManCode(String manCode) {
		this.manCode = manCode;
	}

	public String getOffTel() {
		return this.offTel;
	}

	public void setOffTel(String offTel) {
		this.offTel = offTel;
	}

	public String getTelPhone() {
		return this.telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Long getSex() {
		return this.sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public String getStationIds() {
		return this.stationIds;
	}

	public void setStationIds(String stationIds) {
		this.stationIds = stationIds;
	}

	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUserLevel() {
		return this.userLevel;
	}

	public void setUserLevel(Long userLevel) {
		this.userLevel = userLevel;
	}

	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd")
	public Date getUpdateTime() {
		return this.updateTime;
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

	
	
	

}
