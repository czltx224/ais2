package com.xbwl.finance.dto.impl;

import java.util.Date;

public class FiInterfaceProDto {
	private Long preCustomerId; //����ID(����ǰ)
	private Long customerId;// ����ID(���ĺ�)
	private String customerName;// ��������
	private String distributionMode;// ���ͷ�ʽ(�°�/��ת/�ⷢ)
	private Long dno;// ���͵���(�������)
	private Long settlementType;//�ո�����(1:���/�տ2:ȡ��/����)(�������)
	private String documentsType; //���ݴ���:����\�ɱ�\����\Ԥ���\���ջ���(�������)
	private String documentsSmalltype; //����С�ࣺ���͵�/���˵�/���͵�/Ԥ��(�������)
	private Long documentsNo;//���ݺţ�����С���Ӧ�ĵ���
	private String collectionUser; //�տ�������:���᣺�����ˣ��ͻ����ͻ�Ա���ⷢ���ⷢԱ(�������)
	private double amount;// ���(�������)
	private String costType;// �������ͣ�ר����/��ֵ�����/����Ӧ����/���ջ���/�������ͷ�/������ֵ��/��������/����/��ת��(�������)
	private String penyType; //��������(�ֽᡢ�½�)(�������)
	private Long departId; // ����ҵ���Ų���id(�������)
	private String departName;//����ҵ��������
	private String sourceData;// ������Դ:��ת�ɱ�(�������)
	private Long sourceNo;// ��Դ����:��ת�ɱ�ID(�������)
	
	private String outStockMode;//������ò���ӿڣ����ⷽʽ(����/��ת/�ⷢ/�ͻ�/��������)
	private String contacts; //������λ:û�ڿ��̵����еĿ��̣��ڲ��ͻ��������ɱ����տ�˾����
	
	private String createRemark; //ժҪ
	
	//�ڲ�����
	private Long startDepartId;//ʼ������ID
	private String startDepartName; //ʼ������
	private Long endDepartId; //�ն˲���ID
	private String endDepart;//�ն˲���
	
	//����ɱ�
	private String flightMainNo;//�ֶ�����(������)
	private Double flightWeight;//�Ƶ�����
	private Long flightPiece;//�Ƶ�����
	private Double bulk;//���
	private String flightNo;//�����
	private String startcity;//ʼ��վ
	private String goodsType;//��������
	
	//��������
	//private Long incomeDepartId;//���벿��(¼������ID)
	private Long disDepartId;//���Ͳ���ID
	private double beforeAmount=0.0;//����ǰ���
	private Long gocustomerId;// ����ID(��ת���ⷢ����ID)
	private String gocustomerName;// ��������(��ת���ⷢ����)
	private Long stockStatus;//0��δ���⣬1���ѳ���
	
	
	//����
	private String whoCash;//���
	private String customerService;//�ͷ�Ա
	private String admDepart;//�ͷ�Ա��������
	private Long admDepartId;//�ͷ�Ա��������Id
	private String incomeDepart;//���벿��
	private Long incomeDepartId;//���벿��
	
	
	//�ɱ�
	private String costType1;//�ɱ�����
	private String costTypeDetail;  //�ɱ�С��
	

	public Long getDno() {
		return dno;
	}
	public void setDno(Long dno) {
		this.dno = dno;
	}

