package com.xbwl.finance.web;

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
import com.xbwl.entity.BasSpecialTrainLine;
import com.xbwl.entity.ConsigneeInfo;
import com.xbwl.finance.Service.ICqCorporateRateService;
import com.xbwl.finance.Service.ISpecialTrainRateService;
import com.xbwl.finance.Service.ISpecialTrainLineService;
import com.xbwl.sys.service.IBasAreaService;
import com.xbwl.sys.service.IBasFlightService;
import com.xbwl.sys.service.IConInfoService;

@Controller
@Action("specialTrainRateAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class SpecialTrainRateAction extends SimpleActionSupport {

	@Resource(name = "specialTrainRateServiceImpl")
	private ISpecialTrainRateService specialTrainRateService;
	private BasSpecialTrainRate basSpecialTrainRate;
	
	private Long cusId;
	private String roadType;
	private String town;
	private String street;
	private String addrType;
	private Long departId;
	private String city;
	

	@Override
	protected Object createNewInstance() {
		return new BasSpecialTrainRate();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return specialTrainRateService;
	}

	@Override
	public Object getModel() {
		return basSpecialTrainRate;
	}

	@Override
	public void setModel(Object obj) {
		basSpecialTrainRate = (BasSpecialTrainRate) obj;
		
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
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String findRate(){
		try {
			setPageConfig();
			Page page=specialTrainRateService.findSpecialTrainRate(this.getPages(), cusId, roadType, town, street,departId,city);
			
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
}
