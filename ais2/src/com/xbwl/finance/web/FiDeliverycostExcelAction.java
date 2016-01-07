package com.xbwl.finance.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiDeliverycostExcel;
import com.xbwl.finance.Service.IFiDeliverycostExcelService;

/**
 * author shuw
 * time Oct 22, 2011 11:04:24 AM
 */
@Controller
@Action("fiDeliverycostExcelAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_Deliverycost.jsp", type = "dispatcher"),
		@Result(name = "show", location = "/WEB-INF/xbwl/fi/fi_deliverycost_hand_match.jsp", type = "dispatcher"),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiDeliverycostExcelAction extends SimpleActionSupport {

	private FiDeliverycostExcel fiDelivercostExcel;
	
	private String batchNo;
	private String cusName;
	private Long cusId;
	private Double totalMoney;
	private String code;
	
	@Resource(name = "fiDeliverycostExcelServiceImpl")
	private IFiDeliverycostExcelService fiDeliverycostExcelService; 
	
	
	public String auditFi(){
		 try {
	    		String returnString = fiDeliverycostExcelService.auditFi(batchNo,cusName,cusId,totalMoney,code);
	    		getValidateInfo().setMsg(returnString);
	    		getValidateInfo().setSuccess(true);
		  } catch (Exception e) {
		       	getValidateInfo().setSuccess(false);
		   		getValidateInfo().setMsg("审核失败！");
		        addError("数据审核失败！", e);
         }
		return RELOAD;
	}
	
	public String totalAmount(){
		try {
			setPageConfig();
			String sql  = fiDeliverycostExcelService.getTotalAmount(batchNo);
			Map map=new HashMap();
			map.put("batchNo", batchNo);
			fiDeliverycostExcelService.getPageBySqlMap(getPages(),sql,map);
			Page page = getPages();
			FiDeliverycostExcel oprVo = new FiDeliverycostExcel();
			List <Map>list =page.getResultMap();
			if(list.size()!=0){
				for(Map totalMap : list ){
					oprVo.setExcelAmount(totalMap.get("EXCELAMOUNT")==null?0:Double.parseDouble(totalMap.get("EXCELAMOUNT")+""));
				}
			}else{
				oprVo.setExcelAmount(0.0);
			}
			Struts2Utils.renderJson(oprVo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public String updateAllFax(){
		  try {
    		String returnString = fiDeliverycostExcelService.updateAllFax(batchNo);
    		getValidateInfo().setMsg(returnString);
    		getValidateInfo().setSuccess(true);
    		addMessage("修正成功！");   
	        } catch (Exception e) {
	        	getValidateInfo().setSuccess(false);
	    		getValidateInfo().setMsg("数据修正失败！");
	            addError("数据修正失败！", e);
	        }
		return RELOAD;
	}
	
	public String updateFax(){
		  try {
      		String returnString = fiDeliverycostExcelService.updateFax(batchNo);
      		getValidateInfo().setMsg(returnString);
      		getValidateInfo().setSuccess(true);
      		addMessage("修正成功！");   
	        } catch (Exception e) {
	        	getValidateInfo().setSuccess(false);
	    		getValidateInfo().setMsg("数据修正失败！");
	            addError("数据修正失败！", e);
	        }
		return RELOAD;
	}
	
	public String compareStatus(){
		  try {
        		String returnString = fiDeliverycostExcelService.compareStatus(batchNo);
        		getValidateInfo().setMsg(returnString);
        		getValidateInfo().setSuccess(true);
        		addMessage("数据对账成功！");   
	        } catch (Exception e) {
	        	getValidateInfo().setSuccess(false);
	    		getValidateInfo().setMsg("数据对账失败！");
	            addError("数据对账失败！", e);
	        }
		return RELOAD;
	}

	@Override
	protected Object createNewInstance() {
		return new FiDeliverycostExcel();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiDeliverycostExcelService;
	}

	@Override
	public Object getModel() {
		return fiDelivercostExcel;
	}

	@Override
	public void setModel(Object obj) {
		fiDelivercostExcel=(FiDeliverycostExcel)obj;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public FiDeliverycostExcel getFiDelivercostExcel() {
		return fiDelivercostExcel;
	}

	public void setFiDelivercostExcel(FiDeliverycostExcel fiDelivercostExcel) {
		this.fiDelivercostExcel = fiDelivercostExcel;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public Long getCusId() {
		return cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public IFiDeliverycostExcelService getFiDeliverycostExcelService() {
		return fiDeliverycostExcelService;
	}

	public void setFiDeliverycostExcelService(
			IFiDeliverycostExcelService fiDeliverycostExcelService) {
		this.fiDeliverycostExcelService = fiDeliverycostExcelService;
	}

}
