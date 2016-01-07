package com.xbwl.finance.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.finance.Service.IFiIncomeService;
import com.xbwl.finance.vo.FiIncomeBalanceVo;

/**
 * author shuw
 * time Feb 22, 2012 5:53:29 PM
 */
@Controller
@Action("fiIncomeBalanceVoAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_Income_Balance.jsp", type = "dispatcher"),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })	
public class FiIncomeBalanceVoAction extends SimpleActionSupport {

	private FiIncomeBalanceVo fiIncomeBalanceVo;
	
	@Resource(name="fiIncomeServiceImpl")
	private IFiIncomeService fiIncomeService;
	
	public String list()  {
		try {
			setPageConfig();
			Map map = new HashMap();
			Date  startTime =fiIncomeBalanceVo.getStartTime()==null?new Date():fiIncomeBalanceVo.getStartTime();
			Date endTime =fiIncomeBalanceVo.getEndTime()==null?new Date():fiIncomeBalanceVo.getEndTime();
			map.put("startTime1",startTime);
			map.put("endTime1",endTime);
			map.put("startTime2",startTime);
			map.put("endTime2",endTime);
			map.put("startTime3",startTime);
			map.put("endTime3",endTime);
			String sql  = fiIncomeService.getAllincomeBalanceVo(fiIncomeBalanceVo);
			fiIncomeService.getPageBySqlMap(getPages(), sql, map);
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setMsg("查询操作成功");
		} catch (Exception e) {
			   addError("操作失败,失败原因:"+e.getLocalizedMessage(),e);
			   return RELOAD;
		}
		return LIST;
	}

	protected Object createNewInstance() {
		return new FiIncomeBalanceVo();
	}

	public Map getContextMap() {
		return null;
	}

	protected IBaseService getManager() {
		return fiIncomeService;
	}

	public Object getModel() {
		return fiIncomeBalanceVo;
	}

	public void setModel(Object obj) {
		fiIncomeBalanceVo=(FiIncomeBalanceVo)obj;
	}

	public FiIncomeBalanceVo getFiIncomeBalanceVo() {
		return fiIncomeBalanceVo;
	}

	public void setFiIncomeBalanceVo(FiIncomeBalanceVo fiIncomeBalanceVo) {
		this.fiIncomeBalanceVo = fiIncomeBalanceVo;
	}

}
