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
import com.xbwl.entity.OprScanNotIncludeSendman;
import com.xbwl.oper.reports.service.IOprScanNotIncludeSendmanService;

/**
 * 签收和回单确收报表剔除送货员控制层操作类
 * @author czl
 * @date 2012-04-20
 */
@Controller
@Action("oprScanNotIncludeSendmanAction")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/opr_scanNotIncludeSendman.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class OprScanNotIncludeSendmanAction extends SimpleActionSupport {

	@Resource(name = "oprScanNotIncludeSendmanServiceImpl")
	private IOprScanNotIncludeSendmanService oprScanNotIncludeSendmanService;

	private OprScanNotIncludeSendman oprScanNotIncludeSendman;

	@Override
	protected Object createNewInstance() {
		return new OprScanNotIncludeSendman();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprScanNotIncludeSendmanService;
	}

	@Override
	public Object getModel() {
		return this.oprScanNotIncludeSendman;
	}

	@Override
	public void setModel(Object obj) {
		this.oprScanNotIncludeSendman = (OprScanNotIncludeSendman) obj;
	}

}
