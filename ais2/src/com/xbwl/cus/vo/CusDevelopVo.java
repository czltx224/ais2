package com.xbwl.cus.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 *@author LiuHao
 *@time Nov 3, 2011 10:17:49 AM
 */
public class CusDevelopVo {
	private Long id;
	private String developName;//过程名称
	private String developType;//过程类型
	private String developStage;//过程阶段
	private String developContext;//开发经过
	private Long developCost;//开发成本
	private String filePath;//附件
	private String developedMan;//活动对象
	private String developMan;//开发人
	private Date developTime;//开发时间
	private Long isAudit;//是否审核
	private Date auditTime;//审核时间
	private String auditName;//审核人
	private String remark;//备注
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long cusRecordId;//联系人ID
	private Long status;
	
	private Long departId;
	private String developLinkmanTel;//活动联系人电话
	private Long assessResult;//评估结果 1-5，分别为：非常不满意、不满意、一般、满意、非常满意
	private String resultRemark;//结果说明
	private String cusName;//客户名称
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
	 * @return the developName
	 */
	public String getDevelopName() {
		return developName;
	}
	/**
	 * @param developName the developName to set
	 */
	public void setDevelopName(String developName) {
		this.developName = developName;
	}
	/**
	 * @return the developType
	 */
	public String getDevelopType() {
		return developType;
	}
	/**
	 * @param developType the developType to set
	 */
	public void setDevelopType(String developType) {
		this.developType = developType;
	}
	/**
	 * @return the developStage
	 */
	public String getDevelopStage() {
		return developStage;
	}
	/**
	 * @param developStage the developStage to set
	 */
	public void setDevelopStage(String developStage) {
		this.developStage = developStage;
	}
	/**
	 * @return the developContext
	 */
	public String getDevelopContext() {
		return developContext;
	}
	/**
	 * @param developContext the developContext to set
	 */
	public void setDevelopContext(String developContext) {
		this.developContext = developContext;
	}
	/**
	 * @return the developCost
	 */
	public Long getDevelopCost() {
		return developCost;
	}
	/**
	 * @param developCost the developCost to set
	 */
	public void setDevelopCost(Long developCost) {
		this.developCost = developCost;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the developedMan
	 */
	public String getDevelopedMan() {
		return developedMan;
	}
	/**
	 * @param developedMan the developedMan to set
	 */
	public void setDevelopedMan(String developedMan) {
		this.developedMan = developedMan;
	}
	/**
	 * @return the developMan
	 */
	public String getDevelopMan() {
		return developMan;
	}
	/**
	 * @param developMan the developMan to set
	 */
	public void setDevelopMan(String developMan) {
		this.developMan = developMan;
	}
	/**
	 * @return the developTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getDevelopTime() {
		return developTime;
	}
	/**
	 * @param developTime the developTime to set
	 */
	public void setDevelopTime(Date developTime) {
		this.developTime = developTime;
	}
	/**
	 * @return the isAudit
	 */
	public Long getIsAudit() {
		return isAudit;
	}
	/**
	 * @param isAudit the isAudit to set
	 */
	public void setIsAudit(Long isAudit) {
		this.isAudit = isAudit;
	}
	/**
	 * @return the auditTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getAuditTime() {
		return auditTime;
	}
	/**
	 * @param auditTime the auditTime to set
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	/**
	 * @return the auditName
	 */
	public String getAuditName() {
		return auditName;
	}
	/**
	 * @param auditName the auditName to set
	 */
	public void setAuditName(String auditName) {
		this.auditName = auditName;
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
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	@JSON(format="yyyy-MM-dd")
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
	 * @return the developLinkmanTel
	 */
	public String getDevelopLinkmanTel() {
		return developLinkmanTel;
	}
	/**
	 * @param developLinkmanTel the developLinkmanTel to set
	 */
	public void setDevelopLinkmanTel(String developLinkmanTel) {
		this.developLinkmanTel = developLinkmanTel;
	}
	/**
	 * @return the assessResult
	 */
	public Long getAssessResult() {
		return assessResult;
	}
	/**
	 * @param assessResult the assessResult to set
	 */
	public void setAssessResult(Long assessResult) {
		this.assessResult = assessResult;
	}
	/**
	 * @return the resultRemark
	 */
	public String getResultRemark() {
		return resultRemark;
	}
	/**
	 * @param resultRemark the resultRemark to set
	 */
	public void setResultRemark(String resultRemark) {
		this.resultRemark = resultRemark;
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
