package com.xbwl.finance.web;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.bean.ValidateInfoExcel;
import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiDeliverycost;
import com.xbwl.finance.Service.IFiDeliverycostService;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.vo.OprPrewiredVo;

/**
 * author shuw
 * time Oct 8, 2011 5:50:11 PM
 */
@Controller
@Action("fiDeliverycostAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_Deliverycost.jsp", type = "dispatcher"),
		@Result(name = "outExcel", location = "${deliverycost_excel_url}", type = "dispatcher"),
		@Result(name = "sexcel", location = "/WEB-INF/xbwl/fi/fi_DeliverycostExcel.jsp", type = "dispatcher"),  
		@Result(name = "show", location = "/WEB-INF/xbwl/fi/fi_deliverycost_hand_match.jsp", type = "dispatcher"),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiDeliverycostAction extends SimpleActionSupport{

	private FiDeliverycost fiDeliverycost;
	
	@Resource(name = "fiDeliverycostServiceImpl")
	private IFiDeliverycostService fiDeliverycostService;

	
	@Value("${deliverycost_excel_url}")
	private String deliverycostExcelUrl;
	
	@Value("${fiAuditCost.log_auditCost}")
	private Long log_auditCost ;
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	private String faxMainNo;
	private Long faxId;
	private Long faxPiece;
	private Double faxWeight;
	private String upLoadExcelFileName;
	private File upLoadExcel;
	private String ts;
	
	 @Element(value = FiDeliverycost.class)
		private List<FiDeliverycost> aa;
	
	/**
	 * ȡ���ֹ�ƥ��
	 * @return
	 */
	public String qxAduit(){
		try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		fiDeliverycostService.qxAudit(aa);
        		getValidateInfo().setMsg("���ݱ���ɹ���");
        		addMessage("���ݱ���ɹ���");
        	}else{
        		getValidateInfo().setSuccess(false);
        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
            
        } catch (Exception e) {
        	getValidateInfo().setSuccess(false);
    		getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
            addError("���ݱ���ʧ�ܣ�", e);
        }
		return RELOAD;
	}
	
	/**
	 * ����Excel
	 * @return
	 */
	public String saveExcel(){
		ValidateInfoExcel validateInfoExcel=new  ValidateInfoExcel();
		String batchNo=null;
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
	        		if (upLoadExcel==null) {
						throw new ServiceException("��ѡ����Ҫ�����Excel�ļ�");
					}else{
						batchNo=fiDeliverycostService.saveFiExcel(upLoadExcel, upLoadExcelFileName);
					}
	        	}else{
	        		validateInfoExcel.setSuccess(false);
	        		validateInfoExcel.setBatchNo(batchNo);
	        		validateInfoExcel.setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		Struts2Utils.render("text/html; charset=UTF-8",JSONObject.fromObject(validateInfoExcel).toString());
	        		 return null;
	        	}
	            
	        } catch (Exception e) {
	        	validateInfoExcel.setSuccess(false);
	        	validateInfoExcel.setMsg("����Excel���ɹ���");
	            addError("����Excel���ɹ�", e);
	            Struts2Utils.render("text/html; charset=UTF-8",JSONObject.fromObject(validateInfoExcel).toString());
       		 	return null;
	        }
	        validateInfoExcel.setSuccess(true);
	        validateInfoExcel.setBatchNo(batchNo);
	        validateInfoExcel.setMsg("����Excel�ɹ� �رյ������������ݣ�");
	        Struts2Utils.render("text/html; charset=UTF-8",JSONObject.fromObject(validateInfoExcel).toString());
	        return null;
	}
	
	/**
	 * �ֹ�ƥ��
	 * @return
	 */
	public String saveMat(){
		try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		fiDeliverycostService.saveMat(aa);
        		getValidateInfo().setMsg("���ݱ���ɹ���");
        		addMessage("���ݱ���ɹ���");
        	}else{
        		getValidateInfo().setSuccess(false);
        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
            
        } catch (Exception e) {
        	getValidateInfo().setSuccess(false);
    		getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
            addError("���ݱ���ʧ�ܣ�", e);
        }
		return RELOAD;
		
		
	}
	
	@Override
	public String save(){
        try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		fiDeliverycost.setStatus(0l);
        		fiDeliverycost.setMatStatus(0l);
        		fiDeliverycostService.save(fiDeliverycost);
        		getValidateInfo().setMsg("���ݱ���ɹ���");
        		addMessage("���ݱ���ɹ���");
        	}else{
        		getValidateInfo().setSuccess(false);
        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
            
        } catch (Exception e) {
        	getValidateInfo().setSuccess(false);
    		getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
            addError("���ݱ���ʧ�ܣ�", e);
        }
		return RELOAD;
	}

	
	/**
	 * ������
	 * @return
	 */
	public String fiAudit(){
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
	        		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
	        		fiDeliverycostService.saveFiAudit(aa,user);
	        		getValidateInfo().setMsg("���ݱ���ɹ���");
	        		addMessage("���ݱ���ɹ���");
	        	}else{
	        		getValidateInfo().setSuccess(false);
	        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        	}
	            
	        } catch (Exception e) {
	        	getValidateInfo().setSuccess(false);
	        	getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
	            addError("���ݱ���ʧ�ܣ�", e);
	        }
	        return RELOAD;
	}
	
	/**
	 * ȡ��������
	 * @return
	 */
	public String qxFiAudit(){
		try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
        		fiDeliverycostService.saveQxFiAudit(aa);
        		getValidateInfo().setMsg("���ݱ���ɹ���");
        		addMessage("���ݱ���ɹ���");
        	}else{
        		getValidateInfo().setSuccess(false);
        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
            
        } catch (Exception e) {
        	getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
            addError("���ݱ���ʧ�ܣ�", e);
        }
        return RELOAD;
	}
	
	/**
	 * ɾ������ɱ�����
	 * @return
	 * @throws Exception
	 */
	public String deleteData() throws Exception {
		 try {
			 if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
		            Long id = getId();
		            FiDeliverycost fiDeliverycost = fiDeliverycostService.get(id);
		            fiDeliverycost.setTs(ts);
		            fiDeliverycostService.delete(id);
		            addMessage("����ɾ���ɹ���");
		            getValidateInfo().setSuccess(true);
		        	getValidateInfo().setMsg("����ɾ���ɹ���");
			 }else{
	        		getValidateInfo().setSuccess(false);
	        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        	}
	        } catch (Exception e) {
	            addError("����ɾ��ʧ�ܣ�", e);
	            getValidateInfo().setSuccess(false);
	        	getValidateInfo().setMsg("����ɾ��ʧ�ܣ�");
	        }
	        return RELOAD;
	}

	public String show(){
		return "show";
	}
	
	@Override
	protected Object createNewInstance() {
		return new FiDeliverycost();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiDeliverycostService;
	}

	@Override
	public Object getModel() {
		return fiDeliverycost;
	}

	@Override
	public void setModel(Object obj) {
		fiDeliverycost=(FiDeliverycost)obj;
	}

	public String getFaxMainNo() {
		return faxMainNo;
	}

	public void setFaxMainNo(String faxMainNo) {
		this.faxMainNo = faxMainNo;
	}

	public Long getFaxPiece() {
		return faxPiece;
	}

	public void setFaxPiece(Long faxPiece) {
		this.faxPiece = faxPiece;
	}

	public Double getFaxWeight() {
		return faxWeight;
	}

	public void setFaxWeight(Double faxWeight) {
		this.faxWeight = faxWeight;
	}

	public String getUpLoadExcelFileName() {
		return upLoadExcelFileName;
	}

	public void setUpLoadExcelFileName(String upLoadExcelFileName) {
		this.upLoadExcelFileName = upLoadExcelFileName;
	}

	public File getUpLoadExcel() {
		return upLoadExcel;
	}

	public void setUpLoadExcel(File upLoadExcel) {
		this.upLoadExcel = upLoadExcel;
	}

	public String getDeliverycostExcelUrl() {
		return deliverycostExcelUrl;
	}

	public void setDeliverycostExcelUrl(String deliverycostExcelUrl) {
		this.deliverycostExcelUrl = deliverycostExcelUrl;
	}
 
	
	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String sexcel(){
		return "sexcel";
	}
	
	public String outExcel(){
		return "outExcel";
	}

	public Long getFaxId() {
		return faxId;
	}

	public void setFaxId(Long faxId) {
		this.faxId = faxId;
	}

	public List<FiDeliverycost> getAa() {
		return aa;
	}

	public void setAa(List<FiDeliverycost> aa) {
		this.aa = aa;
	}

	public Long getLog_auditCost() {
		return log_auditCost;
	}

	public void setLog_auditCost(Long log_auditCost) {
		this.log_auditCost = log_auditCost;
	}

	public IOprHistoryService getOprHistoryService() {
		return oprHistoryService;
	}

	public void setOprHistoryService(IOprHistoryService oprHistoryService) {
		this.oprHistoryService = oprHistoryService;
	}
}
