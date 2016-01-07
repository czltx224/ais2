package com.xbwl.sys.web;

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
import com.xbwl.entity.BasSpecialArea;
import com.xbwl.sys.service.IBasSpecialAreaService;

/**
 * 特殊地区表控制层
 * author shuw
 * time Apr 12, 2012 3:51:22 PM
 */
@Controller
 @Action("basSpecialAreaAction")  //loadingbrigadeAction
 @Scope("prototype")
 @Namespace("/bas")
 @Results( {
 			@Result(name = "input", location = "/WEB-INF/xbwl/sys/bas_special_area.jsp", type = "dispatcher"),
 			@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
 			@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" })})
public class BasSpecialAreaAction extends SimpleActionSupport {

	private BasSpecialArea basSpecialArea;
	
	@Resource(name="basSpecialAreaServiceImpl")
	private IBasSpecialAreaService basSpecialAreaService;
	
	protected Object createNewInstance() {
		return new BasSpecialArea();
	}

	public Map getContextMap() {
		return null;
	}

	protected IBaseService getManager() {
		return basSpecialAreaService;
	}

	public Object getModel() {
		return basSpecialArea;
	}

	public void setModel(Object obj) {
		basSpecialArea=(BasSpecialArea)obj;
	}

}
