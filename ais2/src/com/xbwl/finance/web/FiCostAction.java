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
import com.xbwl.entity.FiCost;
import com.xbwl.finance.Service.IFiCostService;

/**
 * author shuw
 * time Sep 21, 2011 3:55:52 PM
 */
@Controller
@Action("fiCostAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiCostAction extends SimpleActionSupport{

	private FiCost fiCost;
	
	@Resource(name = "fiCostServiceImpl")
	private IFiCostService fiCostService;
	
	@Override
	protected Object createNewInstance() {
		return new FiCost();
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
		return fiCost;
	}

	@Override
	public void setModel(Object obj) {
		fiCost=(FiCost)obj;
	}

}
