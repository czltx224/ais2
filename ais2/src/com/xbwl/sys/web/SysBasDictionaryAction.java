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
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.BasDictionaryDetail;
import com.xbwl.sys.service.IBasDictionaryDetailService;

@Controller
@Action("basDictionaryAction")
@Scope("prototype")
@Namespace("/sys")
@Results( {
            @Result(name = "input", location = "/WEB-INF/xbwl/sys/sys_basDictionary.jsp", type = "dispatcher"),
            @Result(name = "reload", type = "json", params = {"root","validateInfo","excludeNullProperties","true"}),
            @Result(name = "list", type = "json", params = {"root","pages","excludeNullProperties","true"})
})
public class SysBasDictionaryAction extends SimpleActionSupport {
	@Resource
	private IBasDictionaryDetailService dictionaryService;
	
	private BasDictionaryDetail basDictionaryDetail;
	

	@Override
	protected Object createNewInstance() {
		return new BasDictionaryDetail(); 
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return dictionaryService;
	}

	@Override
	public Object getModel() {
		return basDictionaryDetail;
	}
	
	public String getBasDictionaryDetailById(){
		try {
			Struts2Utils.renderJson(this.getManager().getAndInitEntity(this.getId()));
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return null;
		
	}

	@Override
	public void setModel(Object obj) {
		basDictionaryDetail=(BasDictionaryDetail)obj;
	}

}
