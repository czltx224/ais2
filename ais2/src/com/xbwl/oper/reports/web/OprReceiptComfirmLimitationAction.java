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
import com.xbwl.entity.OprReceiptComfirmLimitation;
import com.xbwl.oper.reports.service.IOprReceiptComfirmLimitationService;

/**
 * author CaoZhili
 * time Nov 15, 2011 5:31:27 PM
 * 
 * 回单确收时效控制层操作类
 */
@Controller
@Action("oprReceiptComfirmLimitationAction")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/opr_receiptComfirmLimitation.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }

)
public class OprReceiptComfirmLimitationAction extends SimpleActionSupport{

	@Resource(name="oprReceiptComfirmLimitationServiceImpl")
	private IOprReceiptComfirmLimitationService oprReceiptComfirmLimitationService;
	
	private OprReceiptComfirmLimitation oprReceiptComfirmLimitation;
	
	@Override
	protected Object createNewInstance() {
		return new OprReceiptComfirmLimitation();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprReceiptComfirmLimitationService;
	}

	@Override
	public Object getModel() {
		return this.oprReceiptComfirmLimitation;
	}

	@Override
	public void setModel(Object obj) {
		this.oprReceiptComfirmLimitation=(OprReceiptComfirmLimitation)obj;
	}

}
