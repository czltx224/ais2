package com.xbwl.finance.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiInvoice;
import com.xbwl.finance.Service.IFiInvoiceService;


@Controller
@Action("fiInvoiceAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiInvoice.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })	
public class FiInvoiceAction extends SimpleActionSupport {

	@Resource(name="fiInvoiceServiceImpl")
	private IFiInvoiceService fiInvoiceService;
	private FiInvoice fiInvoice;
	
	private String ids;//选择所有ID
	
	@Override
	protected Object createNewInstance() {
		return new FiInvoice();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiInvoiceService;
	}

	@Override
	public Object getModel() {
		return fiInvoice;
	}

	@Override
	public void setModel(Object obj) {
		fiInvoice=(FiInvoice) obj;

	}
	
	/**
	* @Title: 作废
	 */
	public String invalid() throws Exception{
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
	        			this.fiInvoiceService.invalid(ids);
	        			this.getValidateInfo().setMsg("作废成功！");
		        		addMessage("作废成功！");
	        	}else{
	        		this.getValidateInfo().setSuccess(false);
	        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        	}
	            
	        } catch (Exception e) {
	        	this.getValidateInfo().setSuccess(false);
	    		this.getValidateInfo().setMsg("数据保存失败！");
	            addError("数据保存失败！", e);
	        }
		return RELOAD;
	}
	
	/**
	* @Title: 审核
	 */
	public String review() throws Exception{
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
	        			this.fiInvoiceService.review(ids);
	        			this.getValidateInfo().setMsg("审核成功！");
		        		addMessage("审核成功！");
	        	}else{
	        		this.getValidateInfo().setSuccess(false);
	        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        	}
	            
	        } catch (Exception e) {
	        	this.getValidateInfo().setSuccess(false);
	    		this.getValidateInfo().setMsg("数据保存失败！");
	            addError("数据保存失败！", e);
	        }
		return RELOAD;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
