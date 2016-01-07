package com.xbwl.finance.web;

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
import com.xbwl.finance.Service.IFiCostService;
import com.xbwl.finance.vo.FiCostAnalysisVo;

/**
 * author shuw
 * time Oct 13, 2011 5:50:59 PM
 */
@Controller
@Action("fiCostAnalysisVoAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_cost_analysisVo.jsp", type = "dispatcher"),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiCostAnalysisVoAction extends SimpleActionSupport {

private FiCostAnalysisVo fiCostAnalysisVo;
	
	@Resource(name = "fiCostServiceImpl")
	private IFiCostService fiCostService;

	@Override
	protected Object createNewInstance() {
		return new FiCostAnalysisVo();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiCostService;
	}

	@Override
	public Object getModel() {
		return fiCostAnalysisVo;
	}

	@Override
	public void setModel(Object obj) {
		fiCostAnalysisVo=(FiCostAnalysisVo)obj;
	}
}
