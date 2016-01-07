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
import com.xbwl.entity.BasProjectRate;
import com.xbwl.finance.Service.IBasProjectRateService;
 
/**
 * @author CaoZhili time Aug 10, 2011 10:47:00 AM
 * 
 * 项目客户协议价控制层操作类
 */
@Controller
@Action("basProjectRateAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/bas_projectRate.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class BasProjectRateAction extends SimpleActionSupport {

	private BasProjectRate basProjectRate;

	@Resource(name = "basProjectRateServiceImpl")
	private IBasProjectRateService basProjectRateService;
	private Long piece;
	private Double weight;
	private Double bulk;
	private Long cusId;
	private String city;
	private String town;
	@Override
	protected Object createNewInstance() {

		return new BasProjectRate();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.basProjectRateService;
	}

	@Override
	public Object getModel() {

		return this.basProjectRate;
	}

	@Override
	public void setModel(Object obj) {

		this.basProjectRate = (BasProjectRate) obj;
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
	
	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String findRate(){
		try {
			setPageConfig();
			basProjectRateService.findProRate(this.getPages(), piece, weight, bulk, cusId,city,town);
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
