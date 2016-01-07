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
import com.xbwl.entity.BasFlight;
import com.xbwl.sys.service.IBasFlightService;

@Controller
@Action("basFlightAction")
@Scope("prototype")
@Namespace("/sys")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/sys/basFlight.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class BasFlightAction extends SimpleActionSupport {

	@Resource(name = "basFilghtServiceImpl")
	private IBasFlightService basFlightService;
	private BasFlight basFlight;
	

	@Override
	protected Object createNewInstance() {
		return new BasFlight();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return basFlightService;
	}

	@Override
	public Object getModel() {
		return basFlight;
	}

	@Override
	public void setModel(Object obj) {
		basFlight = (BasFlight) obj;
		
	}

}
