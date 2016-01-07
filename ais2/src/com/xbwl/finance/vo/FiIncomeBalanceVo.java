package com.xbwl.finance.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author shuw
 * time Feb 22, 2012 5:55:17 PM
 */

public class FiIncomeBalanceVo {
	
	private Date createTime;  //����
    private Double incomeAmount; //������
    private Double doGoodCost;  //����ɱ�
    private Double signDanCost;   //ǩ���ɱ�
    private Double transitCost;     //��ת�ɱ�
    private Double outSideCost;   //�ⷢ�ɱ�
    private Double therCost;          //�����ɱ�
    private Double totalCost;  //С��
    private Double variableCostRate;   //�䶯�ɱ���
    private Double breakEvenPoint;   //ӯ��ƽ���
    
	private Date startTime; //��ʼʱ��
	private Date endTime;  //����ʱ��

	public FiIncomeBalanceVo() {
		super();
	}
	
	public FiIncomeBalanceVo(Date createTime, Double incomeAmount,
			Double doGoodCost, Double signDanCost, Double transitCost,
			Double outSideCost, Double therCost, Double totalCost,
			Double variableCostRate, Double breakEvenPoint) {
		super();
		this.createTime = createTime;
		this.incomeAmount = incomeAmount;
		this.doGoodCost = doGoodCost;
		this.signDanCost = signDanCost;
		this.transitCost = transitCost;
		this.outSideCost = outSideCost;
		this.therCost = therCost;
		this.totalCost = totalCost;
		this.variableCostRate = variableCostRate;
		this.breakEvenPoint = breakEvenPoint;
	}
	
    
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@JSON(format = "yyyy-MM-dd")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Double getIncomeAmount() {
		return incomeAmount;
	}
	public void setIncomeAmount(Double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}
	public Double getDoGoodCost() {
		return doGoodCost;
	}
	public void setDoGoodCost(Double doGoodCost) {
		this.doGoodCost = doGoodCost;
	}
	public Double getSignDanCost() {
		return signDanCost;
	}
	public void setSignDanCost(Double signDanCost) {
		this.signDanCost = signDanCost;
	}
	public Double getTransitCost() {
		return transitCost;
	}
	public void setTransitCost(Double transitCost) {
		this.transitCost = transitCost;
	}
	public Double getOutSideCost() {
		return outSideCost;
	}
	public void setOutSideCost(Double outSideCost) {
		this.outSideCost = outSideCost;
	}
	public Double getTherCost() {
		return therCost;
	}
	public void setTherCost(Double therCost) {
		this.therCost = therCost;
	}
	public Double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	public Double getVariableCostRate() {
		return variableCostRate;
	}
	public void setVariableCostRate(Double variableCostRate) {
		this.variableCostRate = variableCostRate;
	}
	public Double getBreakEvenPoint() {
		return breakEvenPoint;
	}
	public void setBreakEvenPoint(Double breakEvenPoint) {
		this.breakEvenPoint = breakEvenPoint;
	}

}
