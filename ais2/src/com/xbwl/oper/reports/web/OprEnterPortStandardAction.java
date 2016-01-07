package com.xbwl.oper.reports.web;

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
import com.xbwl.entity.OprEnterPortStandard;
import com.xbwl.oper.reports.service.IOprEnterPortStandardService;

/**
 * author CaoZhili
 * time Nov 9, 2011 11:03:46 AM
 */
@Controller
@Action("oprEnterPortStandardAction")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/opr_enterPortStandard.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }

)
public class OprEnterPortStandardAction extends SimpleActionSupport{

	@Resource(name="oprEnterPortStandardServiceImpl")
	private IOprEnterPortStandardService enterPortStandardService;
	
	private OprEnterPortStandard enterPortStandard;
	
	@Override
	protected Object createNewInstance() {
		return new OprEnterPortStandard();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.enterPortStandardService;
	}

	@Override
	public Object getModel() {
		return this.enterPortStandard;
	}

	@Override
	public void setModel(Object obj) {
		this.enterPortStandard=(OprEnterPortStandard)obj;
	}

}
