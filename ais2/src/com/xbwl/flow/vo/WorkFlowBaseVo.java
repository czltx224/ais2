package com.xbwl.flow.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 *@author LiuHao
 *@time Mar 5, 2012 9:18:48 AM
 */
public class WorkFlowBaseVo {
	private Long id;
	private Long formId;//表单Id
	private Long pipeId;//流程ID
	private String workflowName;//流程名称
	private Long workflowLevel;//流程等级(2紧急\1一般)
	private Long createType;//创建类型(1自动创建\2手工创建)
	private Long createrId;//创建人ID
	private Long isFinished;//流程状态(1正常\2归档\3否决)
	private Long isDelete;//是否删除(0删除\1正常)
	private Long curnodeids;//当前流程节点
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String ts;
	
	private String nodeName;//节点名称

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
	 * @return the formId
	 */
	public Long getFormId() {
		return formId;
	}

	/**
	 * @param formId the formId to set
	 */
	public void setFormId(Long formId) {
		this.formId = formId;
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
	 * @return the workflowName
	 */
	public String getWorkflowName() {
		return workflowName;
	}

	/**
	 * @param workflowName the workflowName to set
	 */
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	/**
	 * @return the workflowLevel
	 */
	public Long getWorkflowLevel() {
		return workflowLevel;
	}

	/**
	 * @param workflowLevel the workflowLevel to set
	 */
	public void setWorkflowLevel(Long workflowLevel) {
		this.workflowLevel = workflowLevel;
	}

	/**
	 * @return the createType
	 */
	public Long getCreateType() {
		return createType;
	}

	/**
	 * @param createType the createType to set
	 */
	public void setCreateType(Long createType) {
		this.createType = createType;
	}

	/**
	 * @return the createrId
	 */
	public Long getCreaterId() {
		return createrId;
	}

	/**
	 * @param createrId the createrId to set
	 */
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	/**
	 * @return the isFinished
	 */
	public Long getIsFinished() {
		return isFinished;
	}

	/**
	 * @param isFinished the isFinished to set
	 */
	public void setIsFinished(Long isFinished) {
		this.isFinished = isFinished;
	}

	/**
	 * @return the isDelete
	 */
	public Long getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(Long isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the curnodeids
	 */
	public Long getCurnodeids() {
		return curnodeids;
	}

	/**
	 * @param curnodeids the curnodeids to set
	 */
	public void setCurnodeids(Long curnodeids) {
		this.curnodeids = curnodeids;
	}

	/**
	 * @return the createTime
	 */
	@JSON(format="yyyy-MM-dd HH:mm:ss")
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
	
	
}
