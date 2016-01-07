package com.xbwl.finance.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 传真价格查询VO
 *@author LiuHao
 *@time Apr 26, 2012 9:11:44 AM
 */
public class FaxrateSerarchVo implements Serializable{
	private Long cusId;//代理ID
	private String whoCash;//付款方
	private Double cusWeight;//计费重量
	private String takeMode;//提货方式
	private String distributionMode;//配送方式
	private Double bulk;//体积
	private Long piece;//件数
	private Long disDepartId;//配送部门
	private String cusName;//供应商名称
	private Long cpId;//供应商ID
	private String[] consigneeTel;//收货人电话
	private String addrType;//地址类型
	private String valuationType;//计价方式
	private String startCity;//始发站
	private String trafficMode;//运输方式
	private String city;//市
	private String town;//区
	private String street;//街道
	private Date startDate;//开始日期
	private Date endDate;//结束日期
	private String cpName;
	
	public FaxrateSerarchVo(){
		
	}
	
	public Long getCusId() {
		return cusId;
	}
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}
	public String getWhoCash() {
		return whoCash;
	}
	public void setWhoCash(String whoCash) {
		this.whoCash = whoCash;
	}
	public Double getCusWeight() {
		return cusWeight;
	}
	public void setCusWeight(Double cusWeight) {
		this.cusWeight = cusWeight;
	}
	public String getTakeMode() {
		return takeMode;
	}
	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}
	public String getDistributionMode() {
		return distributionMode;
	}
	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}
	public Double getBulk() {
		return bulk;
	}
	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}
	public Long getPiece() {
		return piece;
	}
	public void setPiece(Long piece) {
		this.piece = piece;
	}
	public Long getDisDepartId() {
		return disDepartId;
	}
	public void setDisDepartId(Long disDepartId) {
		this.disDepartId = disDepartId;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	
	public String[] getConsigneeTel() {
		return consigneeTel;
	}
	public void setConsigneeTel(String[] consigneeTel) {
		this.consigneeTel = consigneeTel;
	}
	public String getAddrType() {
		return addrType;
	}
	public void setAddrType(String addrType) {
		this.addrType = addrType;
	}
	public String getValuationType() {
		return valuationType;
	}
	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	public String getTrafficMode() {
		return trafficMode;
	}
	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
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
	public Long getCpId() {
		return cpId;
	}
	public void setCpId(Long cpId) {
		this.cpId = cpId;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	
	
	
}
