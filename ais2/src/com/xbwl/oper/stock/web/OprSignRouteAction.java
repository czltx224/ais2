package com.xbwl.oper.stock.web;

import java.text.SimpleDateFormat;
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
import com.xbwl.entity.OprSignRoute;
import com.xbwl.oper.stock.service.IOprSignRouteService;

/**
 * author shuw
 * time Aug 2, 2011 1:56:54 PM
 */
@Controller
@Action("oprSignRouteAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
//		@Result(name = "input", location = "/", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) }
)
public class OprSignRouteAction extends SimpleActionSupport {
	
	@Resource(name="oprSignRouteServiceImpl")
	private IOprSignRouteService oprSignRouteService;
	
	private OprSignRoute oprSignRoute;
	
	@Override
	protected Object createNewInstance() {
		return new OprSignRoute();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return oprSignRouteService;
	}

	@Override
	public Object getModel() {
		return oprSignRoute;
	}

	@Override
	public void setModel(Object obj) {
		oprSignRoute=(OprSignRoute)obj;
	}

}
