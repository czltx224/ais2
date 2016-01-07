package com.xbwl.oper.stock.vo;

/**
 * author CaoZhili time Nov 7, 2011 4:06:22 PM
 */

public class OprMathingGoods implements java.io.Serializable {

	private Long dno;// 配送单号
	private String flightMainNo;// 主单号
	private Long id;// 交接单明细表ID
	private Long overmemoNo;// 交接单号
	private Long mainId;//主单号ID
	private Boolean reachFlag;//是否点到

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOvermemoNo() {
		return overmemoNo;
	}

	public void setOvermemoNo(Long overmemoNo) {
		this.overmemoNo = overmemoNo;
	}

	public Long getMainId() {
		return mainId;
	}

	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}

	public Boolean getReachFlag() {
		return reachFlag;
	}

	public void setReachFlag(Boolean reachFlag) {
		this.reachFlag = reachFlag;
	}
}
