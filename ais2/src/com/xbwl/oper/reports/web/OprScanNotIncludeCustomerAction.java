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
import com.xbwl.entity.OprScanNotIncludeCustomer;
import com.xbwl.oper.reports.service.IOprScanNotIncludeCustomerService;

/**
 * 签收和回单确收报表剔除代理控制层操作类
 * @author czl
 * @date 2012-04-20
 */
@Controller
@Action("oprScanNotIncludeCustomerAction")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/opr_scanNotIncludeCustomer.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class OprScanNotIncludeCustomerAction extends SimpleActionSupport {

	@Resource(name="oprScanNotIncludeCustomerServiceImpl")
	private IOprScanNotIncludeCustomerService oprScanNotIncludeCustomerService;
	
	private OprScanNotIncludeCustomer oprScanNotIncludeCustomer; 
	
	@Override
	protected Object createNewInstance() {
		return new OprScanNotIncludeCustomer();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprScanNotIncludeCustomerService;
	}

	@Override
	public Object getModel() {
		return this.oprScanNotIncludeCustomer;
	}

	@Override
	public void setModel(Object obj) {
		this.oprScanNotIncludeCustomer=(OprScanNotIncludeCustomer)obj;
	}
}
