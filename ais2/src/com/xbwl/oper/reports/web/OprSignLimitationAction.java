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
import com.xbwl.entity.OprSignLimitation;
import com.xbwl.oper.reports.service.IOprSignLimitationService;

/**
 * author CaoZhili
 * time Nov 15, 2011 3:06:15 PM
 * 签收时效标准设置控制层操作类
 */
@Controller
@Action("oprSignLimitationAction")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/opr_signLimitation.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }

)
public class OprSignLimitationAction extends SimpleActionSupport{

	@Resource(name="oprSignLimitationServiceImpl")
	private IOprSignLimitationService oprSignLimitationService;
	
	private OprSignLimitation oprSignLimitation;
	
	@Override
	protected Object createNewInstance() {
		return new OprSignLimitation();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprSignLimitationService;
	}

	@Override
	public Object getModel() {
		return this.oprSignLimitation;
	}

	@Override
	public void setModel(Object obj) {
		this.oprSignLimitation=(OprSignLimitation)obj;
	}

}
