package com.xbwl.cus.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 *@author LiuHao
 *@time Nov 3, 2011 11:17:02 AM
 */
public class CusSaleChanceVo {
	private Long id;
	private Long targetNum;//指标值
	private Date startTime;//指标开始时间
	private Date endTime;//指标结束时间
	private Long completeNum;//实际完成值
	private Long completeRate;//指标完成率
	private Double timeUser;//指标完成时间使用率
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long cusRecordId;
	private Long status;
	
	private Long departId;
	private String cusName;
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
	 * @return the targetNum
	 */
	public Long getTargetNum() {
		return targetNum;
	}
	/**
	 * @param targetNum the targetNum to set
	 */
	public void setTargetNum(Long targetNum) {
		this.targetNum = targetNum;
	}
	/**
	 * @return the startTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the completeNum
	 */
	public Long getCompleteNum() {
		return completeNum;
	}
	/**
	 * @param completeNum the completeNum to set
	 */
	public void setCompleteNum(Long completeNum) {
		this.completeNum = completeNum;
	}
	/**
	 * @return the completeRate
	 */
	public Long getCompleteRate() {
		return completeRate;
	}
	/**
	 * @param completeRate the completeRate to set
	 */
	public void setCompleteRate(Long completeRate) {
		this.completeRate = completeRate;
	}
	/**
	 * @return the timeUser
	 */
	@JSON(format="yyyy-MM-dd")
	public Double getTimeUser() {
		return timeUser;
	}
	/**
	 * @param timeUser the timeUser to set
	 */
	public void setTimeUser(Double timeUser) {
		this.timeUser = timeUser;
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
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	@JSON(format="yyyy-MM-dd")
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
	
}
