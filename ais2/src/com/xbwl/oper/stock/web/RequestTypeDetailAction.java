package com.xbwl.oper.stock.web;

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
import com.xbwl.entity.RequestTypeDetail;
import com.xbwl.oper.stock.service.IRequestTypeDetailService;

/**
 * author LiuHao
 * time Aug 22, 2011 11:35:46 AM
 */

@Controller
@Action("requestTypeDetailAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		//@Result(name = "input", location = "/WEB-INF/xbwl/stock/request_type.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) })
public class RequestTypeDetailAction extends SimpleActionSupport {
	private RequestTypeDetail requestTypeDetail;
	@Resource(name = "requestTtypeDetailServiceImpl")
	private IRequestTypeDetailService requestTypeDetailService;	
	
	@Override
	protected Object createNewInstance() {
		return new RequestTypeDetail();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return requestTypeDetailService;
	}

	@Override
	public Object getModel() {
		return requestTypeDetail;
	}

	@Override
	public void setModel(Object obj) {
		requestTypeDetail=(RequestTypeDetail)obj;
	}

}
