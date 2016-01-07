package com.xbwl.flow.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * �ڵ�����ѯVO
 *@author LiuHao
 *@time Feb 17, 2012 4:37:28 PM
 */
public class FlowNodeinfoVo {
	private Long id;
	private String objName;//�ڵ�����
	private Long pipeId;//����ID
	private String nodeType;//�ڵ�����
	private Long isReject;//�Ƿ������˻�
	private Long rejectnodeId;//�˻ؽڵ�
	private String perPage;//�ڵ�Ԥ����ҳ��
	private String afterPage;//�ڵ����ҳ��
	private Long isRtx;//�Ƿ�rtx����(0������\1����)
	private String subBtnName;//�ύ��ť����
	private String saveBtnName;//���水ť����
	private Long isAutoflow;//�Ƿ��Զ���ת(0���Զ���ת\1�Զ���ת)
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	
	private Long status;
	private String pipeName;//��������
	private String rejectnodeName;//�˻ؽڵ�����
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
	 * @return the nodeType
	 */
	public String getNodeType() {
		return nodeType;
	}
	/**
	 * @param nodeType the nodeType to set
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	/**
	 * @return the isReject
	 */
	public Long getIsReject() {
		return isReject;
	}
	/**
	 * @param isReject the isReject to set
	 */
	public void setIsReject(Long isReject) {
		this.isReject = isReject;
	}
	/**
	 * @return the rejectnodeId
	 */
	public Long getRejectnodeId() {
		return rejectnodeId;
	}
	/**
	 * @param rejectnodeId the rejectnodeId to set
	 */
	public void setRejectnodeId(Long rejectnodeId) {
		this.rejectnodeId = rejectnodeId;
	}
	/**
	 * @return the perPage
	 */
	public String getPerPage() {
		return perPage;
	}
	/**
	 * @param perPage the perPage to set
	 */
	public void setPerPage(String perPage) {
		this.perPage = perPage;
	}
	/**
	 * @return the afterPage
	 */
	public String getAfterPage() {
		return afterPage;
	}
	/**
	 * @param afterPage the afterPage to set
	 */
	public void setAfterPage(String afterPage) {
		this.afterPage = afterPage;
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
	 * @return the subBtnName
	 */
	public String getSubBtnName() {
		return subBtnName;
	}
	/**
	 * @param subBtnName the subBtnName to set
	 */
	public void setSubBtnName(String subBtnName) {
		this.subBtnName = subBtnName;
	}
	/**
	 * @return the saveBtnName
	 */
	public String getSaveBtnName() {
		return saveBtnName;
	}
	/**
	 * @param saveBtnName the saveBtnName to set
	 */
	public void setSaveBtnName(String saveBtnName) {
		this.saveBtnName = saveBtnName;
	}
	/**
	 * @return the isAutoflow
	 */
	public Long getIsAutoflow() {
		return isAutoflow;
	}
	/**
	 * @param isAutoflow the isAutoflow to set
	 */
	public void setIsAutoflow(Long isAutoflow) {
		this.isAutoflow = isAutoflow;
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
	 * @return the rejectnodeName
	 */
	public String getRejectnodeName() {
		return rejectnodeName;
	}
	/**
	 * @param rejectnodeName the rejectnodeName to set
	 */
	public void setRejectnodeName(String rejectnodeName) {
		this.rejectnodeName = rejectnodeName;
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
	
	
	
}
