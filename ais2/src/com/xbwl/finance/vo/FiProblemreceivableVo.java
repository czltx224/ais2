package com.xbwl.finance.vo;

import com.xbwl.entity.FiProblemreceivable;

public class FiProblemreceivableVo extends FiProblemreceivable {
	private String flightmainno;// 航空主单号
	private Double amount;// 金额
	private String sourceDatadetail;// 数据来源
	private Long sourceNodetail;// 来源单号
	private Long reconciliationNo;// 对账单号
	private Long reconciliationStatus;// 对账状态
	private Long piece;// 件数
	private Double cusWeight;// 计费重量
	private Double bulk;// 体积
	private String costType;// 费用类型
	
	public String getFlightmainno() {
		return flightmainno;
	}
	public void setFlightmainno(String flightmainno) {
		this.flightmainno = flightmainno;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getSourceDatadetail() {
		return sourceDatadetail;
	}
	public void setSourceDatadetail(String sourceDatadetail) {
		this.sourceDatadetail = sourceDatadetail;
	}
	public Long getSourceNodetail() {
		return sourceNodetail;
	}
	public void setSourceNodetail(Long sourceNodetail) {
		this.sourceNodetail = sourceNodetail;
	}
	public Long getReconciliationNo() {
		return reconciliationNo;
	}
	public void setReconciliationNo(Long reconciliationNo) {
		this.reconciliationNo = reconciliationNo;
	}
	public Long getReconciliationStatus() {
		return reconciliationStatus;
	}
	public void setReconciliationStatus(Long reconciliationStatus) {
		this.reconciliationStatus = reconciliationStatus;
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
	public Double getBulk() {
		return bulk;
	}
	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	
}
