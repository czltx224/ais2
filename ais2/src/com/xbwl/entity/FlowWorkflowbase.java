package com.xbwl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * FlowWorkflowbase entity.
 * 流程表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FLOW_WORKFLOWBASE")
public class FlowWorkflowbase implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long formId;//表单Id
	private Long pipeId;//流程ID
	private String workflowName;//流程名称
	private Long workflowLevel;//流程等级(2紧急\1一般)
	private Long createType;//创建类型(1自动创建\2手工创建)
	private Long createrId;//创建人ID
	private Long isFinished;//流程状态(1正常\2归档\3否决)
	private Long isDelete;//是否删除(0删除\1正常)
	private Long curnodeids;//当前流程节点
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String ts;

	// Constructors

	/** default constructor */
	public FlowWorkflowbase() {
	}

	/** minimal constructor */
	public FlowWorkflowbase(Long id) {
		this.id = id;
	}
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName="SEQ_FLOW_WORKFLOWBASE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "FORM_ID", precision = 10, scale = 0)
	public Long getFormId() {
		return this.formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	@Column(name = "PIPE_ID", precision = 10, scale = 0)
	public Long getPipeId() {
		return this.pipeId;
	}

	public void setPipeId(Long pipeId) {
		this.pipeId = pipeId;
	}

	@Column(name = "WORKFLOW_NAME", length = 1024)
	public String getWorkflowName() {
		return this.workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	@Column(name = "WORKFLOW_LEVEL", precision = 1, scale = 0)
	public Long getWorkflowLevel() {
		return this.workflowLevel;
	}

	public void setWorkflowLevel(Long workflowLevel) {
		this.workflowLevel = workflowLevel;
	}

	@Column(name = "CREATE_TYPE", precision = 1, scale = 0)
	public Long getCreateType() {
		return this.createType;
	}

	public void setCreateType(Long createType) {
		this.createType = createType;
	}

	@Column(name = "CREATER_ID", precision = 10, scale = 0)
	public Long getCreaterId() {
		return this.createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	@Column(name = "IS_FINISHED", precision = 1, scale = 0)
	public Long getIsFinished() {
		return this.isFinished;
	}

	public void setIsFinished(Long isFinished) {
		this.isFinished = isFinished;
	}

	@Column(name = "IS_DELETE", precision = 1, scale = 0)
	public Long getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Long isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "CURNODEIDS", precision = 10, scale = 0)
	public Long getCurnodeids() {
		return this.curnodeids;
	}

	public void setCurnodeids(Long curnodeids) {
		this.curnodeids = curnodeids;
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

	@JSON(format="yyyy-MM-dd HH:mm:ss")
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