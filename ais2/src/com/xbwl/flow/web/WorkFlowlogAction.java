package com.xbwl.flow.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.FlowWorkflowlog;
import com.xbwl.flow.service.IWorkFlowlogService;

/**
 * 流程管理- 流程流转信息控制层
 * @author LiuHao
 * @time Aug 16, 2011 2:59:16 PM
 */
@Controller
@Action("workFlowlogAction")
@Scope("prototype")
@Namespace("/flow")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/flow/flow_export.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) })
public class WorkFlowlogAction extends SimpleActionSupport {
	
	@Resource(name="workFlowlogServiceImpl")
	private IWorkFlowlogService workFlowlogService;
	private FlowWorkflowlog flowWorkflowlog;
	private Long workflowId;

	@Override
	protected Object createNewInstance() {
		return new FlowWorkflowlog();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return workFlowlogService;
	}

	@Override
	public Object getModel() {
		return flowWorkflowlog;
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

	@Override
	public void setModel(Object obj) {
		this.flowWorkflowlog=(FlowWorkflowlog)obj;
	}
	
	public String getFlowLog(){
		this.setPageConfig();
		try {
			workFlowlogService.getFlowLogByWid(this.getPages(), workflowId);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
}
