package com.xbwl.finance.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.BasCqCorporateRate;
import com.xbwl.finance.Service.ICqCorporateRateService;

@Controller
@Action("cqCorporateRateAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
	@Result(name = "input", location = "/WEB-INF/xbwl/fi/bas_cqCorporateRate.jsp", type = "dispatcher"),	
	@Result(name = "reload", type = "json", params = { "root",
			"validateInfo", "excludeNullProperties", "true" }),
	@Result(name = "tree", type = "json", params = { "root", "areaList",
			"excludeNullProperties", "true" }),
	@Result(name = "list", type = "json", params = { "root", "pages",
			"includeProperties", "*" }) })
public class CqCorporateRateAction extends SimpleActionSupport {

	@Resource(name = "cqCorporateRateServiceImpl")
	private ICqCorporateRateService cqCorporateRateService;
	private BasCqCorporateRate cqCorporateRate;
	private String trafficMode;
	private String takeMode;
	private String city;
	private String town;
	private String street;
	private String valuationType;
	private String startCity;
	private Long cusId;
	private Long departId;
	private String distributionMode;
	private String addressType;
	
	private String upLoadExcelFileName;
	private File upLoadExcel;
	
	/**
	 * 导入Excle,保存协议价
	 * @return
	 */
	public String saveExcel(){
		try{
			cqCorporateRateService.saveExcelOfExcel(upLoadExcel,upLoadExcelFileName);
			getValidateInfo().setSuccess(true);
			getValidateInfo().setMsg("代理协议价保存成功");
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
	
	
	/**
	 * 伪删除方法
	 * @return
	 */
	public String deleteStatus(){
		
		String strids=ServletActionContext.getRequest().getParameter("ids");
		try{
			this.cqCorporateRateService.updateStatusService(strids.split(","), new Long(0));
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
			this.cqCorporateRateService.updateStatusService(strids.split(","), new Long(2));
		}catch(Exception e){
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("审核失败："+e.getLocalizedMessage());
		}
		return "reload";
	}
	/**
	 * 获得代理协议价格
	 * @return
	 */
	public String findRate(){
		setPageConfig();
		try {
			cqCorporateRateService.findCqCorRate(this.getPages(), trafficMode, takeMode, distributionMode, addressType, startCity, city, town, street, valuationType, cusId, departId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"查询失败,失败原因:" + e.getLocalizedMessage());
			return "reload";
		}
		return "list";
	}
	
	@Override
	protected Object createNewInstance() {
		return new BasCqCorporateRate();
	} 

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return cqCorporateRateService;
	}

	@Override
	public Object getModel() {
		return cqCorporateRate;
	}

	@Override
	public void setModel(Object obj) {
		cqCorporateRate = (BasCqCorporateRate) obj;
		
	}
	
	/**
	 * @return the trafficMode
	 */
	public String getTrafficMode() {
		return trafficMode;
	}

	/**
	 * @param trafficMode the trafficMode to set
	 */
	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
	}

	/**
	 * @return the takeMode
	 */
	public String getTakeMode() {
		return takeMode;
	}

	/**
	 * @param takeMode the takeMode to set
	 */
	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the town
	 */
	public String getTown() {
		return town;
	}

	/**
	 * @param town the town to set
	 */
	public void setTown(String town) {
		this.town = town;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the valuationType
	 */
	public String getValuationType() {
		return valuationType;
	}

	/**
	 * @param valuationType the valuationType to set
	 */
	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}

	/**
	 * @return the startCity
	 */
	public String getStartCity() {
		return startCity;
	}

	/**
	 * @param startCity the startCity to set
	 */
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	/**
	 * @return the cusId
	 */
	public Long getCusId() {
		return cusId;
	}

	/**
	 * @param cusId the cusId to set
	 */
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	/**
	 * @return the departId
	 */
	public Long getDepartId() {
		return departId;
	}

	/**
	 * @param departId the departId to set
	 */
	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	/**
	 * @return the distributionMode
	 */
	public String getDistributionMode() {
		return distributionMode;
	}

	/**
	 * @param distributionMode the distributionMode to set
	 */
	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	/**
	 * @return the addressType
	 */
	public String getAddressType() {
		return addressType;
	}

	/**
	 * @param addressType the addressType to set
	 */
	public void setAddressType(String addressType) {
		this.addressType = addressType;
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
