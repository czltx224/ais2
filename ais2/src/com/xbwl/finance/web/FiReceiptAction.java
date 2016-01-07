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
import com.xbwl.entity.FiReceipt;
import com.xbwl.finance.Service.IFiReceiptService;

/**
 * author shuw
 * time Dec 20, 2011 10:27:49 AM
 */
@Controller
@Action("fiReceiptAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_Receipt.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiReceiptAction extends SimpleActionSupport{

	private FiReceipt fiReceipt;
	
	@Resource(name="fiReceiptServiceImpl")
	private IFiReceiptService fiReceiptService;
	
	public String deleteByStatus(){
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
	    			List<Long >idList = getPksByIds();
	    			fiReceiptService.deleteByStatus(idList);
	        		getValidateInfo().setMsg("操作成功");
	        		getValidateInfo().setSuccess(true);
	        		addMessage("操作成功");
	        	}else{
	        		getValidateInfo().setSuccess(false);
	        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        	}
	            
	        } catch (Exception e) {
	        	getValidateInfo().setSuccess(false);
	        	getValidateInfo().setMsg(e.getLocalizedMessage());
	            addError("数据保存失败！", e);
	        }
		return RELOAD;
	}
	
	protected Object createNewInstance() {
		return new FiReceipt();
	}

	public Map getContextMap() {
		return null;
	}

	protected IBaseService getManager() {
		return fiReceiptService;
	}

	public Object getModel() {
		return fiReceipt;
	}

	public void setModel(Object obj) {
		fiReceipt=(FiReceipt)obj;
	}

}
