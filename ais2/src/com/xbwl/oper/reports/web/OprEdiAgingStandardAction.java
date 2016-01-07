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
import com.xbwl.entity.OprEdiAgingStandard;
import com.xbwl.oper.reports.service.IOprEdiAgingStandardService;

@Controller
@Action("oprEdiAgingStandardAction")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/transit/opr_ediAgingStandard.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) 
})
public class OprEdiAgingStandardAction extends SimpleActionSupport {

	@Resource(name="oprEdiAgingStandardServiceImpl")
	private IOprEdiAgingStandardService oprEdiAgingStandardService;
	
	private OprEdiAgingStandard oprEdiAgingStandard;
	
	@Override
	protected Object createNewInstance() {
		return new OprEdiAgingStandard();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprEdiAgingStandardService;
	}

	@Override
	public Object getModel() {
		return this.oprEdiAgingStandard;
	}

	@Override
	public void setModel(Object obj) {
		this.oprEdiAgingStandard=(OprEdiAgingStandard)obj;
	}

}
