package com.xbwl.flow.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 *@author LiuHao
 *@time Apr 19, 2012 5:17:16 PM
 */
public class FlowRalaGiveVo {
	private Long id;
    private Long pipeId;//管道ID
    private String pipeName;
    private Date startDate;//开始日期
    private Date endDate;//结束日期
    private String startTime;//开始时间
    private String endTime;//结束时间
    private Long giveId;//赋予人ID
    private Long userId;//授权人ID
    private String createName;//创建人
    private Date createTime;//创建时间
    private String updateName;
    private Date updateTime;
    private String ts;
    private String remark;
    private Long departId;
    private String departName;
    
    private String giveName;
    private String userName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPipeId() {
		return pipeId;
	}
	public void setPipeId(Long pipeId) {
		this.pipeId = pipeId;
	}
	public String getPipeName() {
		return pipeName;
	}
	public void setPipeName(String pipeName) {
		this.pipeName = pipeName;
	}
	@JSON(format = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JSON(format = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getGiveId() {
		return giveId;
	}
	public void setGiveId(Long giveId) {
		this.giveId = giveId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	@JSON(format = "yyyy-MM-dd")
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
	@JSON(format = "yyyy-MM-dd")
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getGiveName() {
		return giveName;
	}
	public void setGiveName(String giveName) {
		this.giveName = giveName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	} 
}
