package com.xbwl.oper.stock.vo;

import java.io.Serializable;

/**
 * author CaoZhili time Jul 5, 2010 8:30:13 AM
 */
public class OprEnterStockReportVo implements Serializable {

	private Long id;// 
	private Long overmemoId;//
	private Long dno;// 
	private String subNo;// 
	private String flightNo;// 
	private String goods;// 
	private String stockAreaName; // 
	private String distributionMode;//
	private String takeMode;//
	private Long realPiece;// 
	private Long piece;// 
	private Long weight;// 
	private Long cusId;// 
	private String cpName;// 
	private String request;//
	private Long isOpr;//
	private String remark;//

	private Long carId;// 
	private String carCode;// 
	private Long status;//

	private String flightMainNo;//
	private Double bulk;// 体积
	private String addr;// 收货人地址
	private String consignee;// 收货人
	private Long requestDoId;
	private String orderfield;// 排序字段15
	
	private Long endDepartId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOvermemoId() {
		return overmemoId;
	}

	public void setOvermemoId(Long overmemoId) {
		this.overmemoId = overmemoId;
	}

	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	public String getSubNo() {
		return subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getStockAreaName() {
		return stockAreaName;
	}

	public void setStockAreaName(String stockAreaName) {
		this.stockAreaName = stockAreaName;
	}

	public String getDistributionMode() {
		return distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	public String getTakeMode() {
		return takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	public Long getRealPiece() {
		return realPiece;
	}

	public void setRealPiece(Long realPiece) {
		this.realPiece = realPiece;
	}

	public Long getPiece() {
		return piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public Long getCusId() {
		return cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public Long getIsOpr() {
		return isOpr;
	}

	public void setIsOpr(Long isOpr) {
		this.isOpr = isOpr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getFlightMainNo() {
		return flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	public Double getBulk() {
		return bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public Long getRequestDoId() {
		return requestDoId;
	}

	public void setRequestDoId(Long requestDoId) {
		this.requestDoId = requestDoId;
	}

	public String getOrderfield() {
		return orderfield;
	}

	public void setOrderfield(String orderfield) {
		this.orderfield = orderfield;
	}
	

	public Long getEndDepartId() {
		return endDepartId;
	}

	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
	}

	public OprEnterStockReportVo(Long id, Long overmemoId, Long dno,
			String subNo, String flightNo, String goods, String stockAreaName,
			String distributionMode, String takeMode, Long realPiece,
			Long piece, Long weight, Long cusId, String cpName, String request,
			Long isOpr, String remark, Long carId, String carCode, Long status,
			String flightMainNo, Double bulk, String addr, String consignee,
			Long requestDoId, String orderfield) {
		super();
		this.id = id;
		this.overmemoId = overmemoId;
		this.dno = dno;
		this.subNo = subNo;
		this.flightNo = flightNo;
		this.goods = goods;
		this.stockAreaName = stockAreaName;
		this.distributionMode = distributionMode;
		this.takeMode = takeMode;
		this.realPiece = realPiece;
		this.piece = piece;
		this.weight = weight;
		this.cusId = cusId;
		this.cpName = cpName;
		this.request = request;
		this.isOpr = isOpr;
		this.remark = remark;
		this.carId = carId;
		this.carCode = carCode;
		this.status = status;
		this.flightMainNo = flightMainNo;
		this.bulk = bulk;
		this.addr = addr;
		this.consignee = consignee;
		this.requestDoId = requestDoId;
		this.orderfield = orderfield;
	}

	public OprEnterStockReportVo(Long id) {
		super();
		this.id = id;
	}

	public OprEnterStockReportVo() {
		super();
	}
	
}
