package com.xbwl.finance.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.BasProjCusTransitRate;
import com.xbwl.finance.Service.IBasProjCusTransitRateService;

/**项目客户中转协议价控制层操作类
 * author CaoZhili
 * time Nov 29, 2011 9:37:48 AM
 */
@Controller
@Action("basProjCusTransitRateAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/bas_basProjCusTransitRate.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class BasProjCusTransitRateAction extends SimpleActionSupport{

	@Resource(name="basProjCusTransitRateServiceImpl")
	private IBasProjCusTransitRateService basProjCusTransitRateService;
	
	private Long piece;
	private String takeMode;
	private String areaType;
	private String trafficMode;
	private Long cusId;
	private Long cpId;
	private Double bulk;
	private Double weight;
	private Long disDepartId;
	private String town;
	
	private BasProjCusTransitRate basProjCusTransitRate;
	
	@Override
	protected Object createNewInstance() {
		return new BasProjCusTransitRate();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.basProjCusTransitRateService;
	}

	@Override
	public Object getModel() {
		return this.basProjCusTransitRate;
	}

	@Override
	public void setModel(Object obj) {
		this.basProjCusTransitRate=(BasProjCusTransitRate)obj;
	}
	
	/**
	 * @return the piece
	 */
	public Long getPiece() {
		return piece;
	}

	/**
	 * @param piece the piece to set
	 */
	public void setPiece(Long piece) {
		this.piece = piece;
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
	 * @return the areaType
	 */
	public String getAreaType() {
		return areaType;
	}

	/**
	 * @param areaType the areaType to set
	 */
	public void setAreaType(String areaType) {
		this.areaType = areaType;
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
	 * @return the cpId
	 */
	public Long getCpId() {
		return cpId;
	}

	/**
	 * @param cpId the cpId to set
	 */
	public void setCpId(Long cpId) {
		this.cpId = cpId;
	}

	/**
	 * @return the bulk
	 */
	public Double getBulk() {
		return bulk;
	}

	/**
	 * @param bulk the bulk to set
	 */
	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	/**
	 * @return the disDepartId
	 */
	public Long getDisDepartId() {
		return disDepartId;
	}

	/**
	 * @param disDepartId the disDepartId to set
	 */
	public void setDisDepartId(Long disDepartId) {
		this.disDepartId = disDepartId;
	}
	
	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String findProTraRate(){
		this.setPageConfig();
		try {
			basProjCusTransitRateService.findProTraRate(this.getPages(), piece, weight, bulk, cusId, cpId, areaType, takeMode, trafficMode,disDepartId,town);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
}
