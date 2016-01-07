package com.xbwl.flow.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * ���̹���  Ȩ�޹����ѯVO
 *@author LiuHao
 *@time Feb 21, 2012 10:59:43 AM
 */
public class FlowRalaruleVo {
	private Long id;
	private Long nodeId;//�ڵ�ID
	private Long wfoptType;//��������(����\֪��\�����������)
	private Long shareType;//��������(��Ա����\��λ����\�ض������µĸ�λ��ϵ\��ɫ����)
	private Long userobjType;//��Ա����(ָ����Ա�����̲������ֶΡ���������Դ�ֶ�)
	private Long stationobjType;//��λ����(ָ����λ�����̲������ֶΡ���������Դ�ֶΡ�����λ�ֶ�)
	private Long usershareType;//�ض������µĸ�λ��ϵ(ֱ���ϼ���λ\�����ϼ���λ)
	private Long roleobjType;//��ɫ����(ָ����ɫ)
	private Long userIds;//ָ����Աid
	private Long formfieldId;//���ֶ�id
	private Long roleobjId;//��ɫid 
	private Long wfoperatornodeId;//���̽ڵ�id 
	private Long stationId;//��λID
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String ts;
	
	private Long status;
	
	private String userName;//�û�����
	private String formName;//������
	private String formFieldName;//���ֶ�����
	private String nodeName;//�ڵ�����
	private String stationName;//��λ����
	private String roleName;//��ɫ����
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
	 * @return the nodeId
	 */
	public Long getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @return the wfoptType
	 */
	public Long getWfoptType() {
		return wfoptType;
	}
	/**
	 * @param wfoptType the wfoptType to set
	 */
	public void setWfoptType(Long wfoptType) {
		this.wfoptType = wfoptType;
	}
	/**
	 * @return the shareType
	 */
	public Long getShareType() {
		return shareType;
	}
	/**
	 * @param shareType the shareType to set
	 */
	public void setShareType(Long shareType) {
		this.shareType = shareType;
	}
	/**
	 * @return the userobjType
	 */
	public Long getUserobjType() {
		return userobjType;
	}
	/**
	 * @param userobjType the userobjType to set
	 */
	public void setUserobjType(Long userobjType) {
		this.userobjType = userobjType;
	}
	/**
	 * @return the stationobjType
	 */
	public Long getStationobjType() {
		return stationobjType;
	}
	/**
	 * @param stationobjType the stationobjType to set
	 */
	public void setStationobjType(Long stationobjType) {
		this.stationobjType = stationobjType;
	}
	/**
	 * @return the usershareType
	 */
	public Long getUsershareType() {
		return usershareType;
	}
	/**
	 * @param usershareType the usershareType to set
	 */
	public void setUsershareType(Long usershareType) {
		this.usershareType = usershareType;
	}
	/**
	 * @return the roleobjType
	 */
	public Long getRoleobjType() {
		return roleobjType;
	}
	/**
	 * @param roleobjType the roleobjType to set
	 */
	public void setRoleobjType(Long roleobjType) {
		this.roleobjType = roleobjType;
	}
	/**
	 * @return the userIds
	 */
	public Long getUserIds() {
		return userIds;
	}
	/**
	 * @param userIds the userIds to set
	 */
	public void setUserIds(Long userIds) {
		this.userIds = userIds;
	}
	/**
	 * @return the formfieldId
	 */
	public Long getFormfieldId() {
		return formfieldId;
	}
	/**
	 * @param formfieldId the formfieldId to set
	 */
	public void setFormfieldId(Long formfieldId) {
		this.formfieldId = formfieldId;
	}
	/**
	 * @return the roleobjId
	 */
	public Long getRoleobjId() {
		return roleobjId;
	}
	/**
	 * @param roleobjId the roleobjId to set
	 */
	public void setRoleobjId(Long roleobjId) {
		this.roleobjId = roleobjId;
	}
	/**
	 * @return the wfoperatornodeId
	 */
	public Long getWfoperatornodeId() {
		return wfoperatornodeId;
	}
	/**
	 * @param wfoperatornodeId the wfoperatornodeId to set
	 */
	public void setWfoperatornodeId(Long wfoperatornodeId) {
		this.wfoperatornodeId = wfoperatornodeId;
	}
	/**
	 * @return the stationId
	 */
	public Long getStationId() {
		return stationId;
	}
	/**
	 * @param stationId the stationId to set
	 */
	public void setStationId(Long stationId) {
		this.stationId = stationId;
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return the formFieldName
	 */
	public String getFormFieldName() {
		return formFieldName;
	}
	/**
	 * @param formFieldName the formFieldName to set
	 */
	public void setFormFieldName(String formFieldName) {
		this.formFieldName = formFieldName;
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
	 * @return the stationName
	 */
	public String getStationName() {
		return stationName;
	}
	/**
	 * @param stationName the stationName to set
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
