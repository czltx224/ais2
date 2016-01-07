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
	 * 取消手工匹配
	 * @return
	 */
	public String qxAduit(){
		try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		fiDeliverycostService.qxAudit(aa);
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
	 * 导入Excel
	 * @return
	 */
	public String saveExcel(){
		ValidateInfoExcel validateInfoExcel=new  ValidateInfoExcel();
		String batchNo=null;
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
	        		if (upLoadExcel==null) {
						throw new ServiceException("请选择需要导入的Excel文件");
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
	        	validateInfoExcel.setMsg("导入Excel不成功！");
	            addError("导入Excel不成功", e);
	            Struts2Utils.render("text/html; charset=UTF-8",JSONObject.fromObject(validateInfoExcel).toString());
       		 	return null;
	        }
	        validateInfoExcel.setSuccess(true);
	        validateInfoExcel.setBatchNo(batchNo);
	        validateInfoExcel.setMsg("导入Excel成功 关闭弹出框后加载数据！");
	        Struts2Utils.render("text/html; charset=UTF-8",JSONObject.fromObject(validateInfoExcel).toString());
	        return null;
	}
	
	/**
	 * 手工匹配
	 * @return
	 */
	public String saveMat(){
		try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		fiDeliverycostService.saveMat(aa);
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
	public String save(){
        try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		fiDeliverycost.setStatus(0l);
        		fiDeliverycost.setMatStatus(0l);
        		fiDeliverycostService.save(fiDeliverycost);
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
	 */
	public String fiAudit(){
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
	        		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
	        		fiDeliverycostService.saveFiAudit(aa,user);
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
	 * 取消会计审核
	 * @return
	 */
	public String qxFiAudit(){
		try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
        		fiDeliverycostService.saveQxFiAudit(aa);
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
	 * 删除提货成本数据
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
		            addMessage("数据删除成功！");
		            getValidateInfo().setSuccess(true);
		        	getValidateInfo().setMsg("数据删除成功！");
			 }else{
	        		getValidateInfo().setSuccess(false);
	        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        	}
	        } catch (Exception e) {
	            addError("数据删除失败！", e);
	            getValidateInfo().setSuccess(false);
	        	getValidateInfo().setMsg("数据删除失败！");
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
