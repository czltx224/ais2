package com.xbwl.finance.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiAppreciationService;
import com.xbwl.finance.Service.IFiAppreciationServiceService;

/**
 * author shuw
 * time Oct 26, 2011 3:57:17 PM
 */
@Controller
@Action("fiAppreciationServicesAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_appreciation_service_fee.jsp", type = "dispatcher"),
		@Result(name = "show", location = "/WEB-INF/xbwl/fi/fi_appreciation_service_fee_show.jsp", type = "dispatcher"),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiAppreciationServicesAction extends SimpleActionSupport {

	private FiAppreciationService fiAppreciationService;
	
	@Resource(name = "fiAppreciationServiceServiceImpl")
	private IFiAppreciationServiceService fiAppreciationServiceService;
	
	
	public String saveFiAudit() {
		try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		List<Long>list  = getPksByIds();
        		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
        		fiAppreciationServiceService.saveFiAudit(list,user);
        		getValidateInfo().setMsg("数据保存成功！");
        		addMessage("数据保存成功！");
        	}else{
        		getValidateInfo().setSuccess(false);
        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
            
        } catch (Exception e) {
        	getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
        }
		return RELOAD;
	}
	
	public String saveService() {
		try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		List<Long>list  = getPksByIds();
        		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
        		fiAppreciationServiceService.saveService(list,user);
        		getValidateInfo().setMsg("数据保存成功！");
        		addMessage("数据保存成功！");
        	}else{
        		getValidateInfo().setSuccess(false);
        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
            
        } catch (Exception e) {
        	getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
        }
		return RELOAD;
	}
	
	public String show(){
		return "show";
	}
	
	
	
	@Override
	protected Object createNewInstance() {
		return new FiAppreciationService();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiAppreciationServiceService;
	}

	@Override
	public Object getModel() {
		return fiAppreciationService;
	}

	@Override
	public void setModel(Object obj) {
		fiAppreciationService=(FiAppreciationService)obj;
	}

}
