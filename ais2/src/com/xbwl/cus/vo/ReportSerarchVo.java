package com.xbwl.cus.vo;

import java.util.Date;

/**
 * �����ѯVO
 *@author LiuHao
 *@time May 16, 2012 4:03:15 PM
 */
public class ReportSerarchVo {
	private Long departId;//����
	private Long cusId;//����ID
	private String trafficMode;//���䷽ʽ
	private String productType;//��Ʒ����
	private String endCity;//Ŀ��վ
	private Date startDate;
	private Date endDate;
	
	private String departScope;
	private String cusScope;
	private String trafficModeScope;
	private String productTypeScope;
	private String endCityScope;
	
	private String startCount;
	private String endCount;
	private String countRange;
	private String countType;
	
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	public Long getCusId() {
		return cusId;
	}
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}
	public String getTrafficMode() {
		return trafficMode;
	}
	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getEndCity() {
		return endCity;
	}
	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDepartScope() {
		return departScope;
	}
	public void setDepartScope(String departScope) {
		this.departScope = departScope;
	}
	public String getCusScope() {
		return cusScope;
	}
	public void setCusScope(String cusScope) {
		this.cusScope = cusScope;
	}
	
	public String getTrafficModeScope() {
		return trafficModeScope;
	}
	public void setTrafficModeScope(String trafficModeScope) {
		this.trafficModeScope = trafficModeScope;
	}
	public String getProductTypeScope() {
		return productTypeScope;
	}
	public void setProductTypeScope(String productTypeScope) {
		this.productTypeScope = productTypeScope;
	}
	public String getEndCityScope() {
		return endCityScope;
	}
	public void setEndCityScope(String endCityScope) {
		this.endCityScope = endCityScope;
	}
	public String getStartCount() {
		return startCount;
	}
	public void setStartCount(String startCount) {
		this.startCount = startCount;
	}
	public String getEndCount() {
		return endCount;
	}
	public void setEndCount(String endCount) {
		this.endCount = endCount;
	}
	public String getCountRange() {
		return countRange;
	}
	public void setCountRange(String countRange) {
		this.countRange = countRange;
	}
	public String getCountType() {
		return countType;
	}
	public void setCountType(String countType) {
		this.countType = countType;
	}
	
}
