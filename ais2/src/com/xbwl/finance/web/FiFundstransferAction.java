package com.xbwl.finance.web;

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
import com.xbwl.entity.FiFundstransfer;
import com.xbwl.finance.Service.IFiFundstransferService;

@Controller
@Action("fiFundstransferAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiFundstransfer.jsp", type = "dispatcher"),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })	
public class FiFundstransferAction extends SimpleActionSupport {

	@Resource(name="fiFundstransferServiceImpl")
	private IFiFundstransferService fiFundstransferService;
	private FiFundstransfer fiFundstransfer;
	@Override
	protected Object createNewInstance() {
		return new FiFundstransfer();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiFundstransferService;
	}

	@Override
	public Object getModel() {
		return this.fiFundstransfer;
	}

	@Override
	public void setModel(Object obj) {
		this.fiFundstransfer=(FiFundstransfer) obj;

	}
	
	/**
	 * ����ȷ��
	 * @return
	 * @throws Exception
	 */
	public String paymentConfirmation() throws Exception{
		try {
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				Long id=Long.valueOf(Struts2Utils.getParameter("id"));
				User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
				if("null".equals(id)||"".equals(id)||id==null){
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg("���ӵ��Ų�����!");
				}
				this.fiFundstransferService.paymentConfirmation(id, user);
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("����ȷ�ϳɹ�!");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"����ȷ��ʧ��!ʧ��ԭ��" + e.getLocalizedMessage());
			logger.error(e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
	}
	
	/**
	 * �����
	 * @return
	 * @throws Exception
	 */
	public String paymentRevoke() throws Exception{
		try {
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				Long id=Long.valueOf(Struts2Utils.getParameter("id"));
				User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
				if("null".equals(id)||"".equals(id)||id==null){
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg("���ӵ��Ų�����!");
				}
				this.fiFundstransferService.paymentRevoke(id, user);
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("����ȷ�ϳɹ�!");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"����ȷ��ʧ��!ʧ��ԭ��" + e.getLocalizedMessage());
			logger.error(e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
	}
	
	/**
	 * �տ�ȷ��
	 * @return
	 * @throws Exception
	 */
	public String receivablesConfirmation() throws Exception{
		try {
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				Long id=Long.valueOf(Struts2Utils.getParameter("id"));
				User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
				if("null".equals(id)||"".equals(id)||id==null){
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg("���ӵ��Ų�����!");
				}
				this.fiFundstransferService.receivablesConfirmation(id, user);
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("����ȷ�ϳɹ�!");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"����ȷ��ʧ��!ʧ��ԭ��" + e.getLocalizedMessage());
			logger.error(e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
	}
	
	//����
	public String revocation() throws Exception{
		try {
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				Long id=Long.valueOf(Struts2Utils.getParameter("id"));
				User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
				if("null".equals(id)||"".equals(id)||id==null){
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg("���ӵ��Ų�����!");
				}
				this.fiFundstransferService.revocation(id);
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("���ϳɹ�!");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"����ʧ��!ʧ��ԭ��" + e.getLocalizedMessage());
			logger.error(e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
	}
}
