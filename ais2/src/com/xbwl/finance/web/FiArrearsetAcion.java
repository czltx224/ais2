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
import com.xbwl.entity.FiArrearset;
import com.xbwl.finance.Service.IFiArrearsetService;
import com.xbwl.finance.Service.impl.FiArrearsetServiceImpl;

@Controller
@Action("fiArrearsetAcion")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiArrearset.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })				
public class FiArrearsetAcion extends SimpleActionSupport {

	@Resource(name="fiArrearsetServiceImpl")
	private IFiArrearsetService fiArrearsetService;
	private FiArrearset fiArrearset;
	
	@Override
	protected Object createNewInstance() {
		return new FiArrearset();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiArrearsetService;
	}

	@Override
	public Object getModel() {
		return fiArrearset;
	}

	@Override
	public void setModel(Object obj) {
		fiArrearset=(FiArrearset) obj;

	}
	
	public void jurisdictionSave() throws Exception{
		super.save();
	}

}
