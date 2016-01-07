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
import com.xbwl.entity.OprOutLimitation;
import com.xbwl.oper.reports.service.IOprOutLimitationService;

/**
 * author CaoZhili
 * time Nov 15, 2011 1:53:10 PM
 * 出港标准时效控制层操作类
 */
@Controller
@Action("oprOutLimitationAction")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/opr_outLimitation.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }

)
public class OprOutLimitationAction extends SimpleActionSupport{

	@Resource(name="oprOutLimitationServiceImpl")
	private IOprOutLimitationService oprOutLimitationService;
	
	private OprOutLimitation oprOutLimitation;
	
	@Override
	protected Object createNewInstance() {
		return new OprOutLimitation();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprOutLimitationService;
	}

	@Override
	public Object getModel() {
		return this.oprOutLimitation;
	}

	@Override
	public void setModel(Object obj) {
		this.oprOutLimitation=(OprOutLimitation)obj;
	}

}
