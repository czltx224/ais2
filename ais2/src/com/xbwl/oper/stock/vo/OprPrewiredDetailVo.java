package com.xbwl.oper.stock.vo;

import java.util.Date;

/**
 * author shuw
 * time 2011-7-19 下午02:32:39
 */
public class OprPrewiredDetailVo {
	private Long dno;//配送单号
	private String flightNo;//航班号
	private Date flightDate;//航班日期
	private String consignee;//收货人姓名
	private String consigneeTel;//收货人电话   
	private String town;//收货人所在区或者镇
	private String addr;//收货人地址
	private Long piece;//件数
	private Double cusWeight;//计费重量
	private String gowhere;//去向
	private Long status;//状态
	private String customerService;//客服员
	private String goodsStatus;//货物最新状态
	private String goods;
	private String valuationType;
	private Long realPiece;
	private String request;
	private String distributionMode;   //配送方式
	private String city;
	
	private Date createTime;


	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getDno() {
		return dno;
	}
	public void setDno(Long dno) {
		this.dno = dno;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public Date getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getConsigneeTel() {
		return consigneeTel;
	}
	public void setConsigneeTel(String consigneeTel) {
		this.consigneeTel = consigneeTel;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Long getPiece() {
		return piece;
	}
	public void setPiece(Long piece) {
		this.piece = piece;
	}
	public Double getCusWeight() {
		return cusWeight;
	}
	public void setCusWeight(Double cusWeight) {
		this.cusWeight = cusWeight;
	}
	public String getGowhere() {
		return gowhere;
	}
	public void setGowhere(String gowhere) {
		this.gowhere = gowhere;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getCustomerService() {
		return customerService;
	}
	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}
	public String getGoodsStatus() {
		return goodsStatus;
	}
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	public String getGoods() {
		return goods;
	}
	public void setGoods(String goods) {
		this.goods = goods;
	}
	public String getValuationType() {
		return valuationType;
	}
	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}
	public Long getRealPiece() {
		return realPiece;
	}
	public void setRealPiece(Long realPiece) {
		this.realPiece = realPiece;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getDistributionMode() {
		return distributionMode;
	}
	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	

}
