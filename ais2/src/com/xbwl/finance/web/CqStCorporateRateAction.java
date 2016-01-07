package com.xbwl.finance.web;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.BasCqStCorporateRate;
import com.xbwl.finance.Service.ICqStCorporateRateService;

@Controller
@Action("cqStCorporateRateAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/bas_cqStCorporateRate.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class CqStCorporateRateAction extends SimpleActionSupport {

	@Resource(name = "cqStCorporateRateServiceImpl")
	private ICqStCorporateRateService cqStCorporateRateService;
	
	private BasCqStCorporateRate cqStCorporateRate;
	
	private String trafficMode;
	private String takeMode;
	private String city;
	private String town;
	private String street;
	private String startCity;
	private Long departId;
	private String distributionMode;
	private String addressType;
	
	private Long cusId;
	private Double rebate;
	private String cusName;
	private Date startTime;
	private Date endTime;
	
	private String upLoadExcelFileName;
	private File upLoadExcel;

	/**
	 * 导入Excle,保存协议价
	 * @return
	 */
	public String saveExcel(){
		try{
			cqStCorporateRateService.saveExcelOfExcel(upLoadExcel,upLoadExcelFileName);
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
	
	
	/**
	 * 伪删除方法
	 * @return
	 */
	public String deleteStatus(){
		
		String strids=ServletActionContext.getRequest().getParameter("ids");
		try{
			this.cqStCorporateRateService.updateStatusService(strids.split(","), new Long(0));
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
			this.cqStCorporateRateService.updateStatusService(strids.split(","), new Long(2));
		}catch(Exception e){
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("审核失败："+e.getLocalizedMessage());
		}
		
		
		return "reload";
	}
	public String findRate(){
		try {
			setPageConfig();
			cqStCorporateRateService.findCqStCorRate(this.getPages(), trafficMode, takeMode, distributionMode, addressType, startCity, city, town, street, departId);
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
	
	public String discountCq(){
		try {
			List cqstId=super.getPksByIds();
			cqStCorporateRateService.discountCqRate(cqstId, cusId, cusName, rebate,startTime,endTime);
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setMsg(
					"操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"操作失败,失败原因:" + e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
	}
	
	@Override
	protected Object createNewInstance() {
		return new BasCqStCorporateRate();
	} 

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return cqStCorporateRateService;
	}

	@Override
	public Object getModel() {
		return cqStCorporateRate;
	}

	@Override
	public void setModel(Object obj) {
		cqStCorporateRate = (BasCqStCorporateRate) obj;
		
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
	 * @return the rebate
	 */
	public Double getRebate() {
		return rebate;
	}

	/**
	 * @param rebate the rebate to set
	 */
	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	/**
	 * @return the cusName
	 */
	public String getCusName() {
		return cusName;
	}

	/**
	 * @param cusName the cusName to set
	 */
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
