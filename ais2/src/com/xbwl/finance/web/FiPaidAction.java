package com.xbwl.finance.web;

import java.util.HashMap;
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
import com.xbwl.entity.FiPaid;
import com.xbwl.finance.Service.IFiPaidService;

@Controller
@Action("fiPaidAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiPaid.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class FiPaidAction extends SimpleActionSupport {

	@Resource(name = "fiPaidServiceImpl")
	private IFiPaidService fiPaidService;
	private FiPaid fiPaid;
	
	private Long accountId; 

	@Override
	protected Object createNewInstance() {
		return new FiPaid();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiPaidService;
	}

	@Override
	public Object getModel() {
		return this.fiPaid;
	}

	@Override
	public void setModel(Object obj) {
		this.fiPaid = (FiPaid) obj;
	}

	/**
	 * 
	 * @Title: revocation
	 * @Description: TODO(�����ո������)
	 * @param ʵ�ո�����
	 */
	public String revocation() throws Exception {
		try {
			Long id=Long.valueOf(Struts2Utils.getParameter("id"));
			String returninfo = this.fiPaidService.revocation(id);
			if (!returninfo.equals("true")) {
				super.getValidateInfo().setSuccess(false);
				super.getValidateInfo().setMsg("����ʧ��!ʧ��ԭ��:" + returninfo);
			} else {
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("�����ɹ�!");
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
	
	//�������
	public String paymentVerification() throws Exception{
		try {
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				String ids=Struts2Utils.getParameter("ids");
				User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
				this.fiPaidService.paymentVerification(ids, user);
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("�����ɹ�!");
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
	
	//�ʽ��Ͻ����ύ
	public String handInConfirmation() throws Exception{
		try {
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				String paidIds=Struts2Utils.getParameter("paidIds");
				String receivablesaccountId=Struts2Utils.getParameter("receivablesaccountId");//�տ��˺�ID
				String receivablesaccountDeptid=Struts2Utils.getParameter("receivablesaccountDeptid");//�տ��ID
				String receivablesaccountDept=Struts2Utils.getParameter("receivablesaccountDept");//�տ��
				String paymentaccountId=Struts2Utils.getParameter("paymentaccountId");//�����˺�ID
				String remark=Struts2Utils.getParameter("remark");//���ע
				String settlementAmount=Struts2Utils.getParameter("settlementAmount");//δ�Ͻ��ܽ��
				String sumAmount=Struts2Utils.getParameter("sumAmount");//�Ͻ����
				User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
				Map map=new HashMap<String,Object>();
				map.put("paidIds", paidIds);
				map.put("receivablesaccountId", receivablesaccountId);
				map.put("receivablesaccountDeptid", receivablesaccountDeptid);
				map.put("receivablesaccountDept",receivablesaccountDept);
				map.put("paymentaccountId", paymentaccountId);
				map.put("remark", remark);
				map.put("settlementAmount", settlementAmount);
				map.put("sumAmount",sumAmount);
				this.fiPaidService.handInConfirmation(map, user);
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("�ʽ��Ͻ��ɹ�!");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"�ʽ��Ͻ�ʧ��!ʧ��ԭ��" + e.getLocalizedMessage());
			logger.error(e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
	}	
	//�ʽ��Ͻ�ʱ���Ͻ��ܽ���ѯ
	public String searchHandInAmount() throws Exception{
		try {
			this.setPageConfig();
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("paidIds",ServletActionContext.getRequest().getParameter("paidIds"));
			Map map1=this.fiPaidService.searchHandInAmount(map);
			Struts2Utils.renderJson(map1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("��ѯʧ��,ʧ��ԭ��:" + e.getLocalizedMessage());
			return "reload";
		}
		return null;
	}
	
	public String findAccountSingle() throws Exception{
        try {
        	this.setPageConfig();
        	Map map=new HashMap<String,Object>();
			map.put("accountId",accountId);
        	this.fiPaidService.findAccountSingle(this.getPages(), map);
        } catch (Exception e) {
            addError("���ݲ�ѯʧ�ܣ�", e);
        }
        return LIST;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	

	
}
