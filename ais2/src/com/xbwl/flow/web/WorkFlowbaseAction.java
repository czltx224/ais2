package com.xbwl.flow.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jdom.Document;
import org.jdom.output.XMLOutputter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FlowWorkflowbase;
import com.xbwl.flow.service.IFlowPipeinfoService;
import com.xbwl.flow.service.IWorkFlowbaseService;
import com.xbwl.flow.service.IWorkFlowstepService;
import com.xbwl.flow.vo.FlowSaveVo;

/**
 * 流程管理- 出口管理控制层
 * @author LiuHao
 * @time Aug 16, 2011 2:59:16 PM
 */
@Controller
@Action("workFlowbaseAction")
@Scope("prototype")
@Namespace("/flow")
@Results( {
		@Result(name = "ownapply", location = "/WEB-INF/xbwl/flow/flow_ownapply.jsp", type = "dispatcher"),
		@Result(name = "flowguard", location = "/WEB-INF/xbwl/flow/flow_guard.jsp", type = "dispatcher"),
		@Result(name = "charshow", location = "/WEB-INF/xbwl/flow/flow_showchar.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) })
public class WorkFlowbaseAction extends SimpleActionSupport {
	
	@Resource(name="workFlowbaseServiceImpl")
	private IWorkFlowbaseService workFlowbaseService;
	private FlowWorkflowbase flowWorkflowbase;
	@Resource(name = "workFlowstepServiceImpl")
	private IWorkFlowstepService workFlowstepService;
	@Resource(name = "flowPipeinfoServiceImpl")
	private IFlowPipeinfoService flowPipeinfoService;
	
	private String auditRemark;//审批备注
	private Long logType;//操作类型
	private Long nodeId;//节点Id
	private Long workflowId;//流程ID
	private Long pipeId;//管道ID
	private Long returnNodeId;//退回节点ID
	private String oprType;
	private String flowChartXML;
	
	private Long userId;
	private Long curNodeId;//当前节点ID
	

	@Override
	protected Object createNewInstance() {
		return new FlowWorkflowbase();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return workFlowbaseService;
	}

	@Override
	public Object getModel() {
		return flowWorkflowbase;
	}

	@Override
	public void setModel(Object obj) {
		this.flowWorkflowbase=(FlowWorkflowbase)obj;
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
	 * @return the flowChartXML
	 */
	public String getFlowChartXML() {
		return flowChartXML;
	}

	/**
	 * @param flowChartXML the flowChartXML to set
	 */
	public void setFlowChartXML(String flowChartXML) {
		this.flowChartXML = flowChartXML;
	}
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getCurNodeId() {
		return curNodeId;
	}

	public void setCurNodeId(Long curNodeId) {
		this.curNodeId = curNodeId;
	}

	/**
	 * 提交流程
	 * @author LiuHao
	 * @time Mar 2, 2012 1:45:03 PM 
	 * @return
	 */
	public String flowSubmit(){
		try {
			FlowSaveVo saveVo = new FlowSaveVo();
			saveVo.setWorkflowId(workflowId);
			saveVo.setPipeId(pipeId);
			saveVo.setNodeId(nodeId);
			saveVo.setOprType(oprType);
			saveVo.setLogType(logType);
			saveVo.setAuditRemark(auditRemark);
			workFlowbaseService.flowSubmit(saveVo);
			this.getValidateInfo().setMsg("保存成功");
			this.getValidateInfo().setSuccess(true);
		} catch (Exception e) {
			addError("保存失败", e);
		}
		return RELOAD;
	}
	/**
	 * 页面跳转
	 * @author LiuHao
	 * @time Mar 5, 2012 9:23:17 AM 
	 * @return
	 */
	public String gotoOwnApply(){
		return "ownapply";
	}
	/**
	 * 查看流程图
	 * @author LiuHao
	 * @time May 25, 2012 9:34:14 AM 
	 * @return
	 */
	public String charShow(){
		try {
			String xml = flowPipeinfoService.getShowChartStr(pipeId, workflowId);
			this.setFlowChartXML(xml);
		} catch (Exception e) {
			addError("获取流程图XMLstr格式失败", e);
		}
		return "charshow";
	}
	/**
	 * 获取流程步骤的XML格式
	 * @author LiuHao
	 * @time May 25, 2012 9:33:57 AM 
	 * @return
	 */
	public String getStepXml(){
		try {
			Document document = workFlowstepService.getStepXml(pipeId, workflowId);
			//System.out.println(document.toString());
			XMLOutputter outputter = new XMLOutputter();
			//String str=outputter.outputString(document);
			outputter.output(document,Struts2Utils.getResponse().getOutputStream());
		} catch (Exception e) {
			addError("流程图查看失败", e);
		}
		return null;
	}
	public String flowguard(){
		return "flowguard";
	}
	/**
	 * 流程转发
	 * @author LiuHao
	 * @time May 25, 2012 9:33:40 AM 
	 * @return
	 */
	public String flowsend(){
		try {
			workFlowbaseService.flowSend(userId, workflowId, nodeId);
			this.getValidateInfo().setMsg("保存成功");
			this.getValidateInfo().setSuccess(true);
		} catch (Exception e) {
			addError("保存出错！", e);
		}
		return RELOAD;
	}
	
	public String flowControl(){
		try {
			FlowSaveVo saveVo = new FlowSaveVo();
			saveVo.setWorkflowId(workflowId);
			saveVo.setPipeId(pipeId);
			saveVo.setNodeId(curNodeId);
			saveVo.setNextNodeId(nodeId);
			workFlowbaseService.flowControl(saveVo);
			this.getValidateInfo().setMsg("流程控制成功");
			this.getValidateInfo().setSuccess(true);
		} catch (Exception e) {
			addError("操作出错", e);
		}
		return RELOAD;
	}
}
