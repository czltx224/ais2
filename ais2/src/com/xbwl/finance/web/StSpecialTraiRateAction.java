package com.xbwl.finance.web;

import java.util.Date;
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
import com.xbwl.entity.BasCqCorporateRate;
import com.xbwl.entity.BasFlight;
import com.xbwl.entity.BasSpecialTrainRate;
import com.xbwl.entity.BasStSpecialTrainRate;
import com.xbwl.entity.ConsigneeInfo;
import com.xbwl.finance.Service.ICqCorporateRateService;
import com.xbwl.finance.Service.ISpecialTrainRateService;
import com.xbwl.finance.Service.IStSpecialTrainRateService;
import com.xbwl.sys.service.IBasAreaService;
import com.xbwl.sys.service.IBasFlightService;
import com.xbwl.sys.service.IConInfoService;

@Controller
@Action("stSpecialTrainRateAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class StSpecialTraiRateAction extends SimpleActionSupport {

	@Resource(name = "stSpecialTrainRateServiceImpl")
	private IStSpecialTrainRateService stSpecialTrainRateService;
	private BasStSpecialTrainRate basStSpecialTraiRate;
	
	private String roadType;
	private String town;
	private String street;
	private String addrType;
	private Long departId;
	private String city;
	
	private Double rebate;
	private Date startTime;
	private Date endTime;
	private Long cusId;
	private String cusName;

	@Override
	protected Object createNewInstance() {
		return new BasStSpecialTrainRate();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return stSpecialTrainRateService;
	}

	@Override
	public Object getModel() {
		return basStSpecialTraiRate;
	}

	@Override
	public void setModel(Object obj) {
		basStSpecialTraiRate = (BasStSpecialTrainRate) obj;
		
	}
	
	/**
	 * @return the roadType
	 */
	public String getRoadType() {
		return roadType;
	}

	/**
	 * @param roadType the roadType to set
	 */
	public void setRoadType(String roadType) {
		this.roadType = roadType;
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
	 * @return the addrType
	 */
	public String getAddrType() {
		return addrType;
	}

	/**
	 * @param addrType the addrType to set
	 */
	public void setAddrType(String addrType) {
		this.addrType = addrType;
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
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String findRate(){
		try {
			setPageConfig();
			Page page=stSpecialTrainRateService.findStSpecialTrainRate(this.getPages(), roadType, town, street,departId,city);
			this.setPages(page);
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
	
	public String discountSpe(){
		try {
			List cqstId=super.getPksByIds();
			stSpecialTrainRateService.discountSpeRate(cqstId, cusId, cusName, rebate);
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
}
