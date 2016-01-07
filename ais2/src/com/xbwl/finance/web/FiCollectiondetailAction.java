package com.xbwl.finance.web;

import java.util.Date;
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
import com.xbwl.entity.FiCollectiondetail;
import com.xbwl.finance.Service.IFiCollectiondetailService;

@Controller
@Action("fiCollectiondetailAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiCollectiondetail.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })	
public class FiCollectiondetailAction extends SimpleActionSupport {

	@Resource(name="fiCollectiondetailServiceImpl")
	private IFiCollectiondetailService fiCollectiondetailService;
	private FiCollectiondetail fiCollectiondetail;
	
	private Date stateDate;// ���ݿ�ʼʱ��
	private Date endDate;// ���ݽ���ʱ��
	private Long billingCycle;// ��������
	private Long customerId;// ����ID
	private Long createDeptid;// ��������ID
	
	@Override
	protected Object createNewInstance() {
		return new FiCollectiondetail();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiCollectiondetailService;
	}

	@Override
	public Object getModel() {
		return fiCollectiondetail;
	}

	@Override
	public void setModel(Object obj) {
		this.fiCollectiondetail=(FiCollectiondetail) obj;

	}
	
	/**
	 * 
	* @Title: saveCollectionstatement 
	* @Description: TODO(���ջ������ɶ��˵�) 
	* @param @return    ��ѯmap
	* @return String    �������� 
	* @throws
	 */
	public String saveCollectionstatement(){
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
	        		Map<String,Object> searchmap=new HashMap<String,Object>();
	        		searchmap.put("stateDate", stateDate);
	        		searchmap.put("endDate", endDate);
	        		searchmap.put("billingCycle", billingCycle);
	        		searchmap.put("customerId", customerId);
	        		searchmap.put("createDeptid", createDeptid);
	        		System.out.println("createDeptid="+createDeptid);
	        		this.fiCollectiondetailService.saveCollectionstatement(searchmap, this.getPages(), this.getValidateInfo());
	        		this.getValidateInfo().setSuccess(true);
	        		this.getValidateInfo().setMsg("���˵����ɳɹ�!");
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

	public Date getStateDate() {
		return stateDate;
	}

	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(Long billingCycle) {
		this.billingCycle = billingCycle;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getCreateDeptid() {
		return createDeptid;
	}

	public void setCreateDeptid(Long createDeptid) {
		this.createDeptid = createDeptid;
	}


	
	
}
