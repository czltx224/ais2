package com.xbwl.finance.web;

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

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiCheck;
import com.xbwl.finance.Service.IFiCheckService;

@Controller
@Action("fiCheckAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_check.jsp", type = "dispatcher"),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiCheckAction extends SimpleActionSupport {

	@Resource(name = "fiCheckServiceImpl")
	private IFiCheckService fiCheckService;
	private FiCheck fiCheck;
	private String ts;

	public String save() throws Exception {
		try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		if(fiCheck!=null&&fiCheck.getId()!=null){
        			if(ts!=null){
        				if(!ts.equals(fiCheck.getTs())){
        					throw new ServiceException("数据状态已改变，请查询后再操作");
        				}
        			}
        		}
        		getManager().save(fiCheck);
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

	public String deleteByStatus(){
		try {
			if (WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(),getModel(), getContextMap())) {
				fiCheckService.deleteByStatus(getId(), ts);
				getValidateInfo().setMsg("数据保存成功！");
				addMessage("数据保存成功！");
			} else {
				getValidateInfo().setSuccess(false);
				getValidateInfo().setMsg(
						WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
				addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			}
		} catch (Exception e) {
			getValidateInfo().setSuccess(false);
			getValidateInfo().setMsg("数据保存失败！");
			addError("数据保存失败！", e);
		}
		return RELOAD;
	}
	
	//到账确认
	public String checkAudit(){
		try {
			if (WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(),getModel(), getContextMap())) {
				this.fiCheckService.checkAudit(getId(), ts);
				getValidateInfo().setMsg("数据保存成功！");
				addMessage("数据保存成功！");
			} else {
				getValidateInfo().setSuccess(false);
				getValidateInfo().setMsg(
						WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
				addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			}
		} catch (Exception e) {
			getValidateInfo().setSuccess(false);
			getValidateInfo().setMsg("数据保存失败！");
			addError("数据保存失败！", e);
		}
		return RELOAD;
	}
	
	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	
	@Override
	protected Object createNewInstance() {
		return new FiCheck();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiCheckService;
	}

	@Override
	public Object getModel() {
		return this.fiCheck;
	}

	@Override
	public void setModel(Object obj) {
		this.fiCheck = (FiCheck) obj;
	}

}
