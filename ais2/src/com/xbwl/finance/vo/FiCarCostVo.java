package com.xbwl.finance.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.gdcn.bpaf.common.base02.FormElementTag;

/**
 * author shuw
 * time Sep 20, 2011 11:27:11 AM
 */

public class FiCarCostVo {

	private Long dno;
	private String flightMainNo;//主单号
	private Long realPiece;         //件数
	private Double weight;        //重量
	private Long routeNumber;   // 车次号自动增长
	private String carSignNo;        //签单号
	private Long  overmemoId;
	private String carNo;
	private Date useCarDate;
	private String useCarType;
	private String rentCarResult;
	
	
	
	
	
	
	public Long getDno() {
		return dno;
	}
	public void setDno(Long dno) {
		this.dno = dno;
	}
	public String getFlightMainNo() {
		return flightMainNo;
	}
	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}
	public Long getRealPiece() {
		return realPiece;
	}
	public void setRealPiece(Long realPiece) {
		this.realPiece = realPiece;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Long getRouteNumber() {
		return routeNumber;
	}
	public void setRouteNumber(Long routeNumber) {
		this.routeNumber = routeNumber;
	}
	public String getCarSignNo() {
		return carSignNo;
	}
	public void setCarSignNo(String carSignNo) {
		this.carSignNo = carSignNo;
	}
	public Long getOvermemoId() {
		return overmemoId;
	}
	public void setOvermemoId(Long overmemoId) {
		this.overmemoId = overmemoId;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	
	@JSON(format="yyyy-MM-dd")
	public Date getUseCarDate() {
		return useCarDate;
	}
	public void setUseCarDate(Date useCarDate) {
		this.useCarDate = useCarDate;
	}
	public String getUseCarType() {
		return useCarType;
	}
	public void setUseCarType(String useCarType) {
		this.useCarType = useCarType;
	}
	public String getRentCarResult() {
		return rentCarResult;
	}
	public void setRentCarResult(String rentCarResult) {
		this.rentCarResult = rentCarResult;
	}
}
