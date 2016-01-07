package com.xbwl.finance.web;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.BasTraShipmentRate;
import com.xbwl.entity.Customer;
import com.xbwl.finance.Service.IBasTraShipmentRateService;
import com.xbwl.sys.service.ICustomerService;

/**
 * @author CaoZhili time Aug 4, 2011 10:25:50 AM
 * 
 * 中转协议价控制层操作类
 */
@Controller
@Action("basTraShipmentRateAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/bas_traShipmentRate.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class BasTraShipmentRateAction extends SimpleActionSupport {

	private BasTraShipmentRate basTraShipmentRate;

	@Resource(name = "basTraShipmentRateServiceImpl")
	private IBasTraShipmentRateService basTraShipmentRateService;
	
	@Resource(name = "customerServiceImpl")
	private ICustomerService customerService;
	
	private String upLoadExcelFileName;
	private File upLoadExcel;
	
	/**
	 * 导入Excle,保存协议价
	 * @return
	 */
	public String saveExcel(){
		try{
			System.out.println(upLoadExcelFileName+"111111111111111111111111111");
			basTraShipmentRateService.saveExcelOfExcel(upLoadExcel,upLoadExcelFileName);
			getValidateInfo().setSuccess(true);
			getValidateInfo().setMsg("协议价保存成功");
			 Struts2Utils.render("text/html; charset=UTF-8",JSONObject.fromObject(getValidateInfo()).toString());
		}catch(Exception e){
			e.printStackTrace();
			getValidateInfo().setSuccess(false);
			getValidateInfo().setMsg("保存失败："+e.getLocalizedMessage());
			 Struts2Utils.render("text/html; charset=UTF-8",JSONObject.fromObject(getValidateInfo()).toString());
    		 return null;
		}
		return null;
	}
	
	@Override
	protected Object createNewInstance() {

		return new BasTraShipmentRate();
	}

	@Override
	public Map getContextMap() {
		Map map=new HashMap();
		map.put("authority",Struts2Utils.getParameter("authority"));
		return map;
	}

	@Override
	protected IBaseService getManager() {

		return this.basTraShipmentRateService;
	}

	@Override
	public Object getModel() {

		return this.basTraShipmentRate;
	}

	@Override
	public void setModel(Object obj) {

		this.basTraShipmentRate = (BasTraShipmentRate) obj;
	}
	
	/**
	 * 伪删除方法
	 * @return
	 */
	public String deleteStatus(){
		
		String strids=ServletActionContext.getRequest().getParameter("ids");
		try{
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				this.basTraShipmentRateService.updateStatusService(strids.split(","), new Long(0));
			}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		}catch(Exception e){
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("删除失败："+e.getLocalizedMessage());
		}
		return "reload";
	}
	
	/**
	 * 审核方法
	 * @return
	 */
	public String auditStatus(){
		
		String strids=ServletActionContext.getRequest().getParameter("ids");
		try{
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				this.basTraShipmentRateService.updateStatusService(strids.split(","), new Long(2));
			}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		}catch(Exception e){
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("审核失败："+e.getLocalizedMessage());
		}
		return "reload";
	}
	
	public String findShipmentCustomer(){
		setPageConfig();
		Page<Customer> pg;
		String cusName =  ServletActionContext.getRequest().getParameter("filter_LIKES_cusName");
		try {
			pg = this.customerService.gettraCustomer(this.getPages(),cusName);
			Struts2Utils.renderJson(pg);
		} catch (Exception e) {
			super.addError("查询客商失败！", e);
		}
		
		return this.LIST;
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
}