	public Long getSettlementType() {
		return settlementType;
	}
	public void setSettlementType(Long settlementType) {
		this.settlementType = settlementType;
	}
	public String getDocumentsType() {
		return documentsType;
	}
	public void setDocumentsType(String documentsType) {
		this.documentsType = documentsType;
	}
	public String getDocumentsSmalltype() {
		return documentsSmalltype;
	}
	public void setDocumentsSmalltype(String documentsSmalltype) {
		this.documentsSmalltype = documentsSmalltype;
	}
	public String getCollectionUser() {
		return collectionUser;
	}
	public void setCollectionUser(String collectionUser) {
		this.collectionUser = collectionUser;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	public String getSourceData() {
		return sourceData;
	}
	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}
	public Long getSourceNo() {
		return sourceNo;
	}
	public void setSourceNo(Long sourceNo) {
		this.sourceNo = sourceNo;
	}
	public Long getDocumentsNo() {
		return documentsNo;
	}
	public void setDocumentsNo(Long documentsNo) {
		this.documentsNo = documentsNo;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDistributionMode() {
		return distributionMode;
	}
	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}
	public String getOutStockMode() {
		return outStockMode;
	}
	public void setOutStockMode(String outStockMode) {
		this.outStockMode = outStockMode;
	}
	public String getCreateRemark() {
		return createRemark;
	}
	public void setCreateRemark(String createRemark) {
		this.createRemark = createRemark;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public Long getStartDepartId() {
		return startDepartId;
	}
	public void setStartDepartId(Long startDepartId) {
		this.startDepartId = startDepartId;
	}
	public String getStartDepartName() {
		return startDepartName;
	}
	public void setStartDepartName(String startDepartName) {
		this.startDepartName = startDepartName;
	}
	public Long getEndDepartId() {
		return endDepartId;
	}
	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
	}
	public String getEndDepart() {
		return endDepart;
	}
	public void setEndDepart(String endDepart) {
		this.endDepart = endDepart;
	}
	public String getFlightMainNo() {
		return flightMainNo;
	}
	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}
	public Double getFlightWeight() {
		return flightWeight;
	}
	public void setFlightWeight(Double flightWeight) {
		this.flightWeight = flightWeight;
	}
	public Long getFlightPiece() {
		return flightPiece;
	}
	public void setFlightPiece(Long flightPiece) {
		this.flightPiece = flightPiece;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getStartcity() {
		return startcity;
	}
	public void setStartcity(String startcity) {
		this.startcity = startcity;
	}
	public Long getGocustomerId() {
		return gocustomerId;
	}
	public void setGocustomerId(Long gocustomerId) {
		this.gocustomerId = gocustomerId;
	}
	public String getGocustomerName() {
		return gocustomerName;
	}
	public void setGocustomerName(String gocustomerName) {
		this.gocustomerName = gocustomerName;
	}
	public Long getStockStatus() {
		return stockStatus;
	}
	public void setStockStatus(Long stockStatus) {
		this.stockStatus = stockStatus;
	}
	public String getWhoCash() {
		return whoCash;
	}
	public void setWhoCash(String whoCash) {
		this.whoCash = whoCash;
	}
	public String getCustomerService() {
		return customerService;
	}
	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}
	public String getAdmDepart() {
		return admDepart;
	}
	public void setAdmDepart(String admDepart) {
		this.admDepart = admDepart;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public double getBeforeAmount() {
		return beforeAmount;
	}
	public void setBeforeAmount(double beforeAmount) {
		this.beforeAmount = beforeAmount;
	}
	public String getPenyType() {
		return penyType;
	}
	public void setPenyType(String penyType) {
		this.penyType = penyType;
	}
	public String getCostType1() {
		return costType1;
	}
	public void setCostType1(String costType1) {
		this.costType1 = costType1;
	}
	public String getCostTypeDetail() {
		return costTypeDetail;
	}
	public void setCostTypeDetail(String costTypeDetail) {
		this.costTypeDetail = costTypeDetail;
	}
	public Long getAdmDepartId() {
		return admDepartId;
	}
	public void setAdmDepartId(Long admDepartId) {
		this.admDepartId = admDepartId;
	}
	public String getIncomeDepart() {
		return incomeDepart;
	}
	public void setIncomeDepart(String incomeDepart) {
		this.incomeDepart = incomeDepart;
	}
	public Long getIncomeDepartId() {
		return incomeDepartId;
	}
	public void setIncomeDepartId(Long incomeDepartId) {
		this.incomeDepartId = incomeDepartId;
	}
	public Double getBulk() {
		return bulk;
	}
	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}
	public Long getDisDepartId() {
		return disDepartId;
	}
	public void setDisDepartId(Long disDepartId) {
		this.disDepartId = disDepartId;
	}
	public Long getPreCustomerId() {
		return preCustomerId;
	}
	public void setPreCustomerId(Long preCustomerId) {
		this.preCustomerId = preCustomerId;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	
	
	
}
