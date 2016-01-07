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
import com.xbwl.entity.BasDriver;
import com.xbwl.sys.service.IBasDriverService;

/**
 * author CaoZhili time Jun 22, 2011 2:24:09 PM
 */
@Controller
@Action("driverAction")
@Scope("prototype")
@Namespace("/driver")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/sys/sys_basDriver.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class BasDriverAction extends SimpleActionSupport {

	private BasDriver basDriver;

	@Resource(name = "basDriverServiceImpl")
	private IBasDriverService basDriverService;

	@Override
	protected Object createNewInstance() {
		return new BasDriver();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.basDriverService;
	}

	@Override
	public Object getModel() {
		return this.basDriver;
	}

	@Override
	public void setModel(Object obj) {

		this.basDriver = (BasDriver) obj;
	}

}
