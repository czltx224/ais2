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
import com.xbwl.entity.BasCusService;
import com.xbwl.sys.service.IBasCusService;

/**
 *@author LiuHao
 *@time Jul 29, 2011 4:16:20 PM
 */
@Controller
@Action("basCusServiceAction")
@Scope("prototype")
@Namespace("/sys")
@Results({
            @Result(name = "reload", type = "json", params = {"root","validateInfo","excludeNullProperties","true"}),
            @Result(name = "tree", type = "json", params = {"root","areaList","excludeNullProperties","true"}),
            @Result(name = "list", type = "json", params = {"root","pages","includeProperties","*"})
         })
public class BasCusServiceAction extends SimpleActionSupport {
	private BasCusService basCusService;
	@Resource(name="basCusServiceImpl")
	private IBasCusService basCusServiceService;
	private Long cusId;
	@Override
	protected Object createNewInstance() {
		return new BasCusService();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return basCusServiceService;
	}

	@Override
	public Object getModel() {
		return basCusService;
	}

	@Override
	public void setModel(Object obj) {
		basCusService=(BasCusService)obj;
	}

	/**
	 * @return the cusId
	 */
	public Long getCusId() {
		return cusId;
	}

	/**
	 * @param cusId the cusId to set
	 */
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}
	
}
