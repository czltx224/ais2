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
import com.xbwl.entity.FiIncome;
import com.xbwl.finance.Service.IFiIncomeService;


@Controller
@Action("fiIncomeAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })	
public class FiIncomeAction extends SimpleActionSupport {

	@Resource(name="fiIncomeServiceImpl")
	private IFiIncomeService fiIncomeService;
	private FiIncome fiIncome;
		
	@Override
	protected Object createNewInstance() {
		return new FiIncome();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return fiIncomeService;
	}
	@Override
	public Object getModel() {
		return fiIncome;
	}
	@Override
	public void setModel(Object obj) {
		fiIncome=(FiIncome) obj;

	}
}
