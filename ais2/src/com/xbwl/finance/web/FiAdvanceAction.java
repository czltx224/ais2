package com.xbwl.finance.web;

import java.util.List;
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
import com.xbwl.entity.FiAdvance;
import com.xbwl.finance.Service.IFiAdvanceService;
import com.xbwl.finance.Service.IFiAdvancesetServie;

@Controller
@Action("fiAdvanceAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiAdvance.jsp", type = "dispatcher"), 
		@Result(name = "tree", type = "json", params = { "root", "areaList","excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiAdvanceAction extends SimpleActionSupport {

	@Resource(name="fiAdvanceServiceImpl")
	private IFiAdvanceService fiAdvanceServie;
	
	private FiAdvance fiAdvance;
	private Long fiAdvanceId;
	

	@Override
	protected Object createNewInstance() {
		return new FiAdvance();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiAdvanceServie;
	}

	@Override
	public Object getModel() {
		return this.fiAdvance;
	}

	@Override
	public void setModel(Object obj) {
		this.fiAdvance=(FiAdvance) obj;
	}

	@Override
	public String save()  {
        try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		fiAdvanceServie.saveFiAdvance(fiAdvance);
        		getValidateInfo().setSuccess(true);
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
	
	public String deleteStatus(){
	     try {
	            fiAdvanceServie.deleteStatus(getId(),fiAdvanceId);
	            getValidateInfo().setMsg("数据保存成功！");
	            addMessage("数据作废成功！");
	            getValidateInfo().setSuccess(true);
	     } catch (Exception e) {
	            addError("数据作废失败！", e);
	            getValidateInfo().setSuccess(false);
	        	getValidateInfo().setMsg("数据保存失败！");
	    }
		return RELOAD;
	}

	
	public Long getFiAdvanceId() {
		return fiAdvanceId;
	}

	public void setFiAdvanceId(Long fiAdvanceId) {
		this.fiAdvanceId = fiAdvanceId;
	}
}
