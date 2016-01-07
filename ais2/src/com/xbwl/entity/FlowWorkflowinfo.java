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
 * FlowWorkflowinfo entity.
 * 流程流转信息
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FLOW_WORKFLOWINFO")
public class FlowWorkflowinfo implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long workflowId;//流程ID
	private Long laststepId;//上个步骤id
	private Long curstepId;//当前步骤id
	private Long isReceived;//是否接收(0未接收,1已接收)
	private Long isSubmited;//是否提交(0未提交,1已提交)
	private Long isPaused;//是否中止(0未中止,1已中止)
	private Long isAudit;//是否审核
	private Date createTime;
	private String createName;
	private String updateName;
	private Date updateTime;
	private String ts;

	// Constructors

	/** default constructor */
	public FlowWorkflowinfo() {
	}

	/** minimal constructor */
	public FlowWorkflowinfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public FlowWorkflowinfo(Long id, Long workflowId, Long laststepId,
			Long curstepId, Long isReceived, Long isSubmited, Long isPaused,
			Date createTime, String createName, String updateName,
			Date updateTime, String ts) {
		this.id = id;
		this.workflowId = workflowId;
		this.laststepId = laststepId;
		this.curstepId = curstepId;
		this.isReceived = isReceived;
		this.isSubmited = isSubmited;
		this.isPaused = isPaused;
		this.createTime = createTime;
		this.createName = createName;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName="SEQ_FLOW_WORKFLOWINFO")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "WORKFLOW_ID", precision = 10, scale = 0)
	public Long getWorkflowId() {
		return this.workflowId;
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	@Column(name = "LASTSTEP_ID", precision = 10, scale = 0)
	public Long getLaststepId() {
		return this.laststepId;
	}

	public void setLaststepId(Long laststepId) {
		this.laststepId = laststepId;
	}

	@Column(name = "CURSTEP_ID", precision = 10, scale = 0)
	public Long getCurstepId() {
		return this.curstepId;
	}

	public void setCurstepId(Long curstepId) {
		this.curstepId = curstepId;
	}

	@Column(name = "IS_RECEIVED", precision = 1, scale = 0)
	public Long getIsReceived() {
		return this.isReceived;
	}

	public void setIsReceived(Long isReceived) {
		this.isReceived = isReceived;
	}

	@Column(name = "IS_SUBMITED", precision = 1, scale = 0)
	public Long getIsSubmited() {
		return this.isSubmited;
	}

	public void setIsSubmited(Long isSubmited) {
		this.isSubmited = isSubmited;
	}

	@Column(name = "IS_PAUSED", precision = 1, scale = 0)
	public Long getIsPaused() {
		return this.isPaused;
	}

	public void setIsPaused(Long isPaused) {
		this.isPaused = isPaused;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
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

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	@Column(name = "IS_AUDIT")
	public Long getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Long isAudit) {
		this.isAudit = isAudit;
	}
	
}