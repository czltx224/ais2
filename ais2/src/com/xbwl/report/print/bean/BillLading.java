package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * @author Administrator
 * @createTime 10:52:48 AM
 * @updateName Administrator
 * @updateTime 10:52:48 AM
 * �����ʵ����
 */
@XBlinkAlias("billLading")
public class BillLading extends PrintBean {
	// ���Ͻ�����
	private String context;

	// Ͷ�ߵ绰
	private String complainTel;

	// �ͷ�Ա����
	private String cusService;

	// �ͷ��绰
	private String cusServiceTel;
	private String title;//����
	
	private String toWhere;//ȥ��
	private String startDepartName;//ʼ������
	private String traceTo;//ʵ��ȥ��
	private String cqName;//��������
	private String flightInfo;//������Ϣ
	private String reachDate;//��������
	private String takeMode;//�����ʽ
	private String dno;//���͵���
	private String flightMainNo;//������
	private String subNo;//�ֵ���
	private String consigneeInfo;//�ջ�����Ϣ
	private String piece;//����
	private String weight;//����
	private String consigneeFee;//���ͷ�
	private String cpValueAddFee;//��ֵ�����(�ִ���)
	private String paymentCollection;//�����˷�(���ջ���)
	private String countFee;//�ϼ�
	private String signType;//ǩ������
	private String request;//�ͻ�Ҫ��(���Ի�Ҫ��)
	private String signRequire;//ǩ��Ҫ��
	private String cardId;//ǩ����֤��
	private String isUrgent;//����ͻ�(VIP ר�� ��֪ͨ�Ż�)  Ĭ����ͨ
	private String distributionMode;//���ͷ�ʽ
	private String morningflag;//�Ƿ����
	private String specialSign;//�����ַ�(�VIP����)
	private String stockAddFee;//�ִ���ֵ�����
	private String signMan;//ǩ����
	private String signTime;//ǩ��ʱ��
	private String bulk;//���

	private String printName;// ��ӡ��
	private Long printNum;// ��ӡ����
	private String printTime;// ��ӡʱ��
	private String printId;// ��ӡ��¼��ID
	private String sourceNo;// ��ԴID

	public BillLading(){
		this.printNum=0l;
		this.cardId="";
		this.reachDate="";
		this.request="";
		this.signRequire="";
		this.signMan="";
		this.signTime="";
	}
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getComplainTel() {
		return complainTel;
	}

	public void setComplainTel(String complainTel) {
		this.complainTel = complainTel;
	}

	public String getCusService() {
		return cusService;
	}

	public void setCusService(String cusService) {
		this.cusService = cusService;
	}

	public String getCusServiceTel() {
		return cusServiceTel;
	}

	public void setCusServiceTel(String cusServiceTel) {
		this.cusServiceTel = cusServiceTel;
	}

	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

	public Long getPrintNum() {
		return printNum;
	}

	public void setPrintNum(Long printNum) {
		this.printNum = printNum;
	}

	public String getPrintTime() {
		return printTime;
	}

	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}

	public String getPrintId() {
		return printId;
	}

	public void setPrintId(String printId) {
		this.printId = printId;
	}

	public String getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}

	public String getToWhere() {
		return toWhere;
	}

	public void setToWhere(String toWhere) {
		this.toWhere = toWhere;
	}

	public String getStartDepartName() {
		return startDepartName;
	}

	public void setStartDepartName(String startDepartName) {
		this.startDepartName = startDepartName;
	}

	public String getTraceTo() {
		return traceTo;
	}

	public void setTraceTo(String traceTo) {
		this.traceTo = traceTo;
	}

	public String getCqName() {
		return cqName;
	}

	public void setCqName(String cqName) {
		this.cqName = cqName;
	}

	public String getFlightInfo() {
		return flightInfo;
	}

	public void setFlightInfo(String flightInfo) {
		this.flightInfo = flightInfo;
	}

	public String getReachDate() {
		return reachDate;
	}

	public void setReachDate(String reachDate) {
		this.reachDate = reachDate;
	}

	public String getTakeMode() {
		return takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	public String getDno() {
		return dno;
	}

	public void setDno(String dno) {
		this.dno = dno;
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

	public String getConsigneeInfo() {
		return consigneeInfo;
	}

	public void setConsigneeInfo(String consigneeInfo) {
		this.consigneeInfo = consigneeInfo;
	}

	public String getPiece() {
		return piece;
	}

	public void setPiece(String piece) {
		this.piece = piece;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getConsigneeFee() {
		return consigneeFee;
	}

	public void setConsigneeFee(String consigneeFee) {
		this.consigneeFee = consigneeFee;
	}

	public String getCpValueAddFee() {
		return cpValueAddFee;
	}

	public void setCpValueAddFee(String cpValueAddFee) {
		this.cpValueAddFee = cpValueAddFee;
	}

	public String getPaymentCollection() {
		return paymentCollection;
	}

	public void setPaymentCollection(String paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

	public String getCountFee() {
		return countFee;
	}

	public void setCountFee(String countFee) {
		this.countFee = countFee;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getSignRequire() {
		return signRequire;
	}

	public void setSignRequire(String signRequire) {
		this.signRequire = signRequire;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getIsUrgent() {
		return isUrgent;
	}

	public void setIsUrgent(String isUrgent) {
		this.isUrgent = isUrgent;
	}

	public String getMorningflag() {
		return morningflag;
	}

	public void setMorningflag(String morningflag) {
		this.morningflag = morningflag;
	}

	public String getDistributionMode() {
		return distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	public String getSpecialSign() {
		return specialSign;
	}

	public void setSpecialSign(String specialSign) {
		this.specialSign = specialSign;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStockAddFee() {
		return stockAddFee;
	}

	public void setStockAddFee(String stockAddFee) {
		this.stockAddFee = stockAddFee;
	}

	public String getSignMan() {
		return signMan;
	}

	public void setSignMan(String signMan) {
		this.signMan = signMan;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getBulk() {
		return bulk;
	}

	public void setBulk(String bulk) {
		this.bulk = bulk;
	}
}
