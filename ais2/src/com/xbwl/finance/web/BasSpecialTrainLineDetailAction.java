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
import com.xbwl.entity.BasSpecialTrainLineDetail;
import com.xbwl.finance.Service.IBasSpecialTrainLineDetailService;

/**
 * @author CaoZhili
 * time Aug 12, 2011 11:40:48 AM
 * 
 * 专车线路细表控制层操作类
 */
@Controller
@Action("basSpecialTrainLineDetailAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class BasSpecialTrainLineDetailAction extends SimpleActionSupport{

	@Resource(name="basSpecialTrainLineDetailServiceImpl")
	private IBasSpecialTrainLineDetailService basSpecialTrainLineDetailService;
	
	private BasSpecialTrainLineDetail basSpecialTrainLineDetail;
	
	@Override
	protected Object createNewInstance() {

		return new BasSpecialTrainLineDetail();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.basSpecialTrainLineDetailService;
	}

	@Override
	public Object getModel() {

		return this.basSpecialTrainLineDetail;
	}

	@Override
	public void setModel(Object obj) {

		this.basSpecialTrainLineDetail=(BasSpecialTrainLineDetail)obj;
	}
}
