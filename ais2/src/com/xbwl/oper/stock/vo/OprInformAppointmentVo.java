package com.xbwl.oper.stock.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author CaoZhili time Jul 18, 2011 2:39:22 PM
 */
public class OprInformAppointmentVo implements java.io.Serializable {

	private Long dno;// ���͵���
	private Long cusId;// ����˾���̱���
	private String cpName;// ����˾��������
	private String flightNo;// �����
	private Date flightDate;// ��������
	private String flightTime;// ����ʱ��
	private String trafficMode;// ���䷽ʽ
	private String flightMainNo;// ����������
	private String subNo;// �ֵ���
	private String distributionMode;// ���ͷ�ʽ
	private String takeMode;// �����ʽ
	private String receiptType;// �ص�����
	private String consignee;// �ջ�������
	private String consigneeTel;// �ջ��˵绰
	private String consigneePho;// �ջ����ֻ�
	private String city;// �ջ���������
	private String town;// �ջ���������������
	private String addr;// �ջ��˵�ַ
	private Long piece;// ����
	private Double cusWeight;// �Ʒ�����
	private Double bulk;// ���
	private String goodStatus;// ����״̬
	private Double cpRate;// ����Ӧ������
	private Double cpFee;// ����Ӧ���ѣ�Ԥ����
	private Double paymentCollection;
	private Double consigneeRate;// �ջ���Ӧ������
	private Double consigneeFee;// �ջ���Ӧ���ѣ�������(���ͷ�)
	private String customerService;// �ͷ�Ա
	private Double cusValueAddFee;// �ͻ���ֵ�����
	private Double cpValueAddFee;// ��ֵ������ܶ�
	private String areaType;// �ջ��˵�ַ����

	private Long oprInformAppointmentId;// ֪ͨԤԼ���е�id
	private Long informNum;// ֪ͨ����
	private Date lastInformTime;// ���֪ͨʱ��
	private String lastServiceName;// ���֪ͨ�ͷ�Ա
	private String lastInformCus;// ���֪ͨ�ͻ�
	private Long lastInformResult;// ���֪ͨ���
	private Long notifyStatus;// ԤԼ״̬ ԤԼ�ɹ���Ҫ�޸Ĵ�״̬������opr_status ��
	private Long reachStatus;

	private String request;
	private Long stockPiece;
	private Long realPiece;

	private String informType;

	private String requestStage;// ִ�н׶�
	private Double countFee;// �ջ���Ӧ���ϼƷ���
	private String ts;
	private Long departId;
	private Long outStatus;

	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
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

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	@JSON(format = "yyyy-MM-dd hh:mm:ss")
	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	public String getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

	public String getTrafficMode() {
		return trafficMode;
	}

	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
	}

	public String getFlightMainNo() {
		return flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	public String getSubNo() {
		return subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
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

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
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

	public String getConsigneePho() {
		return consigneePho;
	}

	public void setConsigneePho(String consigneePho) {
		this.consigneePho = consigneePho;
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

	public Double getBulk() {
		return bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

	public Double getCpRate() {
		return cpRate;
	}

	public void setCpRate(Double cpRate) {
		this.cpRate = cpRate;
	}

	public Double getCpFee() {
		return cpFee;
	}

	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}

	public Double getConsigneeRate() {
		return consigneeRate;
	}

	public void setConsigneeRate(Double consigneeRate) {
		this.consigneeRate = consigneeRate;
	}

	public Double getConsigneeFee() {
		return consigneeFee;
	}

	public void setConsigneeFee(Double consigneeFee) {
		this.consigneeFee = consigneeFee;
	}

	public String getCustomerService() {
		return customerService;
	}

	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public Long getInformNum() {
		return informNum;
	}

	public void setInformNum(Long informNum) {
		this.informNum = informNum;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public Long getStockPiece() {
		return stockPiece;
	}

	public void setStockPiece(Long stockPiece) {
		this.stockPiece = stockPiece;
	}

	public Long getRealPiece() {
		return realPiece;
	}

	public void setRealPiece(Long realPiece) {
		this.realPiece = realPiece;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getLastInformTime() {
		return lastInformTime;
	}

	public void setLastInformTime(Date lastInformTime) {
		this.lastInformTime = lastInformTime;
	}

	public String getLastServiceName() {
		return lastServiceName;
	}

	public void setLastServiceName(String lastServiceName) {
		this.lastServiceName = lastServiceName;
	}

	public String getLastInformCus() {
		return lastInformCus;
	}

	public void setLastInformCus(String lastInformCus) {
		this.lastInformCus = lastInformCus;
	}

	public Long getLastInformResult() {
		return lastInformResult;
	}

	public void setLastInformResult(Long lastInformResult) {
		this.lastInformResult = lastInformResult;
	}

	public String getGoodStatus() {
		return goodStatus;
	}

	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}

	public Double getCountFee() {
		// д�����㹫ʽ
		this.countFee = new Double(0);
		return this.getCpFee() + this.getConsigneeFee()
				+ this.getPaymentCollection() + this.getCpValueAddFee();
		// return this.countFee+1;
	}

	public void setCountFee(Double countFee) {
		this.countFee = countFee;
	}

	public Long getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(Long notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public Long getReachStatus() {
		return reachStatus;
	}

	public void setReachStatus(Long reachStatus) {
		this.reachStatus = reachStatus;
	}

	public Double getPaymentCollection() {
		return paymentCollection;
	}

	public void setPaymentCollection(Double paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

	public String getRequestStage() {
		return requestStage;
	}

	public void setRequestStage(String requestStage) {
		this.requestStage = requestStage;
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

	public Long getOprInformAppointmentId() {
		return oprInformAppointmentId;
	}

	public void setOprInformAppointmentId(Long oprInformAppointmentId) {
		this.oprInformAppointmentId = oprInformAppointmentId;
	}

	public String getInformType() {
		return informType;
	}

	public void setInformType(String informType) {
		this.informType = informType;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public Long getOutStatus() {
		return outStatus;
	}

	public void setOutStatus(Long outStatus) {
		this.outStatus = outStatus;
	}

	public OprInformAppointmentVo() {
		super();
	}
	
	
}
