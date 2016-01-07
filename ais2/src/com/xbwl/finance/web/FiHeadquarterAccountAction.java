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

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiHeadquarterAccount;
import com.xbwl.finance.Service.IFiHeadquarterAccountService;

@Controller
@Action("fiHeadquarterAccountAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_headquarter_account.jsp", type = "dispatcher"),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiHeadquarterAccountAction extends SimpleActionSupport {

	@Resource(name="fiHeadquarterAccountServiceImpl")
	private IFiHeadquarterAccountService fiHeadquarterAccountService;
	
	private FiHeadquarterAccount fiHeadquarterAccount;
	
	@Override
	protected Object createNewInstance() {
		return new FiHeadquarterAccount();
	}

	@Override
	public Map getContextMap() {
		// REVIEW Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiHeadquarterAccountService;
	}

	@Override
	public Object getModel() {
		return this.fiHeadquarterAccount;
	}

	@Override
	public void setModel(Object obj) {
		this.fiHeadquarterAccount=(FiHeadquarterAccount) obj;

	}
	
	
	
    /**
     * 出纳收款单核销
     * @return
     * @throws Exception
     */
    public String verification() throws Exception{
    	try{
    		if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
    			Map<String,Object> requestMap=new HashMap<String, Object>();
    			User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
				this.fiHeadquarterAccountService.verification(fiHeadquarterAccount,user);
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
    
    
    /**
     * 作废
     * @return
     * @throws Exception
     */
	public String revocation() throws Exception {
		try {
			Long id=Long.valueOf(Struts2Utils.getParameter("id"));
    		if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				this.fiHeadquarterAccountService.revocation(id);
				this.getValidateInfo().setSuccess(true);
    			this.getValidateInfo().setMsg("数据保存成功！");
        		addMessage("数据保存成功！");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"撤销失败!失败原因：" + e.getLocalizedMessage());
			logger.error(e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
	}
    

	/**
	 * 在save()前执行二次绑定.
	 */
	public void prepareVerification() throws Exception {
		prepareModel();
	}
}
