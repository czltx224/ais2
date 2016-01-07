package com.xbwl.finance.web;

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
import com.xbwl.finance.Service.IFiReceivabledetailService;
import com.xbwl.finance.vo.FiReceivableVo;

/**
 * author shuw
 * time Oct 17, 2011 9:46:12 AM
 */
@Controller
@Action("fiReceivableVoAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_receivable_vo.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root", "validateInfo","excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiReceivableVoAction extends SimpleActionSupport{

	private FiReceivableVo fiReceivableVo;
	
	@Resource(name = "fiReceivabledetailServiceImpl")
	private IFiReceivabledetailService fiReceivabledetailService;

	@Override
	protected Object createNewInstance() {
		return new FiReceivableVo();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiReceivabledetailService;
	}

	@Override
	public Object getModel() {
		return fiReceivableVo;
	}

	@Override
	public void setModel(Object obj) {
		fiReceivableVo = (FiReceivableVo)obj;
	}
	
	
}
