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

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiReceivablestatement;
import com.xbwl.finance.Service.IFiReceivablestatementService;

@Controller
@Action("fiReceivablestatementAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiReceivablestatement.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "reload", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class FiReceivablestatementAction extends SimpleActionSupport {

	@Resource(name = "fiReceivablestatementServiceImpl")
	private IFiReceivablestatementService fiReceivablestatementService;
	private FiReceivablestatement fiReceivablestatement;

	User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
	
	private String reconciliationNos;//ѡ�����ж��˵���
	
	@Override
	protected Object createNewInstance() {
		return new FiReceivablestatement();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiReceivablestatementService;
	}

	@Override
	public Object getModel() {
		return fiReceivablestatement;
	}

	@Override
	public void setModel(Object obj) {
		FiReceivablestatement fiReceivablestatement = (FiReceivablestatement) obj;

	}
	
	/**
	 * 
	* @Title: confirmReview 
	* @Description: ���˵����
	 */
	public String confirmReview() throws Exception{
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
	        		if(fiReceivablestatementService.isConfirmReview(reconciliationNos)){
	        			fiReceivablestatementService.confirmReview(reconciliationNos,user);
	        			this.getValidateInfo().setMsg("���˵���˳ɹ���");
		        		addMessage("���˵���˳ɹ���");
	        		}else{
	        			this.getValidateInfo().setSuccess(false);
	    	    		this.getValidateInfo().setMsg("����ʧ��:���˵�״̬����Ϊδ��ˣ�");
	        		}
	        	}else{
	        		this.getValidateInfo().setSuccess(false);
	        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        	}
	            
	        } catch (Exception e) {
	        	this.getValidateInfo().setSuccess(false);
	    		this.getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
	            addError("���ݱ���ʧ�ܣ�", e);
	        }
			return RELOAD;
		
	}
	
	/**
	 * 
	* @Title: revocationReview 
	* @Description: �������˵����
	 */
	public String revocationReview() throws Exception{
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
	        		//if(fiReceivablestatementService.isRevocationReview(reconciliationNos)){
	        			fiReceivablestatementService.revocationReview(reconciliationNos,user);
	        			this.getValidateInfo().setMsg("�������˵���˳ɹ���");
		        		addMessage("�������˵���˳ɹ���");
	        		//}else{
	        		//	this.getValidateInfo().setSuccess(false);
	    	    	//	this.getValidateInfo().setMsg("����ʧ��:���ֶ��˵�״̬��Ϊ����ˣ�");
	        	//	}
	        	}else{
	        		this.getValidateInfo().setSuccess(false);
	        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        	}
	            
	        } catch (Exception e) {
	        	this.getValidateInfo().setSuccess(false);
	    		this.getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
	            addError("���ݱ���ʧ�ܣ�", e);
	        }
		return RELOAD;
	}
	
	/**
	* @Title: ���˵�����
	 */
	public String invalid() throws Exception{
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
	        			fiReceivablestatementService.invalid(reconciliationNos);
	        			this.getValidateInfo().setMsg("���˵����ϳɹ���");
		        		addMessage("���˵����ϳɹ���");
	        	}else{
	        		this.getValidateInfo().setSuccess(false);
	        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        	}
	            
	        } catch (Exception e) {
	        	this.getValidateInfo().setSuccess(false);
	    		this.getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
	            addError("���ݱ���ʧ�ܣ�", e);
	        }
		return RELOAD;
	}
	
	/**
	 * �������˵�
	 * @return
	 * @throws Exception
	 */
	public String invalid1() throws Exception{
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
	        			fiReceivablestatementService.invalid(reconciliationNos);
	        			this.getValidateInfo().setMsg("���˵����ϳɹ���");
		        		addMessage("���˵����ϳɹ���");
	        	}else{
	        		this.getValidateInfo().setSuccess(false);
	        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        	}
	            
	        } catch (Exception e) {
	        	this.getValidateInfo().setSuccess(false);
	    		this.getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
	            addError("���ݱ���ʧ�ܣ�", e);
	        }
		return RELOAD;
	}

	public String getReconciliationNos() {
		return reconciliationNos;
	}

	public void setReconciliationNos(String reconciliationNos) {
		this.reconciliationNos = reconciliationNos;
	}
}
