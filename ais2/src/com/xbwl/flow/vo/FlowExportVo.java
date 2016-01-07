package com.xbwl.flow.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 出口管理查询VO
 *@author LiuHao
 *@time Feb 20, 2012 11:27:29 AM
 */
public class FlowExportVo {
	private Long id;//
	private Long startnodeId;//前一节点ID
	private Long endnodeId;//后一节点id 
	private String condition;//出口条件
	private String conditionRemark;//出口条件备注
	private String linkName;//出口名称 
	private Long pipeId;//流程id
	private Long linkFrom;//开始连接点
	private Long linkTo;//结束连接点
	private Long x1;//图形坐标
	private Long x2;
	private Long x3;
	private Long x4;
	private Long x5;
	private Long y1;
	private Long y2;
	private Long y3;
	private Long y4;
	private Long y5;
	private String updateName;
	private Date updateTime;
	private String createName;
	private Long status;
	private Date createTime;
	private String ts;
	
	private String startnodeName;//前一节点名称
	private String endnodeName;//后一节点名称
	private String pipeName;//流程名称
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
	 * @return the startnodeId
	 */
	public Long getStartnodeId() {
		return startnodeId;
	}
	/**
	 * @param startnodeId the startnodeId to set
	 */
	public void setStartnodeId(Long startnodeId) {
		this.startnodeId = startnodeId;
	}
	/**
	 * @return the endnodeId
	 */
	public Long getEndnodeId() {
		return endnodeId;
	}
	/**
	 * @param endnodeId the endnodeId to set
	 */
	public void setEndnodeId(Long endnodeId) {
		this.endnodeId = endnodeId;
	}
	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}
	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}
	/**
	 * @return the linkName
	 */
	public String getLinkName() {
		return linkName;
	}
	/**
	 * @param linkName the linkName to set
	 */
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	/**
	 * @return the pipeId
	 */
	public Long getPipeId() {
		return pipeId;
	}
	/**
	 * @param pipeId the pipeId to set
	 */
	public void setPipeId(Long pipeId) {
		this.pipeId = pipeId;
	}
	/**
	 * @return the linkFrom
	 */
	public Long getLinkFrom() {
		return linkFrom;
	}
	/**
	 * @param linkFrom the linkFrom to set
	 */
	public void setLinkFrom(Long linkFrom) {
		this.linkFrom = linkFrom;
	}
	/**
	 * @return the linkTo
	 */
	public Long getLinkTo() {
		return linkTo;
	}
	/**
	 * @param linkTo the linkTo to set
	 */
	public void setLinkTo(Long linkTo) {
		this.linkTo = linkTo;
	}
	/**
	 * @return the x1
	 */
	public Long getX1() {
		return x1;
	}
	/**
	 * @param x1 the x1 to set
	 */
	public void setX1(Long x1) {
		this.x1 = x1;
	}
	/**
	 * @return the x2
	 */
	public Long getX2() {
		return x2;
	}
	/**
	 * @param x2 the x2 to set
	 */
	public void setX2(Long x2) {
		this.x2 = x2;
	}
	/**
	 * @return the x3
	 */
	public Long getX3() {
		return x3;
	}
	/**
	 * @param x3 the x3 to set
	 */
	public void setX3(Long x3) {
		this.x3 = x3;
	}
	/**
	 * @return the x4
	 */
	public Long getX4() {
		return x4;
	}
	/**
	 * @param x4 the x4 to set
	 */
	public void setX4(Long x4) {
		this.x4 = x4;
	}
	/**
	 * @return the x5
	 */
	public Long getX5() {
		return x5;
	}
	/**
	 * @param x5 the x5 to set
	 */
	public void setX5(Long x5) {
		this.x5 = x5;
	}
	/**
	 * @return the y1
	 */
	public Long getY1() {
		return y1;
	}
	/**
	 * @param y1 the y1 to set
	 */
	public void setY1(Long y1) {
		this.y1 = y1;
	}
	/**
	 * @return the y2
	 */
	public Long getY2() {
		return y2;
	}
	/**
	 * @param y2 the y2 to set
	 */
	public void setY2(Long y2) {
		this.y2 = y2;
	}
	/**
	 * @return the y3
	 */
	public Long getY3() {
		return y3;
	}
	/**
	 * @param y3 the y3 to set
	 */
	public void setY3(Long y3) {
		this.y3 = y3;
	}
	/**
	 * @return the y4
	 */
	public Long getY4() {
		return y4;
	}
	/**
	 * @param y4 the y4 to set
	 */
	public void setY4(Long y4) {
		this.y4 = y4;
	}
	/**
	 * @return the y5
	 */
	public Long getY5() {
		return y5;
	}
	/**
	 * @param y5 the y5 to set
	 */
	public void setY5(Long y5) {
		this.y5 = y5;
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
	 * @return the startnodeName
	 */
	public String getStartnodeName() {
		return startnodeName;
	}
	/**
	 * @param startnodeName the startnodeName to set
	 */
	public void setStartnodeName(String startnodeName) {
		this.startnodeName = startnodeName;
	}
	/**
	 * @return the endnodeName
	 */
	public String getEndnodeName() {
		return endnodeName;
	}
	/**
	 * @param endnodeName the endnodeName to set
	 */
	public void setEndnodeName(String endnodeName) {
		this.endnodeName = endnodeName;
	}
	/**
	 * @return the pipeName
	 */
	public String getPipeName() {
		return pipeName;
	}
	/**
	 * @param pipeName the pipeName to set
	 */
	public void setPipeName(String pipeName) {
		this.pipeName = pipeName;
	}
	/**
	 * @return the conditionRemark
	 */
	public String getConditionRemark() {
		return conditionRemark;
	}
	/**
	 * @param conditionRemark the conditionRemark to set
	 */
	public void setConditionRemark(String conditionRemark) {
		this.conditionRemark = conditionRemark;
	}
	
	
	
}
