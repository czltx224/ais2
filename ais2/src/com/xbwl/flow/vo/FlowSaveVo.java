package com.xbwl.flow.vo;
/**
 *@author LiuHao
 *@time Apr 9, 2012 3:48:31 PM
 *流程保存VO
 */
public class FlowSaveVo {
	private Long workflowId;//流程ID
	private Long pipeId;//管道ID
	private Long nodeId;//节点ID
	private String oprType;//操作类型
	private Long logType;//审批类型
	private String auditRemark;//更改备注
	private Long returnNodeId;//退回节点ID
	private Long dno;//配送单号
	private String applyRemark;//审批备注
	private String formDetailStr;//表单明细字符串
	private String changeType;//更改类型
	private String manType;//人员类型
	
	private Long nextNodeId;//下个节点ID
	/**
	 * @return the workflowId
	 */
	public Long getWorkflowId() {
		return workflowId;
	}
	/**
	 * @param workflowId the workflowId to set
	 */
	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}
	/**
	 * @return the pipeId
	 */
	public Long getPipeId() {
		return pipeId;
	}
	/**
	 * @param pipeId the pipeId to set
	 */
	public void setPipeId(Long pipeId) {
		this.pipeId = pipeId;
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
	 * @return the oprType
	 */
	public String getOprType() {
		return oprType;
	}
	/**
	 * @param oprType the oprType to set
	 */
	public void setOprType(String oprType) {
		this.oprType = oprType;
	}
	/**
	 * @return the logType
	 */
	public Long getLogType() {
		return logType;
	}
	/**
	 * @param logType the logType to set
	 */
	public void setLogType(Long logType) {
		this.logType = logType;
	}
	/**
	 * @return the auditRemark
	 */
	public String getAuditRemark() {
		return auditRemark;
	}
	/**
	 * @param auditRemark the auditRemark to set
	 */
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	/**
	 * @return the returnNodeId
	 */
	public Long getReturnNodeId() {
		return returnNodeId;
	}
	/**
	 * @param returnNodeId the returnNodeId to set
	 */
	public void setReturnNodeId(Long returnNodeId) {
		this.returnNodeId = returnNodeId;
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
	 * @return the applyRemark
	 */
	public String getApplyRemark() {
		return applyRemark;
	}
	/**
	 * @param applyRemark the applyRemark to set
	 */
	public void setApplyRemark(String applyRemark) {
		this.applyRemark = applyRemark;
	}
	/**
	 * @return the formDetailStr
	 */
	public String getFormDetailStr() {
		return formDetailStr;
	}
	/**
	 * @param formDetailStr the formDetailStr to set
	 */
	public void setFormDetailStr(String formDetailStr) {
		this.formDetailStr = formDetailStr;
	}
	/**
	 * @return the changeType
	 */
	public String getChangeType() {
		return changeType;
	}
	/**
	 * @param changeType the changeType to set
	 */
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	/**
	 * @return the manType
	 */
	public String getManType() {
		return manType;
	}
	/**
	 * @param manType the manType to set
	 */
	public void setManType(String manType) {
		this.manType = manType;
	}
	public Long getNextNodeId() {
		return nextNodeId;
	}
	public void setNextNodeId(Long nextNodeId) {
		this.nextNodeId = nextNodeId;
	}
	
	
}
