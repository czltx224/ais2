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
 * FlowWorkflowstep entity.
 * 流程步骤
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FLOW_WORKFLOWSTEP")
public class FlowWorkflowstep implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long workflowId;//流程id 
	private Long stepId;//步骤id
	private Long nodeId;//节点id
	private String nodeType;//节点类型1:开始，2:活动,3：结束
	private Long receiverId;//接收人id
	private Date receiveTime;//接收时间
	private Long submiterId;//提交人id
	private Date submitTime;//提交时间
	private Date createTime;
	private String createName;
	private String updateName;
	private Date updateTime;
	private String ts;

	// Constructors

	/** default constructor */
	public FlowWorkflowstep() {
	}

	/** minimal constructor */
	public FlowWorkflowstep(Long id) {
		this.id = id;
	}
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName="SEQ_FLOW_WORKFLOWSTEP")
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

	@Column(name = "STEP_ID", precision = 10, scale = 0)
	public Long getStepId() {
		return this.stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	@Column(name = "NODE_ID", precision = 10, scale = 0)
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "NODE_TYPE", precision = 1, scale = 0)
	public String getNodeType() {
		return this.nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	@Column(name = "RECEIVER_ID", precision = 10, scale = 0)
	public Long getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "RECEIVE_TIME", length = 7)
	public Date getReceiveTime() {
		return this.receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	@Column(name = "SUBMITER_ID", precision = 10, scale = 0)
	public Long getSubmiterId() {
		return this.submiterId;
	}

	public void setSubmiterId(Long submiterId) {
		this.submiterId = submiterId;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "SUBMIT_TIME", length = 7)
	public Date getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
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