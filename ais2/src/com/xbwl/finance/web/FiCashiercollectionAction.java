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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiCashiercollection;
import com.xbwl.finance.Service.IFiCashiercollectionService;

@Controller
@Action("fiCashiercollectionAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiCashiercollection.jsp", type = "dispatcher"),
		@Result(name = "verReport", location = "/WEB-INF/xbwl/fi/fiCashiercollection_verReport.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class FiCashiercollectionAction extends SimpleActionSupport {
	
	@Resource(name="fiCashiercollectionServiceImpl")
	private IFiCashiercollectionService fiCashiercollectionService;
	
	private FiCashiercollection fiCashiercollection;
	
	@Value("${cash_excel_url}")
	private String  cashExcelUrl;
	
	@Value("${pos_excel_url}")
	private String  posExcelUrl;
	
	private Long batchNo;

	//���뱣��
	public String saveExcelData(){
		try{
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				this.fiCashiercollectionService.saveExcelData(batchNo);
    			this.getValidateInfo().setMsg("���ݱ���ɹ���");
        		addMessage("���ݱ���ɹ���");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		}catch(Exception e){
        	this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
            addError("���ݱ���ʧ�ܣ�", e);
		}
		return RELOAD;
	}
	
	//����POS����
	public String saveExcelPosData() throws Exception{
		try{
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				this.fiCashiercollectionService.saveExcelPosData(batchNo);
    			this.getValidateInfo().setMsg("���ݱ���ɹ���");
        		addMessage("���ݱ���ɹ���");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		}catch(Exception e){
        	this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
            addError("���ݱ���ʧ�ܣ�", e);
		}
		return RELOAD;
	}
	
	//��������տ
	public String saveCashiercollection() throws Exception{
		try{
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				this.fiCashiercollectionService.saveCashiercollection(fiCashiercollection);
    			this.getValidateInfo().setMsg("���ݱ���ɹ���");
        		addMessage("���ݱ���ɹ���");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		}catch(Exception e){
        	this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
            addError("���ݱ���ʧ�ܣ�", e);
		}
		return RELOAD;
	}

    /**
     * ��������տǰ���ΰ�
     * @throws Exception
     */
    public void prepareSaveCashiercollection() throws Exception {
    	prepareModel();
    }
    
    /**
     * �����տ����
     * @return
     * @throws Exception
     */
    public String saveVerification() throws Exception{
    	try{
    		if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
    			Map<String,Object> requestMap=new HashMap<String, Object>();
    			User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
    			requestMap.put("verificationType", Struts2Utils.getRequest().getParameter("verificationType"));
    			requestMap.put("verificationRemark", Struts2Utils.getRequest().getParameter("verificationRemark"));
    			requestMap.put("ids", Struts2Utils.getRequest().getParameter("ids"));
    			requestMap.put("fiCashiercollectionId", Struts2Utils.getRequest().getParameter("fiCashiercollectionId"));
				this.fiCashiercollectionService.saveVerification(requestMap,user);
				this.getValidateInfo().setSuccess(true);
    			this.getValidateInfo().setMsg("���ݱ���ɹ���");
        		addMessage("���ݱ���ɹ���");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
    	}catch(Exception e){
    		this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("���ݱ���ʧ��");
    		addError("���ݱ���ʧ��!", e);
    	}
    	return RELOAD;
    }
    
    //���ϳ����տ
    public String invalidCashiercollection() throws Exception{
    	try{
    		if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				this.fiCashiercollectionService.invalidCashiercollection(fiCashiercollection);
				this.getValidateInfo().setSuccess(true);
    			this.getValidateInfo().setMsg("���ϳɹ���");
        		addMessage("���ϳɹ���");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
    	}catch(Exception e){
    		this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("����ʧ��");
    		addError("����ʧ��!", e);
    	}
    	return RELOAD;
    }
    
    public String manualVerification() throws Exception{
    	try{
    		if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
    			Map<String,Object> requestMap=new HashMap<String, Object>();
    			User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
    			requestMap.put("id", Struts2Utils.getRequest().getParameter("id"));
    			requestMap.put("verificationRemark", Struts2Utils.getRequest().getParameter("verificationRemark"));
				this.fiCashiercollectionService.manualVerification(requestMap,user);
				this.getValidateInfo().setSuccess(true);
    			this.getValidateInfo().setMsg("�ֹ������ɹ���");
        		addMessage("�ֹ������ɹ���");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
    	}catch(Exception e){
    		this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("�ֹ�����ʧ��");
    		addError("���ݱ���ʧ��!", e);
    	}
    	return RELOAD;
    }
    
    /**
     * �༭ʵ��
     * @return
     * @throws Exception
     */
    public String verReport() throws Exception {
        return "verReport";
    }
    
	/**
	 * ��save()ǰִ�ж��ΰ�.
	 */
	public void prepareInvalidCashiercollection() throws Exception {
		prepareModel();
	}
    
	public Long getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}
    
	public String getCashExcelUrl() {
		return cashExcelUrl;
	}

	public void setCashExcelUrl(String cashExcelUrl) {
		this.cashExcelUrl = cashExcelUrl;
	}
	

	public String getPosExcelUrl() {
		return posExcelUrl;
	}

	public void setPosExcelUrl(String posExcelUrl) {
		this.posExcelUrl = posExcelUrl;
	}

	@Override
	protected Object createNewInstance() {
		return new FiCashiercollection();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.fiCashiercollectionService;
	}

	@Override
	public Object getModel() {
		return this.fiCashiercollection;
	}

	@Override
	public void setModel(Object obj) {
		this.fiCashiercollection=(FiCashiercollection) obj;

	}
}
