package com.xbwl.sys.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.BasLoadingbrigade;
import com.xbwl.sys.service.IBasLoadingbrigadeService;

@Controller
@Action("loadingbrigadeAction")  //loadingbrigadeAction
@Scope("prototype")
@Namespace("/sys")
@Results( {
            @Result(name = "input", location = "/WEB-INF/xbwl/sys/sys_loadingbrigade.jsp", type = "dispatcher"),
            @Result(name = "reload", type = "json", params = {"root","validateInfo","excludeNullProperties","true"}),
            @Result(name = "list", type = "json", params = {"root","pages","excludeNullProperties","true"})
})
public class BasLoadingbrigadeAction extends SimpleActionSupport{
	
	@Resource
	private IBasLoadingbrigadeService	loadingbrigadeService;
 
	@Resource
	private  DozerBeanMapper dozer;
	
	private BasLoadingbrigade basLoadingbrigade;
	

	@Override
	protected Object createNewInstance() {
		return new BasLoadingbrigade();
	}

	@Override
	public Map getContextMap() {
		return null ;
	}

	@Override
	protected IBaseService getManager() {
		return loadingbrigadeService;
	}

	@Override
	public Object getModel() {
		return basLoadingbrigade;
	}

	@Override
	public void setModel(Object obj) {
		basLoadingbrigade=(BasLoadingbrigade)obj;
	}

}
