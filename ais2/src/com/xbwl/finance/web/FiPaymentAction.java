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

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiArrearset;
import com.xbwl.entity.FiPayment;
import com.xbwl.finance.Service.IFiArrearsetService;
import com.xbwl.finance.Service.IFiPaymentService;

@Controller
@Action("fiPaymentAcion")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiPayment.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class FiPaymentAction extends SimpleActionSupport {

	@Resource(name = "fiPaymentServiceImpl")
	private IFiPaymentService fiPaymentService;
	private FiPayment fiPayment;

	private String ids;
	
	private String departId1;

	@Override
	protected Object createNewInstance() {
		return new FiPayment();
	}

	@Override
	public Map getContextMap() {
		Map map=new HashMap();
		map.put("audit", Struts2Utils.getParameter("audit"));//���
		map.put("departId1",departId1);
		return map;
	}

	@Override
	protected IBaseService getManager() {
		return fiPaymentService;
	}

	@Override
	public Object getModel() {
		return fiPayment;
	}

	@Override
	public void setModel(Object obj) {
		fiPayment = (FiPayment) obj;
	}

	/**
	 * 
	 * @Title: searchReceiving
	 * @Description: TODO(����ѡ��id���ҵ�ID��Ӧ�������͵�Ӧ�յ�������Page)
	 * @param ids��ʽ1,2,3,
	 */
	public String searchReceiving() throws Exception {
		try {
			this.setPageConfig();
			this.getPages().setPageSize(1000);
			this.getPages().setLimit(1000);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("ids",ServletActionContext.getRequest().getParameter("ids"));
			map.put("documentsSmalltype",ServletActionContext.getRequest().getParameter("documentsSmalltype"));//����С��

			Map map1=this.fiPaymentService.searchReceiving(map);
			Struts2Utils.renderJson(map1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"��ѯʧ��,ʧ��ԭ��:" + e.getLocalizedMessage());
			return "reload";
		}
		return null;
	}

	/**
	 * 
	 * @Title: searchReceiving
	 * @Description: TODO(����ѡ��id���ҵ�ID��Ӧ�������͵����յ�������Page)
	 * @param ids��ʽ1,2,3,
	 */
	public String searchPayment() throws Exception {
		try {
			this.setPageConfig();
			this.getPages().setPageSize(1000);
			this.getPages().setLimit(1000);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("ids",ServletActionContext.getRequest().getParameter("ids"));
			map.put("documentsSmalltype",ServletActionContext.getRequest().getParameter("documentsSmalltype"));//����С��
			this.fiPaymentService.searchPayment(this.getPages(), map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"��ѯʧ��,ʧ��ԭ��:" + e.getLocalizedMessage());
			return "reload";
		}
		return LIST;
	}
	
	/**
	 * 
	* @Description: TODO(�տ�ȷ�ϣ�����ѡ��id�������տ) 
	 */
	public String saveReceiving() throws Exception{
		try{
			User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("ids",ServletActionContext.getRequest().getParameter("ids"));
			map.put("penyJenis",ServletActionContext.getRequest().getParameter("penyJenis"));//�տʽ
			map.put("documentsSmalltype",ServletActionContext.getRequest().getParameter("documentsSmalltype"));//����С��
			map.put("selectIds", ServletActionContext.getRequest().getParameter("selectIds"));//�տ��˺�ID
			map.put("settlementAmount", ServletActionContext.getRequest().getParameter("settlementAmount"));//ʵ�����
			
			//֧Ʊ��Ϣ
			map.put("checkcustomerId", ServletActionContext.getRequest().getParameter("checkcustomerId"));
			map.put("checkNo", ServletActionContext.getRequest().getParameter("checkNo"));
			map.put("checkamount", ServletActionContext.getRequest().getParameter("checkamount"));
			map.put("checkDate", ServletActionContext.getRequest().getParameter("checkDate"));
			map.put("checkUser", ServletActionContext.getRequest().getParameter("checkUser"));
			map.put("checkRemark", ServletActionContext.getRequest().getParameter("checkRemark"));
			
			if (map.get("penyJenis").toString().equals("�ո��Գ�")){
				String[] ids=map.get("ids").toString().split(",");
				if(ids.length>1){
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg("�տ�ʧ�ܣ��ո�����һ��ֻ��ѡ��һ���տ�š�");
					return "reload";
				}
			}
			this.fiPaymentService.saveReceiving(map,user);
    		this.getValidateInfo().setMsg("�տ�ɹ���");
    		addMessage("�տ�ɹ���");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("�տ�ȷ��ʧ��,ʧ��ԭ��:" + e.getLocalizedMessage());
			return "reload";
			
		}
		return "reload";
	}

	/**
	 * 
	* @Description: TODO(����ȷ�ϣ�����ѡ��id�����渶�) 
	 */
	public String savePayment() throws Exception{
		try{
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("ids",ServletActionContext.getRequest().getParameter("ids"));//ѡ�еĸ��ID
			map.put("penyJenis",ServletActionContext.getRequest().getParameter("penyJenis"));//�տʽ
			map.put("documentsSmalltype",ServletActionContext.getRequest().getParameter("documentsSmalltype"));//����С��
			map.put("selectIds", ServletActionContext.getRequest().getParameter("selectIds"));//�տ��˺�ID
			map.put("settlementAmount", ServletActionContext.getRequest().getParameter("settlementAmount"));//����ʵ�տ���
			this.fiPaymentService.savePayment(map);
			this.getValidateInfo().setSuccess(true);
			this.getValidateInfo().setMsg("����ɹ�!");
			this.addMessage("����ɹ�!");
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("����ȷ��ʧ��,ʧ��ԭ��:"+e.getLocalizedMessage());
			return "reload";
		}
		return "reload";
	}
	
	/**
	 * 
	* @Description: TODO(ί���ո������ѡ��id������ί���ո��) 
	 */
	public String saveEntrust() throws Exception{
		try{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("ids",ServletActionContext.getRequest().getParameter("ids"));//ѡ�еĸ��ID
			map.put("documentsSmalltype",ServletActionContext.getRequest().getParameter("documentsSmalltype"));//����С��
			this.fiPaymentService.saveEntrust(map,this.fiPayment);
			this.getValidateInfo().setSuccess(true);
			this.getValidateInfo().setMsg("ί�гɹ�!");
			this.addMessage("ί�гɹ�!");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("ί��ʧ�ܣ�ʧ��ԭ��:"+e.getLocalizedMessage());
			return "reload";
		}
		return "reload";
	}
	
	/**
	 * ��saveEntrust()ǰִ�ж��ΰ�.
	 */
	public void prepareSaveEntrust() throws Exception {
      super.prepareModel();
	}
	
	//����
	public String saveLosses() throws Exception{
		try{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("ids",ServletActionContext.getRequest().getParameter("ids"));//ѡ�еĸ��ID
			map.put("customerId",ServletActionContext.getRequest().getParameter("customerId"));//���˿���id
			map.put("departId",ServletActionContext.getRequest().getParameter("departId"));//���˿��̲���
			map.put("remark",ServletActionContext.getRequest().getParameter("remark"));//��ע
			
			this.fiPaymentService.saveLosses(map);
			this.getValidateInfo().setSuccess(true);
			this.getValidateInfo().setMsg("���˳ɹ�!");
			this.addMessage("���˳ɹ�!");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("����ʧ�ܣ�ʧ��ԭ��:"+e.getLocalizedMessage());
			return "reload";
		}
		return "reload";
	}
	
	
	/**
	 * ���
	 * @return
	 * @throws Exception
	 */
	public String audit() throws Exception{
		try {
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				Long id=Long.valueOf(Struts2Utils.getParameter("id"));
				User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
				if("null".equals(id)||"".equals(id)||id==null){
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg("���ݲ�����!");
				}
				this.fiPaymentService.audit(id, user);//���
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("��˳ɹ�!");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"���ʧ��!ʧ��ԭ��" + e.getLocalizedMessage());
			logger.error(e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
	}
	
	
	/**
	 * ���
	 * @return
	 * @throws Exception
	 */
	public String revocationAudit() throws Exception{
		try {
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				Long id=Long.valueOf(Struts2Utils.getParameter("id"));
				User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
				if("null".equals(id)||"".equals(id)||id==null){
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg("���ݲ�����!");
				}
				this.fiPaymentService.revocationAudit(id, user);//�������
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("������˳ɹ�!");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"�������ʧ��!ʧ��ԭ��" + e.getLocalizedMessage());
			logger.error(e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
	}
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getDepartId1() {
		return departId1;
	}

	public void setDepartId1(String departId1) {
		this.departId1 = departId1;
	}
	

}
