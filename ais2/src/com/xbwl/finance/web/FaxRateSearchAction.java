package com.xbwl.finance.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.finance.Service.ICqCorporateRateService;
import com.xbwl.finance.vo.FaxrateSerarchVo;

@Controller
@Action("faxRateSearchAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
	@Result(name = "input", location = "/WEB-INF/xbwl/fi/bas_cqCorporateRate.jsp", type = "dispatcher"),	
	@Result(name = "reload", type = "json", params = { "root",
			"validateInfo", "excludeNullProperties", "true" }),
	@Result(name = "tree", type = "json", params = { "root", "areaList",
			"excludeNullProperties", "true" }),
	@Result(name = "list", type = "json", params = { "root", "pages",
			"includeProperties", "*" }) })
public class FaxRateSearchAction extends SimpleActionSupport {

	@Resource(name = "cqCorporateRateServiceImpl")
	private ICqCorporateRateService cqCorporateRateService;
	private FaxrateSerarchVo faxrateSerarchVo;

	@Override
	protected Object createNewInstance() {
		return new FaxrateSerarchVo();
	} 

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return cqCorporateRateService;
	}

	@Override
	public Object getModel() {
		return faxrateSerarchVo;
	}

	@Override
	public void setModel(Object obj) {
		faxrateSerarchVo = (FaxrateSerarchVo) obj;
		
	}
	public void prepareSearchRate(){
		try {
			this.prepareSave();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String searchRate(){
		this.setPageConfig();
		try {
			Page returnPage  = cqCorporateRateService.findRate(this.getPages(), faxrateSerarchVo);
			Struts2Utils.renderJson(returnPage);
		} catch (Exception e) {
			addError("²éÑ¯³ö´í", e);
		}
		return null;
	}
}
