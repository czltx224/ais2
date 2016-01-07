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
 * FlowWorkflowlog entity.
 * 流程日志
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FLOW_WORKFLOWLOG")
public class FlowWorkflowlog implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long workflowId;//流程ID
	private Long nodeinfoId;//节点ID
	private Long logType;//审批类型(1通过\2否决\3退回)
	private Long returnNodeid;//退回节点ID
	private String remark;//审批备注
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String ts;
	
	private Long operatorId;//操作者ID

	// Constructors

	/** default constructor */
	public FlowWorkflowlog() {
	}

	/** minimal constructor */
	public FlowWorkflowlog(Long id) {
		this.id = id;
	}

	/** full constructor */
	public FlowWorkflowlog(Long id, Long workflowId, Long nodeinfoId,
			Long logType, Long returnNodeid, String remark, Date createTime,
			String createName, String ts) {
		this.id = id;
		this.workflowId = workflowId;
		this.nodeinfoId = nodeinfoId;
		this.logType = logType;
		this.returnNodeid = returnNodeid;
		this.remark = remark;
		this.createTime = createTime;
		this.createName = createName;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName="SEQ_FLOW_WORKFLOWLOG")
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

	@Column(name = "NODEINFO_ID", precision = 10, scale = 0)
	public Long getNodeinfoId() {
		return this.nodeinfoId;
	}

	public void setNodeinfoId(Long nodeinfoId) {
		this.nodeinfoId = nodeinfoId;
	}

	@Column(name = "LOG_TYPE", precision = 1, scale = 0)
	public Long getLogType() {
		return this.logType;
	}

	public void setLogType(Long logType) {
		this.logType = logType;
	}

	@Column(name = "RETURN_NODEID", precision = 10, scale = 0)
	public Long getReturnNodeid() {
		return this.returnNodeid;
	}

	public void setReturnNodeid(Long returnNodeid) {
		this.returnNodeid = returnNodeid;
	}

	@Column(name = "REMARK", length = 1024)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	/**
	 * @return the updateTime
	 */
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATE_TIME")
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
	@Column(name = "UPDATE_NAME")
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
	 * @return the operatorId
	 */
	@Column(name = "OPERATOR_ID")
	public Long getOperatorId() {
		return operatorId;
	}

	/**
	 * @param operatorId the operatorId to set
	 */
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	
}