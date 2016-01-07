package com.xbwl.finance.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.FiInternalSpecialRate;
import com.xbwl.finance.Service.IFiInternalSpecialRateService;

/**
 * author CaoZhili time Oct 20, 2011 10:42:47 AM
 * 内部特殊客户协议价设置控制层操作类
 */
@Controller
@Action("fiInternalSpecialRateAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_fiInternalSpecialRate.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class FiInternalSpecialRateAction extends SimpleActionSupport {

	@Resource(name = "fiInternalSpecialRateServiceImpl")
	private IFiInternalSpecialRateService fiInternalSpecialRateService;

	private FiInternalSpecialRate fiInternalSpecialRate;

	@Override
	protected Object createNewInstance() {
		return new FiInternalSpecialRate();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiInternalSpecialRateService;
	}

	@Override
	public Object getModel() {
		return this.fiInternalSpecialRate;
	}

	@Override
	public void setModel(Object obj) {
		this.fiInternalSpecialRate = (FiInternalSpecialRate) obj;
	}

	public String invalid(){
		
		String strids[] =ServletActionContext.getRequest().getParameter("ids").split(",");
		Long status=0l;
		try{
			this.fiInternalSpecialRateService.invalidService(strids,status);
		}catch (Exception e) {
			addError("内部特殊协议价作废失败！", e);
		}
		return this.RELOAD;
	}
}
