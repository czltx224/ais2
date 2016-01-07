package com.xbwl.finance.web;
import java.util.HashMap;
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
import com.xbwl.entity.FiProblemreceivable;
import com.xbwl.finance.Service.IFiProblemreceivableService;
import com.xbwl.finance.Service.IFiReceivabledetailService;
import com.xbwl.finance.Service.IFiReceivablestatementService;

@Controller
@Action("fiProblemreceivableAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiProblemreceivable.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class FiProblemreceivableAction extends SimpleActionSupport {

	@Resource(name="fiProblemreceivableServiceImpl")
	private IFiProblemreceivableService fiProblemreceivableService;
	
	private FiProblemreceivable fiProblemreceivable;
	
	@Resource(name = "fiReceivabledetailServiceImpl")
	private IFiReceivabledetailService fiReceivabledetailService;
	
	@Override
	protected Object createNewInstance() {
		return new FiProblemreceivable();
	}

	@Override
	protected IBaseService getManager() {
		return fiProblemreceivableService;
	}

	@Override
	public Object getModel() {
		return fiProblemreceivable;
	}

	@Override
	public void setModel(Object obj) {
		fiProblemreceivable=(FiProblemreceivable) obj;

	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//撤销登记
	public String revocationRegister() throws Exception{
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
	        		if(!fiReceivabledetailService.isStatusAudited(this.fiProblemreceivable.getSourceNo())){
	        			this.fiProblemreceivableService.revocationRegister(this.fiProblemreceivable);
	        			this.getValidateInfo().setSuccess(true);
	    	    		this.getValidateInfo().setMsg("问题账款撤销成功！");
	        		}else{
	        			this.getValidateInfo().setSuccess(false);
	    	    		this.getValidateInfo().setMsg("操作失败:此问题账款对应的对账单已审核或已收银，不能再撤销登记！");
	        		}
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
	 * 在save()前执行二次绑定.
	 */
	public void prepareRevocationRegister() throws Exception {
		prepareModel();
	}
	
	//审核
	public String audit() throws Exception{
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
	        		this.fiProblemreceivableService.audit(this.fiProblemreceivable);
        			this.getValidateInfo().setSuccess(true);
    	    		this.getValidateInfo().setMsg("问题账款审核成功！");
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
	

	public void prepareAudit() throws Exception {
		prepareModel();
	}
	
	public String problemreceivableRegister() throws Exception{
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
	        		this.fiProblemreceivableService.problemreceivableRegister(this.fiProblemreceivable);
     			this.getValidateInfo().setSuccess(true);
 	    		this.getValidateInfo().setMsg("问题账款撤销审核成功！");
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
	
	public void prepareProblemreceivableRegister() throws Exception {
		prepareModel();
	}
}
