package com.xbwl.sys.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.BasDictionary;
import com.xbwl.sys.service.IBasDictionaryService;


@Controller
@Action("dictionaryAction")
@Scope("prototype")
@Namespace("/sys")
@Results( {
            @Result(name = "reload", type = "json", params = {"root","validateInfo","excludeNullProperties","true"}),
            @Result(name = "list", type = "json", params = {"root","pages","excludeNullProperties","true"}),
            @Result(name = "detailList", location = "/sys/basDictionaryDetailAction!list.action", type = "dispatcher")
})
public class BasDictionaryAction extends SimpleActionSupport {
	
	@Resource
	private IBasDictionaryService dictionaryBasService;
	
	private BasDictionary basDictionary;
	

	@Override
	protected Object createNewInstance() {
		return new BasDictionary();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return dictionaryBasService;
	}

	@Override
	public Object getModel() {
		return basDictionary;
	}

	@Override
	public void setModel(Object obj) {
		basDictionary = (BasDictionary)obj;
	}

	public BasDictionary getBasDictionary() {
		return basDictionary;
	}

	public void setBasDictionary(BasDictionary basDictionary) {
		this.basDictionary = basDictionary;
	}

	@Override
	public String ralaList() throws Exception {
		return "detailList";//跳转到basDictionaryDetailAction!list.action
	}

	@Override
	public String list() throws Exception {
		String ralasafe = ServletActionContext.getRequest().getParameter("ralasafe");
		
		if(null!=ralasafe && "true".equals(ralasafe)){
			return super.list();
		}else{
			return "detailList";//跳转到basDictionaryDetailAction!list.action
		}
	}
	
	
}
