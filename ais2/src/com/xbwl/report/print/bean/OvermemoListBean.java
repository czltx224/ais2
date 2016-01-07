package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * author CaoZhili time Oct 27, 2011 6:50:32 PM
 * ʵ���嵥ʵ����
 */
@XBlinkAlias("billLading")
public class OvermemoListBean extends PrintBean {

	private String startDepartName;// ��������
	private String overmemoId;// ʵ�䵥��
	private String dno;// ���͵���
	private String consigneeInfo;// �ջ�����Ϣ
	private String toWhere;// ʵ��ȥ��
	private String autostowMode;// ���ط�ʽ
	private String piece;// ����
	private String weight;// ����
	private String cqName;// ��������
	private String flightMainNo;// ������
	private String subNo;// �ֵ���
	private String realPiece;// ʵ�����
	private String signNun;// ǩ������
	// �����˷ѣ����ͻ��ѣ�Ӧ���ɱ�
	private String consigneeFee;// ���ͻ��ѣ��������ͷ�consigneeFee+������ֵ�����cusValueAddFee��
	private String paymentCollection;// �����˷�(���ջ���)
	private String traFee;//Ӧ���ɱ�����ת�ѣ�
	private String request;//�ͻ�Ҫ��
	private String cpFee;//Ԥ�����ͷѣ�Ԥ�����ͷ�cpFee+Ԥ����ֵ�����cpValueAddFee��

	private String sendName;// �ͻ�Ա
	private String autostowName;// ����Ա
	private String sendNameTel;// �ͻ�Ա�绰
	private String totalTicket;// ��Ʊ��
	private String totalPiece;// �ܼ���
	private String totalWeight;// ������
	private String loadingGroup;// װ����
	private String driverName;
	private String dispatchGroup;// �ֲ���
	private String routeNumber;// ���κ�
	private String cargoName;//����Ա
	private String shouru;//���� ����+Ԥ��+ר��+��ֵ

	private String printName;// ��ӡ��
	private Long printNum;// ��ӡ����
	private String printTime;// ��ӡʱ��
	private String printId;// ��ӡ��¼��ID
	private String sourceNo;// ��ԴID

	public OvermemoListBean() {
		this.toWhere = "";
		this.signNun = "";
	}

	public String getStartDepartName() {
		return startDepartName;
	}

	public void setStartDepartName(String startDepartName) {
		this.startDepartName = startDepartName;
	}

	public String getOvermemoId() {
		return overmemoId;
	}

	public void setOvermemoId(String overmemoId) {
		this.overmemoId = overmemoId;
	}

	public String getDno() {
		return dno;
	}

	public void setDno(String dno) {
		this.dno = dno;
	}

	public String getConsigneeInfo() {
		return consigneeInfo;
	}

	public void setConsigneeInfo(String consigneeInfo) {
		this.consigneeInfo = consigneeInfo;
	}

	public String getToWhere() {
		return toWhere;
	}

	public void setToWhere(String toWhere) {
		this.toWhere = toWhere;
	}

	public String getAutostowMode() {
		return autostowMode;
	}

	public void setAutostowMode(String autostowMode) {
		this.autostowMode = autostowMode;
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

	public String getCqName() {
		return cqName;
	}

	public void setCqName(String cqName) {
		this.cqName = cqName;
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

	public String getRealPiece() {
		return realPiece;
	}

	public void setRealPiece(String realPiece) {
		this.realPiece = realPiece;
	}

	public String getSignNun() {
		return signNun;
	}

	public void setSignNun(String signNun) {
		this.signNun = signNun;
	}

	public String getConsigneeFee() {
		return consigneeFee;
	}

	public void setConsigneeFee(String consigneeFee) {
		this.consigneeFee = consigneeFee;
	}

	public String getPaymentCollection() {
		return paymentCollection;
	}

	public void setPaymentCollection(String paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getAutostowName() {
		return autostowName;
	}

	public void setAutostowName(String autostowName) {
		this.autostowName = autostowName;
	}

	public String getSendNameTel() {
		return sendNameTel;
	}

	public void setSendNameTel(String sendNameTel) {
		this.sendNameTel = sendNameTel;
	}

	public String getTotalTicket() {
		return totalTicket;
	}

	public void setTotalTicket(String totalTicket) {
		this.totalTicket = totalTicket;
	}

	public String getTotalPiece() {
		return totalPiece;
	}

	public void setTotalPiece(String totalPiece) {
		this.totalPiece = totalPiece;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getLoadingGroup() {
		return loadingGroup;
	}

	public void setLoadingGroup(String loadingGroup) {
		this.loadingGroup = loadingGroup;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDispatchGroup() {
		return dispatchGroup;
	}

	public void setDispatchGroup(String dispatchGroup) {
		this.dispatchGroup = dispatchGroup;
	}

	public String getRouteNumber() {
		return routeNumber;
	}

	public void setRouteNumber(String routeNumber) {
		this.routeNumber = routeNumber;
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
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

	public String getTraFee() {
		return traFee;
	}

	public void setTraFee(String traFee) {
		this.traFee = traFee;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getCpFee() {
		return cpFee;
	}

	public void setCpFee(String cpFee) {
		this.cpFee = cpFee;
	}

	public String getShouru() {
		return shouru;
	}

	public void setShouru(String shouru) {
		this.shouru = shouru;
	}
}
