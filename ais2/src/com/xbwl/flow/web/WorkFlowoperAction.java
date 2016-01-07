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
import com.xbwl.entity.FlowWorkflowoperators;
import com.xbwl.flow.service.IWorkFlowoperService;

/**
 * 流程管理- 流程流转信息控制层
 * @author LiuHao
 * @time Aug 16, 2011 2:59:16 PM
 */
@Controller
@Action("workFlowoperAction")
@Scope("prototype")
@Namespace("/flow")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/flow/flow_export.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) })
public class WorkFlowoperAction extends SimpleActionSupport {
	
	@Resource(name="workFlowoperServiceImpl")
	private IWorkFlowoperService workFlowoperService;
	private FlowWorkflowoperators flowWorkflowoperators;

	@Override
	protected Object createNewInstance() {
		return new FlowWorkflowoperators();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return workFlowoperService;
	}

	@Override
	public Object getModel() {
		return flowWorkflowoperators;
	}

	@Override
	public void setModel(Object obj) {
		this.flowWorkflowoperators=(FlowWorkflowoperators)obj;
	}
}
