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
import com.xbwl.entity.BasCar;
import com.xbwl.sys.service.IBasCarService;

/**
 * 
 * @author CaoZhili time Jun 18, 2011
 * 
 */
@Controller
@Action("basCarAction")
@Scope("prototype")
@Namespace("/bascar")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/sys/sys_basCar.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class BasCarAction extends SimpleActionSupport {

	@Resource(name = "basCarServiceImpl")
	private IBasCarService basCarService;

	private BasCar basCar;

	@Override
	protected Object createNewInstance() {
		return new BasCar();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	public Object getModel() {
		return this.basCar;
	}

	@Override
	public void setModel(Object obj) {

		this.basCar = (BasCar) obj;
	}

	@Override
	protected IBaseService getManager() {
		return this.basCarService;
	}

}
