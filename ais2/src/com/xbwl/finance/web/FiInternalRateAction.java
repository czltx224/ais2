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
import com.xbwl.entity.FiInternalRate;
import com.xbwl.finance.Service.IFiInternalRateService;

/**
 * author CaoZhili time Oct 19, 2011 5:26:00 PM
 * 内部结算控制层操作类
 */
@Controller
@Action("fiInternalRateAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_fiInternalRate.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class FiInternalRateAction extends SimpleActionSupport {

	@Resource(name = "fiInternalRateServiceImpl")
	private IFiInternalRateService fiInternalRateService;

	private FiInternalRate fiInternalRate;

	@Override
	protected Object createNewInstance() {
		return new FiInternalRate();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiInternalRateService;
	}

	@Override
	public Object getModel() {
		return this.fiInternalRate;
	}

	@Override
	public void setModel(Object obj) {
		this.fiInternalRate = (FiInternalRate) obj;
	}

	public String invalid(){
		
		String strids[] =ServletActionContext.getRequest().getParameter("ids").split(",");
		Long status=0l;
		try{
			this.fiInternalRateService.invalidService(strids,status);
		}catch (Exception e) {
			addError("内部结算协议价作废失败！", e);
		}
		return this.RELOAD;
	}
	
}
