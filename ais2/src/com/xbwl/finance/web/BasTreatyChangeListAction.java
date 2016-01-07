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
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.BasTreatyChangeList;
import com.xbwl.finance.Service.IBasTreatyChangeListService;

/**
 * @author CaoZhili
 * time Aug 10, 2011 2:39:30 PM
 */
@Controller
@Action("basTreatyChangeListAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/bas_treatyChangeList.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class BasTreatyChangeListAction extends SimpleActionSupport {

	private BasTreatyChangeList basTreatyChangeList;
	
	@Resource(name="basTreatyChangeListServiceImpl")
	private IBasTreatyChangeListService basTreatyChangeListService;
	
	@Override
	protected Object createNewInstance() {

		return new BasTreatyChangeList();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.basTreatyChangeListService;
	}

	@Override
	public Object getModel() {

		return this.basTreatyChangeList;
	}

	@Override
	public void setModel(Object obj) {

		this.basTreatyChangeList=(BasTreatyChangeList)obj;
	}
	
	public String findTableName(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			String sql=this.basTreatyChangeListService.getSqlService(map);
			
			this.basTreatyChangeListService.getPageBySqlMap(this.getPages(), sql,map);
		}catch(Exception e){
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("查询修改表名称失败！"+e.getLocalizedMessage());
			
		}
		return this.LIST;
	}

}
