package com.xbwl.finance.web;

import java.io.File;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.bean.ValidateInfoExcel;
import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiCashiercollectionExcel;
import com.xbwl.finance.Service.IFiCapitaaccountService;
import com.xbwl.finance.Service.IFiCashiercollectionExcelService;

/**
 * author shuw
 * time Nov 15, 2011 10:03:58 AM
 */
@Controller
@Action("fiCashiercollectionExcelAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiCashiercollection.jsp", type = "dispatcher"),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiCashiercollectionExcelAction extends SimpleActionSupport{
	
	private FiCashiercollectionExcel fiCashiercollectionExcel;
	
	@Resource(name="fiCashiercollectionExcelServiceImpl")
	private IFiCashiercollectionExcelService fiCapitaaccountExcelService;

	private String upLoadExcelFileName;
	private File upLoadExcel;

	public String saveExcel(){
		ValidateInfoExcel validateInfoExcel=new  ValidateInfoExcel();
		String batchNo=null;
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
	        		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
	        		if (upLoadExcel==null) {
						throw new ServiceException("请选择需要导入的Excel文件");
					}else{
						batchNo=fiCapitaaccountExcelService.saveFiExcel(upLoadExcel, upLoadExcelFileName);
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
	        	validateInfoExcel.setMsg(e.getMessage());
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
	
	@Override
	protected Object createNewInstance() {
		return new FiCashiercollectionExcel();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiCapitaaccountExcelService;
	}

	@Override
	public Object getModel() {
		return fiCashiercollectionExcel;
	}

	@Override
	public void setModel(Object obj) {
		fiCashiercollectionExcel=(FiCashiercollectionExcel)obj;
	}  
	
	
}
