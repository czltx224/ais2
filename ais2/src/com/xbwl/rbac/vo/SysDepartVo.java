package com.xbwl.rbac.vo;
// default package

import java.util.Date;

/**
 * SysDepart entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class SysDepartVo implements java.io.Serializable {

	// Fields

	private Long departId;
	private String departName;
	private String departNo;
	private Long leadStation;
	private String staName;
	private String createName;
	private Date createTime;
	private String updateName;
	private String ts;
	private Date updateTime;
	private Long isBussinessDepa;
	private String addr;
	private String telephone;
	private String owntakeType;//自提类型
	//上级部门名称
	private String parentName;
	//上级部门ID
	private Long parentId;
	private Long isCusDepart;
	
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
	public Long getLeadStation() {
		return leadStation;
	}
	public void setLeadStation(Long leadStation) {
		this.leadStation = leadStation;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getIsBussinessDepa() {
		return isBussinessDepa;
	}
	public void setIsBussinessDepa(Long isBussinessDepa) {
		this.isBussinessDepa = isBussinessDepa;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getStaName() {
		return staName;
	}
	public void setStaName(String staName) {
		this.staName = staName;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	/**
	 * @return the departNo
	 */
	public String getDepartNo() {
		return departNo;
	}
	/**
	 * @param departNo the departNo to set
	 */
	public void setDepartNo(String departNo) {
		this.departNo = departNo;
	}
	/**
	 * @return the owntakeType
	 */
	public String getOwntakeType() {
		return owntakeType;
	}
	/**
	 * @param owntakeType the owntakeType to set
	 */
	public void setOwntakeType(String owntakeType) {
		this.owntakeType = owntakeType;
	}
	/**
	 * @return the isCusDepart
	 */
	public Long getIsCusDepart() {
		return isCusDepart;
	}
	/**
	 * @param isCusDepart the isCusDepart to set
	 */
	public void setIsCusDepart(Long isCusDepart) {
		this.isCusDepart = isCusDepart;
	}
	
}