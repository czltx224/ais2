package com.xbwl.finance.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiApplyfund;
import com.xbwl.finance.Service.IFiApplyfundService;

@Controller
@Action("fiApplyfundAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiApplyfund.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class FiApplyfundAction extends SimpleActionSupport {

	@Resource(name="fiApplyfundServiceImpl")
	private IFiApplyfundService fiApplyfundService;
	
	private FiApplyfund fiApplyfund;
	
	@Override
	protected Object createNewInstance() {
		return new FiApplyfund();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiApplyfundService;
	}

	@Override
	public Object getModel() {
		return this.fiApplyfund;
	}

	@Override
	public void setModel(Object obj) {
		this.fiApplyfund=(FiApplyfund) obj;

	}
	
	//保存、修改
	public String saveApplyfund() throws Exception{
		try{
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				this.fiApplyfundService.saveApplyfund(fiApplyfund);
    			this.getValidateInfo().setMsg("数据保存成功！");
        		addMessage("数据保存成功！");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		}catch(Exception e){
        	this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
		}
    	return RELOAD;
	}
	
	//审核
	public String auditApplyfund() throws Exception{
		try{
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				String isSit=Struts2Utils.getRequest().getAttribute("isSit")+"";
				if(isSit==null||"".equals(isSit)){
					throw new ServiceException("是否生成坐支单ID不能为空");
				}
				fiApplyfund.setIsSit(Long.valueOf(isSit));//资金交接类型
				this.fiApplyfundService.auditApplyfund(fiApplyfund);
    			this.getValidateInfo().setMsg("数据保存成功！");
        		addMessage("数据保存成功！");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		}catch(Exception e){
        	this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
		}
    	return RELOAD;
	}
	
	//生成坐支单
	public String fundstransferSit() throws Exception{
		try{
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				String capitalTypeId=Struts2Utils.getRequest().getAttribute("capitalTypeId")+"";
				if(capitalTypeId==null||"".equals(capitalTypeId)){
					throw new ServiceException("资金交接单类型不存在");
				}
				if(fiApplyfund==null)	throw new ServiceException("资金申请单实体不能为空");
				fiApplyfund.setCapitalTypeId(Long.valueOf(capitalTypeId));//资金交接类型
				this.fiApplyfundService.fundstransferSit(fiApplyfund);
    			this.getValidateInfo().setMsg("数据保存成功！");
        		addMessage("数据保存成功！");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		}catch(Exception e){
        	this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
		}
    	return RELOAD;
	}
	
	
    //作废
    public String invalidApplyfund() throws Exception{
    	try{
    		if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				this.fiApplyfundService.invalidApplyfund(fiApplyfund);
				this.getValidateInfo().setSuccess(true);
    			this.getValidateInfo().setMsg("数据保存成功！");
        		addMessage("数据保存成功！");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
    	}catch(Exception e){
    		this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("数据保存失败");
    		addError("数据保存失败!", e);
    	}
    	return RELOAD;
    }
    
    
    public String receivablesConfirmation() throws Exception{
		try {
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				Long id=Long.valueOf(Struts2Utils.getParameter("id"));
				User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
				if("null".equals(id)||"".equals(id)||id==null){
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg("资金申请单不存在!");
				}
				this.fiApplyfundService.receivablesConfirmation(id, user);
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("收款确认成功!");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"收款确认失败!失败原因：" + e.getLocalizedMessage());
			logger.error(e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
    }
    
	/**
	 * 在save()前执行二次绑定.
	 */
	public void prepareFundstransferSit() throws Exception {
		prepareModel();
	}
    
	/**
	 * 在save()前执行二次绑定.
	 */
	public void prepareAuditApplyfund() throws Exception {
		prepareModel();
	}
	
    
	/**
	 * 在save()前执行二次绑定.
	 */
	public void prepareInvalidCashiercollection() throws Exception {
		prepareModel();
	}
	
    /**
     * 保存出纳收款单前二次绑定
     * @throws Exception
     */
    public void prepareSaveApplyfund() throws Exception {
    	prepareModel();
    }
    
    

}
