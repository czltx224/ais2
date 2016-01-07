package com.xbwl.finance.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author shuw
 * time Oct 17, 2011 4:46:50 PM
 */

public class DepartProfitVo {

	private Long dno;
	private String  cusName;
	private Long cusId;
	private String trafficMode;
	private String distributionMode;
	private String takeMode;
	
	private String consignee;
	private String whoCash; // ���ʽ
	
	private Double cusWeight;//�Ʒ�����
	private Double normSonderzugPrice;  //ר��������
	private Double normCpRate;	  //Ԥ�����͹�������
	private Double normConsigneeRate;	//�������͹�������
	
	private Double cpRate;   //Ԥ��������
	private Double sonderzugRate;	// ��������
    private Double consigneeRate;  //ר������
	
	
	private Double sonderzugPrice;//����ר����
	private Double yfSonderzugPrice;//Ԥ��ר����
	private Double cpFee;//Ԥ�����ͷ�
	private Double consigneeFee;//�������ͷ�
	private Double cusValueAddFee;//Ԥ����ֵ�����
	private Double cpValueAddFee;//������ֵ���ܶ�
	private Double therAddFee;  //��������
	private Double  totalIncomeFee;//Ӫҵ����ϼ�

	private Double  doGoodsCostFee;   //����ɱ�
	private Double carCostFee; //�����ɱ�
	private Double transitCostFee;  //��ת�ɱ�
	private Double outCostFee;  //�ⷢ�ɱ�
	private Double therCostFee;  //�����ɱ�
	private Double totalCostFee;  //�ɱ��ϼ�
	
	private Double grossProfitFee;  //ë����
	private Long sonderzug;//�Ƿ�ר��
	
	private Date accounting;
	private Date createTime;
	private Long departId;
	private String departName;
	private String serviceDepartName;
	private String serviceDepartCode;
	private String serviceName;
	
	public Long getDno() {
		return dno;
	}
	public void setDno(Long dno) {
		this.dno = dno;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getTrafficMode() {
		return trafficMode;
	}
	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
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
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
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
	public Double getSonderzugPrice() {
		return sonderzugPrice;
	}
	public void setSonderzugPrice(Double sonderzugPrice) {
		this.sonderzugPrice = sonderzugPrice;
	}
	public Double getCpFee() {
		return cpFee;
	}
	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}
	public Double getConsigneeFee() {
		return consigneeFee;
	}
	public void setConsigneeFee(Double consigneeFee) {
		this.consigneeFee = consigneeFee;
	}
	public Double getCusValueAddFee() {
		return cusValueAddFee;
	}
	public void setCusValueAddFee(Double cusValueAddFee) {
		this.cusValueAddFee = cusValueAddFee;
	}
	public Double getCpValueAddFee() {
		return cpValueAddFee;
	}
	public void setCpValueAddFee(Double cpValueAddFee) {
		this.cpValueAddFee = cpValueAddFee;
	}
	public Double getTotalIncomeFee() {
		return totalIncomeFee;
	}
	public void setTotalIncomeFee(Double totalIncomeFee) {
		this.totalIncomeFee = totalIncomeFee;
	}
	public Double getDoGoodsCostFee() {
		return doGoodsCostFee;
	}
	public void setDoGoodsCostFee(Double doGoodsCostFee) {
		this.doGoodsCostFee = doGoodsCostFee;
	}
	public Double getCarCostFee() {
		return carCostFee;
	}
	public void setCarCostFee(Double carCostFee) {
		this.carCostFee = carCostFee;
	}
	public Double getTransitCostFee() {
		return transitCostFee;
	}
	public void setTransitCostFee(Double transitCostFee) {
		this.transitCostFee = transitCostFee;
	}
	public Double getOutCostFee() {
		return outCostFee;
	}
	public void setOutCostFee(Double outCostFee) {
		this.outCostFee = outCostFee;
	}
	public Double getTherCostFee() {
		return therCostFee;
	}
	public void setTherCostFee(Double therCostFee) {
		this.therCostFee = therCostFee;
	}
	public Double getTotalCostFee() {
		return totalCostFee;
	}
	public void setTotalCostFee(Double totalCostFee) {
		this.totalCostFee = totalCostFee;
	}
	public Double getGrossProfitFee() {
		return grossProfitFee;
	}
	public void setGrossProfitFee(Double grossProfitFee) {
		this.grossProfitFee = grossProfitFee;
	}
	public Long getSonderzug() {
		return sonderzug;
	}
	public void setSonderzug(Long sonderzug) {
		this.sonderzug = sonderzug;
	}
	
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getServiceDepartName() {
		return serviceDepartName;
	}
	public void setServiceDepartName(String serviceDepartName) {
		this.serviceDepartName = serviceDepartName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Long getCusId() {
		return cusId;
	}
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}
	public Double getNormSonderzugPrice() {
		return normSonderzugPrice;
	}
	public void setNormSonderzugPrice(Double normSonderzugPrice) {
		this.normSonderzugPrice = normSonderzugPrice;
	}
	public Double getNormCpRate() {
		return normCpRate;
	}
	public void setNormCpRate(Double normCpRate) {
		this.normCpRate = normCpRate;
	}
	public Double getNormConsigneeRate() {
		return normConsigneeRate;
	}
	public void setNormConsigneeRate(Double normConsigneeRate) {
		this.normConsigneeRate = normConsigneeRate;
	}
	public Double getCpRate() {
		return cpRate;
	}
	public void setCpRate(Double cpRate) {
		this.cpRate = cpRate;
	}
	public Double getSonderzugRate() {
		return sonderzugRate;
	}
	public void setSonderzugRate(Double sonderzugRate) {
		this.sonderzugRate = sonderzugRate;
	}
	public Double getConsigneeRate() {
		return consigneeRate;
	}
	public void setConsigneeRate(Double consigneeRate) {
		this.consigneeRate = consigneeRate;
	}
	public Double getTherAddFee() {
		return therAddFee;
	}
	public void setTherAddFee(Double therAddFee) {
		this.therAddFee = therAddFee;
	}
	
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getAccounting() {
		return accounting;
	}
	public void setAccounting(Date accounting) {
		this.accounting = accounting;
	}
	public String getServiceDepartCode() {
		return serviceDepartCode;
	}
	public void setServiceDepartCode(String serviceDepartCode) {
		this.serviceDepartCode = serviceDepartCode;
	}
	public Double getYfSonderzugPrice() {
		return yfSonderzugPrice;
	}
	public void setYfSonderzugPrice(Double yfSonderzugPrice) {
		this.yfSonderzugPrice = yfSonderzugPrice;
	}



}
