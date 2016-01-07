package com.xbwl.entity;

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
 * FlowPipeinfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FLOW_PIPEINFO")
public class FlowPipeinfo implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String objName;//流程名称
	private Long objType;//流程类型
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

	// Constructors

	/** default constructor */
	public FlowPipeinfo() {
	}

	/** minimal constructor */
	public FlowPipeinfo(Long id) {
		this.id = id;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName="SEQ_FLOW_PIPEINFO")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OBJ_NAME")
	public String getObjName() {
		return this.objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	@Column(name = "OBJ_TYPE", precision = 10, scale = 0)
	public Long getObjType() {
		return this.objType;
	}

	public void setObjType(Long objType) {
		this.objType = objType;
	}

	@Column(name = "FORM_ID", precision = 10, scale = 0)
	public Long getFormId() {
		return this.formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	@Column(name = "IS_CANAUTOFLOW", precision = 1, scale = 0)
	public Long getIsCanautoflow() {
		return this.isCanautoflow;
	}

	public void setIsCanautoflow(Long isCanautoflow) {
		this.isCanautoflow = isCanautoflow;
	}

	@Column(name = "IS_RTX", precision = 1, scale = 0)
	public Long getIsRtx() {
		return this.isRtx;
	}

	public void setIsRtx(Long isRtx) {
		this.isRtx = isRtx;
	}

	@Column(name = "OBJ_DESC")
	public String getObjDesc() {
		return this.objDesc;
	}

	public void setObjDesc(String objDesc) {
		this.objDesc = objDesc;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "IS_DELETE", precision = 1, scale = 0)
	public Long getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Long isDelete) {
		this.isDelete = isDelete;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

}