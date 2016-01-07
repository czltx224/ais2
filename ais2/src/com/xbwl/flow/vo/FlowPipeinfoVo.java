package com.xbwl.flow.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 *@author LiuHao
 *@time Feb 17, 2012 9:18:59 AM
 */
public class FlowPipeinfoVo {
	private Long id;
	private String objName;//流程名称
	private Long objType;//流程类型ID
	private Long formId;//流程表单
	private Long isCanautoflow;//是否能自动流转
	private Long isRtx;//是否RTX提醒
	private String objDesc;//流程备注
	private Long status;//状态
	private Long isDelete;//是否删除
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String ts;
	
	private String formName;//表单名称
	
	private String dicObjType;//流程类型名称

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
	 * @return the objName
	 */
	public String getObjName() {
		return objName;
	}

	/**
	 * @param objName the objName to set
	 */
	public void setObjName(String objName) {
		this.objName = objName;
	}

	/**
	 * @return the objType
	 */
	public Long getObjType() {
		return objType;
	}

	/**
	 * @param objType the objType to set
	 */
	public void setObjType(Long objType) {
		this.objType = objType;
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
	 * @return the isCanautoflow
	 */
	public Long getIsCanautoflow() {
		return isCanautoflow;
	}

	/**
	 * @param isCanautoflow the isCanautoflow to set
	 */
	public void setIsCanautoflow(Long isCanautoflow) {
		this.isCanautoflow = isCanautoflow;
	}

	/**
	 * @return the isRtx
	 */
	public Long getIsRtx() {
		return isRtx;
	}

	/**
	 * @param isRtx the isRtx to set
	 */
	public void setIsRtx(Long isRtx) {
		this.isRtx = isRtx;
	}

	/**
	 * @return the objDesc
	 */
	public String getObjDesc() {
		return objDesc;
	}

	/**
	 * @param objDesc the objDesc to set
	 */
	public void setObjDesc(String objDesc) {
		this.objDesc = objDesc;
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
	 * @return the formName
	 */
	public String getFormName() {
		return formName;
	}

	/**
	 * @param formName the formName to set
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}

	/**
	 * @return the dicObjType
	 */
	public String getDicObjType() {
		return dicObjType;
	}

	/**
	 * @param dicObjType the dicObjType to set
	 */
	public void setDicObjType(String dicObjType) {
		this.dicObjType = dicObjType;
	}
	
	
}
