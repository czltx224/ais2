package com.xbwl.ct.web;

import java.util.List;
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
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.ct.service.ICtUserService;

import dto.CtUserDto;

/**
 * author shuw
 * time May 2, 2012 10:03:29 AM
 */
@Controller
@Action("ctUserAction")
@Scope("prototype")
@Namespace("/ct")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/ct/ct_user.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }) })
public class CtUserAction extends SimpleActionSupport{
	
	private CtUserDto ctUser	;
	
	@Resource(name="ctUserServiceImpl")
	private ICtUserService ctUserService;

	protected Object createNewInstance() {
		return new CtUserDto();
	}

	public Map getContextMap() {
		return null;
	}

	protected IBaseService getManager() {
		return ctUserService;
	}

	public Object getModel() {
		return ctUser;
	}

	public void setModel(Object obj) {
		ctUser=(CtUserDto)obj;
	}
	
	/**
	 * 通过远程接口查询EDI用户
	 * @return
	 */
	public String findCtUser(){
		
		try{
			setPageConfig();
			String checkItems = getCheckItems();
			if(null!=checkItems && checkItems.indexOf("_")>0){
				checkItems = checkItems.substring(checkItems.indexOf("_")+1);
			}else{
				checkItems="";
			}
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			if(null!=map.get("EQL_id")){
				map.put("id", map.get("EQL_id"));
			}
			map.put(checkItems, getItemsValue());
			List rsList = this.ctUserService.findCtUser(map);
			getPages().setResultMap(rsList);
		}catch (Exception e) {
			addError(e.getLocalizedMessage(), e);
			e.printStackTrace();
		}
		return this.LIST;
	}
	
	/**
	 * 通过远程接口保存EDI用户
	 * @return
	 */
	public String saveCtUser(){
		try{
			CtUserDto dto = new CtUserDto();
			dto.setAddress(ServletActionContext.getRequest().getParameter("address"));
			dto.setContact(ServletActionContext.getRequest().getParameter("contact"));
			dto.setPhone(ServletActionContext.getRequest().getParameter("phone"));
			dto.setRemark(ServletActionContext.getRequest().getParameter("remark"));
			dto.setUserName(ServletActionContext.getRequest().getParameter("userName"));
			dto.setUserPassword(ServletActionContext.getRequest().getParameter("userPassword"));
			dto.setUserId(ServletActionContext.getRequest().getParameter("userId"));
			dto.setStatus(ServletActionContext.getRequest().getParameter("status"));
			dto.setId(ServletActionContext.getRequest().getParameter("id"));
			dto.setFree1(ServletActionContext.getRequest().getParameter("free1"));
			
			this.ctUserService.save(dto);
			getValidateInfo().setMsg("数据保存成功！");
    		addMessage("数据保存成功！");
		}catch (Exception e) {
			getValidateInfo().setSuccess(false);
			getValidateInfo().setMsg("数据保存失败！");
			e.printStackTrace();
			addError(e.getLocalizedMessage(), e);
		}
		return this.RELOAD;
	}
	
	public String deleteCtUser(){
		String ids = ServletActionContext.getRequest().getParameter("ids");
		try{
			this.ctUserService.deleteCtUser(ids.split(","));
    		addMessage("删除数据成功！");
		}catch (Exception e) {
			e.printStackTrace();
			addError(e.getLocalizedMessage(), e);
		}
		return this.RELOAD;
	}
}
