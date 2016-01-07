package com.xbwl.cus.vo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * CusCurrival entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class CusCurrivalVo implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String curName;//竞争对手名称
	private Long cusId;//客商编号
	private String cusName;//客商名称
	private String choiceReason;//选择对手原因
	private String curGoodness;//竞争对手优势
	private String curBadness;//竞争对手劣势
	private Date teamworkTime;//合作时间
	private Double teamworkWeight;//合作货量
	private String keyLinkman;//关键联系人
	private String curProjiect;//提供方案
	private Long departId;//部门编号
	private String createName;//创建人
	private Date createTime;
	private Date updateTime;
	private String updateName;
	private String ts;
	
	private Long cusRecordId;
	private Long status;
	private String cpName;//客商名称
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
	 * @return the curName
	 */
	public String getCurName() {
		return curName;
	}
	/**
	 * @param curName the curName to set
	 */
	public void setCurName(String curName) {
		this.curName = curName;
	}
	/**
	 * @return the cusId
	 */
	public Long getCusId() {
		return cusId;
	}
	/**
	 * @param cusId the cusId to set
	 */
	public void setCusId(Long cusId) {
		this.cusId = cusId;
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
	/**
	 * @return the choiceReason
	 */
	public String getChoiceReason() {
		return choiceReason;
	}
	/**
	 * @param choiceReason the choiceReason to set
	 */
	public void setChoiceReason(String choiceReason) {
		this.choiceReason = choiceReason;
	}
	/**
	 * @return the curGoodness
	 */
	public String getCurGoodness() {
		return curGoodness;
	}
	/**
	 * @param curGoodness the curGoodness to set
	 */
	public void setCurGoodness(String curGoodness) {
		this.curGoodness = curGoodness;
	}
	/**
	 * @return the curBadness
	 */
	public String getCurBadness() {
		return curBadness;
	}
	/**
	 * @param curBadness the curBadness to set
	 */
	public void setCurBadness(String curBadness) {
		this.curBadness = curBadness;
	}
	/**
	 * @return the teamworkTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getTeamworkTime() {
		return teamworkTime;
	}
	/**
	 * @param teamworkTime the teamworkTime to set
	 */
	public void setTeamworkTime(Date teamworkTime) {
		this.teamworkTime = teamworkTime;
	}
	/**
	 * @return the teamworkWeight
	 */
	public Double getTeamworkWeight() {
		return teamworkWeight;
	}
	/**
	 * @param teamworkWeight the teamworkWeight to set
	 */
	public void setTeamworkWeight(Double teamworkWeight) {
		this.teamworkWeight = teamworkWeight;
	}
	/**
	 * @return the keyLinkman
	 */
	public String getKeyLinkman() {
		return keyLinkman;
	}
	/**
	 * @param keyLinkman the keyLinkman to set
	 */
	public void setKeyLinkman(String keyLinkman) {
		this.keyLinkman = keyLinkman;
	}
	/**
	 * @return the curProjiect
	 */
	public String getCurProjiect() {
		return curProjiect;
	}
	/**
	 * @param curProjiect the curProjiect to set
	 */
	public void setCurProjiect(String curProjiect) {
		this.curProjiect = curProjiect;
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
	 * @return the cpName
	 */
	public String getCpName() {
		return cpName;
	}
	/**
	 * @param cpName the cpName to set
	 */
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	
}