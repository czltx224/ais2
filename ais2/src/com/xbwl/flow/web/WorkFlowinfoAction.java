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
import com.xbwl.entity.FlowWorkflowinfo;
import com.xbwl.flow.service.IWorkFlowinfoService;

/**
 * 流程管理- 流程流转信息控制层
 * @author LiuHao
 * @time Aug 16, 2011 2:59:16 PM
 */
@Controller
@Action("workFlowinfoAction")
@Scope("prototype")
@Namespace("/flow")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/flow/flow_export.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) })
public class WorkFlowinfoAction extends SimpleActionSupport {
	
	@Resource(name="workFlowinfoServiceImpl")
	private IWorkFlowinfoService workFlowinfoService;
	private FlowWorkflowinfo flowWorkflowinfo;

	@Override
	protected Object createNewInstance() {
		return new FlowWorkflowinfo();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return workFlowinfoService;
	}

	@Override
	public Object getModel() {
		return flowWorkflowinfo;
	}

	@Override
	public void setModel(Object obj) {
		this.flowWorkflowinfo=(FlowWorkflowinfo)obj;
	}
}
