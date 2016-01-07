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
 * FlowWorkflowoperators entity.
 * 流程操作者
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FLOW_WORKFLOWOPERATORS")
public class FlowWorkflowoperators implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long workflowId;//流程id
	private Long stepId;//步骤id
	private Long ruleId;//规则id
	private Long userId;//操作者id
	private Long operateType;//操作类型：1审批、2知会\3批注
	private Date createTime;
	private String createName;
	private String updateName;
	private Date updateTime;
	private String ts;

	// Constructors

	/** default constructor */
	public FlowWorkflowoperators() {
	}

	/** minimal constructor */
	public FlowWorkflowoperators(Long id) {
		this.id = id;
	}

	/** full constructor */
	public FlowWorkflowoperators(Long id, Long workflowId, Long stepId,
			Long ruleId, Long userId, Long operateType, Date createTime,
			String createName, String updateName, Date updateTime, String ts) {
		this.id = id;
		this.workflowId = workflowId;
		this.stepId = stepId;
		this.ruleId = ruleId;
		this.userId = userId;
		this.operateType = operateType;
		this.createTime = createTime;
		this.createName = createName;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName="SEQ_FLOW_WORKFLOWOPERATORS")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "WORKFLOW_ID", precision = 22, scale = 0)
	public Long getWorkflowId() {
		return this.workflowId;
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	@Column(name = "STEP_ID", precision = 22, scale = 0)
	public Long getStepId() {
		return this.stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	@Column(name = "RULE_ID", precision = 22, scale = 0)
	public Long getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	@Column(name = "USER_ID", precision = 22, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "OPERATE_TYPE", precision = 22, scale = 0)
	public Long getOperateType() {
		return this.operateType;
	}

	public void setOperateType(Long operateType) {
		this.operateType = operateType;
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

}