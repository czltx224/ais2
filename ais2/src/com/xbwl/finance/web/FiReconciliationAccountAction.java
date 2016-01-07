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
import com.xbwl.entity.FiReconciliationAccount;
import com.xbwl.finance.Service.IFiReconciliationAccountService;

/**
 * author shuw
 * time Dec 7, 2011 2:34:39 PM
 */
@Controller
@Action("fiReconciliationAccountAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_reconciliation_account.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root", "validateInfo","excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiReconciliationAccountAction extends SimpleActionSupport{

	private FiReconciliationAccount fiReconciliationAccount;
	
	@Resource(name="fiReconciliationAccountServiceImpl")
	private IFiReconciliationAccountService fiReconciliationAccountService;
	
	@Override
	protected Object createNewInstance() {
		return new FiReconciliationAccount();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiReconciliationAccountService;
	}

	@Override
	public Object getModel() {
		return fiReconciliationAccount;
	}

	@Override
	public void setModel(Object obj) {
		fiReconciliationAccount=(FiReconciliationAccount)obj;
	}

}
