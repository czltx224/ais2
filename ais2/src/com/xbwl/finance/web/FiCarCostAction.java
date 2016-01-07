package com.xbwl.finance.web;

import java.util.HashMap;
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

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiOutcost;
import com.xbwl.entity.OprSignRoute;
import com.xbwl.oper.stock.service.IOprSignRouteService;

@Controller
@Action("fiCarCostAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiCarCostManage.jsp", type = "dispatcher"),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiCarCostAction extends SimpleActionSupport {

	@Resource(name="oprSignRouteServiceImpl")
	private IOprSignRouteService oprSignRouteService;
	
	private OprSignRoute oprSignRoute;
	
	@Element(value = OprSignRoute.class)
	private List<OprSignRoute> aa;
	
	private String ts ;

	/**
	 * 取消会计审核
	 * @return
	 */
	public String qxFiAudit(){
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), null, getContextMap())){
	        		oprSignRouteService.qxFiAudit(getId(),ts);
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
	
	/**
	 * 会计审核
	 * @return
	 * 
	 */
	public String fiAudit(){
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), null, getContextMap())){
	        		oprSignRouteService.fiAuditByName(aa);
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
	
	/**
	 * 车队审核（多条一起审核）
	 * @return
	 * 
	 */
	public String carAudit(){
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), null, getContextMap())){
	        		oprSignRouteService.carAuditByName(aa);
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
	
	@Override
	protected Object createNewInstance() {
		return new OprSignRoute();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return oprSignRouteService;
	}

	@Override
	public Object getModel() {
		return oprSignRoute;
	}

	@Override
	public void setModel(Object obj) {
		oprSignRoute=(OprSignRoute)obj;
	}
	
	public String transit(){
		return "transit";
	}
	
	
	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public List<OprSignRoute> getAa() {
		return aa;
	}

	public void setAa(List<OprSignRoute> aa) {
		this.aa = aa;
	}

}
