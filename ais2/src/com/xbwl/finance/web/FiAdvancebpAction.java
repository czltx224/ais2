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
import com.xbwl.entity.FiAdvancebp;
import com.xbwl.finance.Service.IFiAdvancebpService;

@Controller
@Action("fiAdvancebpAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiAdvancebp.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })	
public class FiAdvancebpAction extends SimpleActionSupport {

	@Resource(name="fiAdvancebpServiceImpl")
	private IFiAdvancebpService fiAdvancebpService;
	
	private FiAdvancebp fiAdvancebp;
	
	@Override
	protected Object createNewInstance() {
		return new FiAdvancebp();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiAdvancebpService;
	}

	@Override
	public Object getModel() {
		return this.fiAdvancebp;
	}

	@Override
	public void setModel(Object obj) {
		this.fiAdvancebp=(FiAdvancebp) obj;
	}
	
	//审核
	public String reviewConfirmation() throws Exception{
		 try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
	   			this.fiAdvancebpService.reviewConfirmation(fiAdvancebp);
	   			this.getValidateInfo().setSuccess(true);
	    		this.getValidateInfo().setMsg("审核成功！");
    		}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
    		}
    	}catch (Exception e) {
        	this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
    	}
		return RELOAD;
	}
	
	//撤销审核
	public String reviewRegister() throws Exception{
		 try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
	   			this.fiAdvancebpService.reviewRegister(fiAdvancebp);
	   			this.getValidateInfo().setSuccess(true);
	    		this.getValidateInfo().setMsg("撤销审核成功！");
    		}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
    		}
    	}catch (Exception e) {
        	this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
    	}
		return RELOAD;
	}
	
	//作废
	public String addRegister() throws Exception{
		 try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
	   			this.fiAdvancebpService.addRegister(fiAdvancebp);
	   			this.getValidateInfo().setSuccess(true);
	    		this.getValidateInfo().setMsg("作废成功！");
    		}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
    		}
    	}catch (Exception e) {
        	this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
    	}
		return RELOAD;
	}
	
	/**
	 * 在addRegister()前执行二次绑定.
	 */
	public void prepareAddRegister() throws Exception {
		prepareModel();
	}
	
	/**
	 * 在reviewRegister()前执行二次绑定.
	 */
	public void prepareReviewRegister() throws Exception {
		prepareModel();
	}
	
	/**
	 * 在reviewConfirmation()前执行二次绑定.
	 */
	public void prepareReviewConfirmation() throws Exception {
		prepareModel();
	}

}
