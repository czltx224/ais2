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
import com.xbwl.entity.OprArteryCarLimitation;
import com.xbwl.oper.reports.service.IOprArteryCarLimitationService;

/**
 * author CaoZhili
 * time Nov 15, 2011 5:01:00 PM
 * 干线车标准设置控制层操作类
 */
@Controller
@Action("oprArteryCarLimitationAction")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/opr_arteryCarLimitation.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }

)
public class OprArteryCarLimitationAction extends SimpleActionSupport {

	@Resource(name="oprArteryCarLimitationServiceImpl")
	private IOprArteryCarLimitationService oprArteryCarLimitationService;
	
	private OprArteryCarLimitation oprArteryCarLimitation;
	
	@Override
	protected Object createNewInstance() {
		return new OprArteryCarLimitation();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprArteryCarLimitationService;
	}

	@Override
	public Object getModel() {
		return this.oprArteryCarLimitation;
	}

	@Override
	public void setModel(Object obj) {
		this.oprArteryCarLimitation=(OprArteryCarLimitation)obj;
	}

}
