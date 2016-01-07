package com.xbwl.oper.fax.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 *@author LiuHao
 *@time Dec 14, 2011 2:36:19 PM
 */
public class OprChangeVo {
	
	private Long changeNo;//更改单号
	private Long isSystem;//是否为系统默认
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String remark;//备注
	private Long status;//状态 0，删除，1，未审核，2，已审核，3，审核不通过，4，已知会
	private Long dno;//配送单号
	private String changeField;//更改属性名称
	private String changeFieldZh;//更改属性中文名称
	private String changePre;//更改前的值
	private String changePost;//更改后的值
	private Long departId;
	private String departName;
	private Long changeDetailId;//更改明细单号
	private Long faxStatus;//传真状态 1.正常 0.删除
	
	
	/**
	 * @return the changeNo
	 */
	public Long getChangeNo() {
		return changeNo;
	}
	/**
	 * @param changeNo the changeNo to set
	 */
	public void setChangeNo(Long changeNo) {
		this.changeNo = changeNo;
	}
	/**
	 * @return the isSystem
	 */
	public Long getIsSystem() {
		return isSystem;
	}
	/**
	 * @param isSystem the isSystem to set
	 */
	public void setIsSystem(Long isSystem) {
		this.isSystem = isSystem;
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
	 * @return the dno
	 */
	public Long getDno() {
		return dno;
	}
	/**
	 * @param dno the dno to set
	 */
	public void setDno(Long dno) {
		this.dno = dno;
	}
	/**
	 * @return the changeField
	 */
	public String getChangeField() {
		return changeField;
	}
	/**
	 * @param changeField the changeField to set
	 */
	public void setChangeField(String changeField) {
		this.changeField = changeField;
	}
	/**
	 * @return the changeFieldZh
	 */
	public String getChangeFieldZh() {
		return changeFieldZh;
	}
	/**
	 * @param changeFieldZh the changeFieldZh to set
	 */
	public void setChangeFieldZh(String changeFieldZh) {
		this.changeFieldZh = changeFieldZh;
	}
	/**
	 * @return the changePre
	 */
	public String getChangePre() {
		return changePre;
	}
	/**
	 * @param changePre the changePre to set
	 */
	public void setChangePre(String changePre) {
		this.changePre = changePre;
	}
	/**
	 * @return the changePost
	 */
	public String getChangePost() {
		return changePost;
	}
	/**
	 * @param changePost the changePost to set
	 */
	public void setChangePost(String changePost) {
		this.changePost = changePost;
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
	 * @return the departName
	 */
	public String getDepartName() {
		return departName;
	}
	/**
	 * @param departName the departName to set
	 */
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public Long getChangeDetailId() {
		return changeDetailId;
	}
	public void setChangeDetailId(Long changeDetailId) {
		this.changeDetailId = changeDetailId;
	}
	/**
	 * @return the faxStatus
	 */
	public Long getFaxStatus() {
		return faxStatus;
	}
	/**
	 * @param faxStatus the faxStatus to set
	 */
	public void setFaxStatus(Long faxStatus) {
		this.faxStatus = faxStatus;
	}
	
}
