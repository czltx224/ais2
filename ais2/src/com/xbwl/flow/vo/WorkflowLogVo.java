package com.xbwl.flow.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 流程日志查询VO
 *@author LiuHao
 *@time Mar 5, 2012 10:53:07 AM
 */
public class WorkflowLogVo {
	private Long id;
	private Long workflowId;//流程ID
	private Long nodeinfoId;//节点ID
	private Long logType;//审批类型(1通过\2否决\3退回)
	private Long returnNodeid;//退回节点ID
	private String remark;//审批备注
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String ts;
	
	private String nodeName;//节点名称
	private String returnNodeName;//退回节点名称

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
	 * @return the workflowId
	 */
	public Long getWorkflowId() {
		return workflowId;
	}

	/**
	 * @param workflowId the workflowId to set
	 */
	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	/**
	 * @return the nodeinfoId
	 */
	public Long getNodeinfoId() {
		return nodeinfoId;
	}

	/**
	 * @param nodeinfoId the nodeinfoId to set
	 */
	public void setNodeinfoId(Long nodeinfoId) {
		this.nodeinfoId = nodeinfoId;
	}

	/**
	 * @return the logType
	 */
	public Long getLogType() {
		return logType;
	}

	/**
	 * @param logType the logType to set
	 */
	public void setLogType(Long logType) {
		this.logType = logType;
	}

	/**
	 * @return the returnNodeid
	 */
	public Long getReturnNodeid() {
		return returnNodeid;
	}

	/**
	 * @param returnNodeid the returnNodeid to set
	 */
	public void setReturnNodeid(Long returnNodeid) {
		this.returnNodeid = returnNodeid;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * @return the updateTime
	 */
	@JSON(format="yyyy-MM-dd HH:mm:ss")
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
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the returnNodeName
	 */
	public String getReturnNodeName() {
		return returnNodeName;
	}

	/**
	 * @param returnNodeName the returnNodeName to set
	 */
	public void setReturnNodeName(String returnNodeName) {
		this.returnNodeName = returnNodeName;
	}
	
	
}
