package com.xbwl.flow.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * ������VO
 *@author LiuHao
 *@time Feb 16, 2012 10:25:45 AM
 */
public class FlowFormlinkVo {
	private Long id;
	private Long pid;//��ID(�����ID)
	private Long oid;//����ID(ʵ�ʱ�ID)
	private Long status; //	״̬(0:����,1:����)
	private Date createTime;//����ʱ��
	private String createName;//������
	private Date updateTime;//�޸�ʱ��
	private String updateName;//�޸���
	private String ts;
	
	private String pName;//�������
	private String oName;//ʵ�ʱ���
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
	 * @return the pid
	 */
	public Long getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(Long pid) {
		this.pid = pid;
	}
	/**
	 * @return the oid
	 */
	public Long getOid() {
		return oid;
	}
	/**
	 * @param oid the oid to set
	 */
	public void setOid(Long oid) {
		this.oid = oid;
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
	 * @return the pName
	 */
	public String getPName() {
		return pName;
	}
	/**
	 * @param name the pName to set
	 */
	public void setPName(String name) {
		pName = name;
	}
	/**
	 * @return the oName
	 */
	public String getOName() {
		return oName;
	}
	/**
	 * @param name the oName to set
	 */
	public void setOName(String name) {
		oName = name;
	}
	
	
}
