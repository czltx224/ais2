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
import com.xbwl.entity.FiAdvanceset;
import com.xbwl.finance.Service.IFiAdvancesetServie;

@Controller
@Action("fiAdvancesetAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiAdvanceset.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class FiAdvancesetAction extends SimpleActionSupport {

	@Resource(name = "fiAdvancesetServiceImpl")
	private IFiAdvancesetServie fiAdvancesetServie;
	private FiAdvanceset fiAdvanceset;

	@Override
	protected Object createNewInstance() {
		return new FiAdvanceset();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiAdvancesetServie;
	}

	@Override
	public Object getModel() {
		return this.fiAdvanceset;
	}

	@Override
	public void setModel(Object obj) {
		this.fiAdvanceset = (FiAdvanceset) obj;
	}

}
