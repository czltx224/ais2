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
import com.xbwl.entity.OprScanningLimitation;
import com.xbwl.oper.reports.service.IOprScanningLimitationService;

/**
 * author CaoZhili
 * time Nov 15, 2011 4:38:17 PM
 * 
 * 扫描时效标准控制层操作类 
 */
@Controller
@Action("oprScanningLimitationAction")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/opr_scanningLimitation.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }

)
public class OprScanningLimitationAction extends SimpleActionSupport{

	@Resource(name="oprScanningLimitationServiceImpl")
	private IOprScanningLimitationService oprScanningLimitationService;
	
	private OprScanningLimitation oprScanningLimitation;
	
	@Override
	protected Object createNewInstance() {
		return new OprScanningLimitation();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprScanningLimitationService;
	}

	@Override
	public Object getModel() {
		return this.oprScanningLimitation;
	}

	@Override
	public void setModel(Object obj) {
		this.oprScanningLimitation=(OprScanningLimitation)obj;
	}

}
