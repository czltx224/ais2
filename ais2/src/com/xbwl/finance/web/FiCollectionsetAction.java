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
import com.xbwl.entity.FiCollectionset;
import com.xbwl.finance.Service.IFiCollectionsetService;


@Controller
@Action("fiCollectionsetAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiCollectionset.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })	
public class FiCollectionsetAction extends SimpleActionSupport {

	@Resource(name="fiCollectionsetServiceImpl")
	private IFiCollectionsetService fiCollectionsetService;
	
	private FiCollectionset fiCollectionset;
	
	@Override
	protected Object createNewInstance() {
		return new FiCollectionset();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiCollectionsetService;
	}

	@Override
	public Object getModel() {
		return fiCollectionset;
	}

	@Override
	public void setModel(Object obj) {
		this.fiCollectionset=(FiCollectionset) obj;
		

	}

}
