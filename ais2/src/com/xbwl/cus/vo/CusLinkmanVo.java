package com.xbwl.cus.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 *@author LiuHao
 *@time Nov 3, 2011 10:59:41 AM
 */
public class CusLinkmanVo {
	private Long id;
	private String name;//联系人名称
	private String tel;//电话
	private Long sex;//性别
	private String duty;//所在企业职责
	private Date birthday;//生日
	private Long isPivot;//是否关键人物
	private String hobbiesInterests;//爱好
	private String linkman;//关联联系人
	private String relation;//关系
	private Date lastVisistTime;//最后沟通时间
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long cusRecordId;
	private Long status;
	private Long departId;
	private String cusName;//客服名称
	private String phone;//手机
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return the sex
	 */
	public Long getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(Long sex) {
		this.sex = sex;
	}
	/**
	 * @return the duty
	 */
	public String getDuty() {
		return duty;
	}
	/**
	 * @param duty the duty to set
	 */
	public void setDuty(String duty) {
		this.duty = duty;
	}
	/**
	 * @return the birthday
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the isPivot
	 */
	public Long getIsPivot() {
		return isPivot;
	}
	/**
	 * @param isPivot the isPivot to set
	 */
	public void setIsPivot(Long isPivot) {
		this.isPivot = isPivot;
	}
	/**
	 * @return the hobbiesInterests
	 */
	public String getHobbiesInterests() {
		return hobbiesInterests;
	}
	/**
	 * @param hobbiesInterests the hobbiesInterests to set
	 */
	public void setHobbiesInterests(String hobbiesInterests) {
		this.hobbiesInterests = hobbiesInterests;
	}
	/**
	 * @return the linkman
	 */
	public String getLinkman() {
		return linkman;
	}
	/**
	 * @param linkman the linkman to set
	 */
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	/**
	 * @return the relation
	 */
	public String getRelation() {
		return relation;
	}
	/**
	 * @param relation the relation to set
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}
	/**
	 * @return the lastVisistTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getLastVisistTime() {
		return lastVisistTime;
	}
	/**
	 * @param lastVisistTime the lastVisistTime to set
	 */
	public void setLastVisistTime(Date lastVisistTime) {
		this.lastVisistTime = lastVisistTime;
	}
	/**
	 * @return the createName
	 */
	public String getCreateName() {
		return createName;
	}
	/**
	 * @param createName the createName to set
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	/**
	 * @return the createTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the updateName
	 */
	public String getUpdateName() {
		return updateName;
	}
	/**
	 * @param updateName the updateName to set
	 */
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	/**
	 * @return the updateTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the ts
	 */
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
	 * @return the cusRecordId
	 */
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
	 * @return the status
	 */
	public Long getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}
	/**
	 * @return the departId
	 */
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
	 * @return the cusName
	 */
	public String getCusName() {
		return cusName;
	}
	/**
	 * @param cusName the cusName to set
	 */
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
