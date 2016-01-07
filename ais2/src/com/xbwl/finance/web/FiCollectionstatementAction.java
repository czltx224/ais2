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
import com.xbwl.entity.FiCollectionstatement;
import com.xbwl.finance.Service.IFiCollectionstatementService;

@Controller
@Action("fiCollectionstatementAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiCollectionstatement.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })	
public class FiCollectionstatementAction extends SimpleActionSupport {

	@Resource(name="fiCollectionstatementServiceImpl")
	private IFiCollectionstatementService fiCollectionstatementService;
	private FiCollectionstatement fiCollectionstatement;
	
	private String reconciliationNos;//ѡ�����ж��˵���
	
	User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
	
	@Override
	protected Object createNewInstance() {
		return new FiCollectionstatement();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiCollectionstatementService;
	}

	@Override
	public Object getModel() {
		return fiCollectionstatement;
	}

	@Override
	public void setModel(Object obj) {
		this.fiCollectionstatement=(FiCollectionstatement) obj;

	}

	/**
	 * 
	* @Title: confirmReview 
	* @Description: TODO(���˵����) 
	* @param @return
	* @param @throws Exception    �趨�ļ� 
	* @return String    �������� 
	* @throws
	 */
	public String confirmReview() throws Exception{
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
	        		if(this.fiCollectionstatementService.isConfirmReview(reconciliationNos)){
	        			this.fiCollectionstatementService.confirmReview(reconciliationNos,user);
	        			this.getValidateInfo().setMsg("���ݱ���ɹ���");
		        		addMessage("���ݱ���ɹ���");
	        		}else{
	        			this.getValidateInfo().setSuccess(false);
	    	    		this.getValidateInfo().setMsg("����ʧ��:���ֶ��˵�״̬��Ϊδ��ˣ�");
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

	public String getReconciliationNos() {
		return reconciliationNos;
	}

	public void setReconciliationNos(String reconciliationNos) {
		this.reconciliationNos = reconciliationNos;
	}
	
	
}
