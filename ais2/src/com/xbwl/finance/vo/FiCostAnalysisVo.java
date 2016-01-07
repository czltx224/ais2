package com.xbwl.finance.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author shuw
 * time Oct 13, 2011 5:33:24 PM
 */

public class FiCostAnalysisVo {

	private Long dno;
	private String cpName;
	private String consignee;//�ջ�������
	private String consigneeTel;//�ջ��˵绰���ֻ�
	private String distributionMode;//���ͷ�ʽ
	private String takeMode;//�����ʽ
	private Date createTime; //�ɱ�����
	private String trafficMode;//���䷽ʽ
	
	private Long cusId;
	private Long createDepartId;
	private String costType; //�ɱ�����
	private Double  doGoodCost; //����ɱ�
	private Double signDanCost; //ǩ������
	private Double transitCost;  //��ת�ɱ�
	private Double outSideCost;  // �ⷢ�ɱ�
	private Double therCost;  // �����ɱ�

	private Double tatalCost;  // �����ɱ�
	private Date createDanTime;// ¼������
	private String goWhereS;  // ��Ӧ��
	
	
	public Long getDno() {
		return dno;
	}
	public void setDno(Long dno) {
		this.dno = dno;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
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
	
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTrafficMode() {
		return trafficMode;
	}
	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
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
	public Double getTatalCost() {
		return tatalCost;
	}
	public void setTatalCost(Double tatalCost) {
		this.tatalCost = tatalCost;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateDanTime() {
		return createDanTime;
	}
	public void setCreateDanTime(Date createDanTime) {
		this.createDanTime = createDanTime;
	}
	public String getGoWhereS() {
		return goWhereS;
	}
	public void setGoWhereS(String goWhereS) {
		this.goWhereS = goWhereS;
	}
	public Double getDoGoodCost() {
		return doGoodCost;
	}
	public void setDoGoodCost(Double doGoodCost) {
		this.doGoodCost = doGoodCost;
	}
	public Long getCreateDepartId() {
		return createDepartId;
	}
	public void setCreateDepartId(Long createDepartId) {
		this.createDepartId = createDepartId;
	}
	public Long getCusId() {
		return cusId;
	}
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

}
