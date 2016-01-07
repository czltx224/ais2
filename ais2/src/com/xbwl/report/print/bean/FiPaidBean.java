package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

@XBlinkAlias("billLading")
public class FiPaidBean  extends PrintBean{
	private Long paidId=0L;//ʵ��ʵ��ID
	private String paymentType; //�ո�����(1:�տ/2:���)(�������)
	private String documentsSmalltype; //����С�ࣺ���͵�/���˵�/���͵�/Ԥ��(�������)
	private String costType; //��������
	private Long documentsNo; //����С���Ӧ�ĵ���(�������)
	private String settlementAmount; // ʵ�ո����
	private String customerName; //���̱�����(�������)

	private String flightMainNo;//����������
	private Long piece;// ����
	private Double cusWeight;// �Ʒ�����
	private Double bulk;// ���
	private String sourceData; //������Դ(�������)
	private Long sourceId; //��Դ����(�������)
	private String addr;//�ջ��˵�ַ
	private String consignee;//�ջ�������
	private String consigneeTel;//�ջ��˵绰���ֻ� 
	
	private String printName;// ��ӡ��
	private Long printNum=0l;// ��ӡ����
	private String printTime;// ��ӡʱ��
	private String printId;// ��ӡ��¼��ID
	
	private Double sumAmount=0.0; // ʵ�ո��ܽ��
	
	public Long getPaidId() {
		return paidId;
	}
	public void setPaidId(Long paidId) {
		this.paidId = paidId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getDocumentsSmalltype() {
		return documentsSmalltype;
	}
	public void setDocumentsSmalltype(String documentsSmalltype) {
		this.documentsSmalltype = documentsSmalltype;
	}
	public Long getDocumentsNo() {
		return documentsNo;
	}
	public void setDocumentsNo(Long documentsNo) {
		this.documentsNo = documentsNo;
	}
	public String getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getFlightMainNo() {
		return flightMainNo;
	}
	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
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
	public String getSourceData() {
		return sourceData;
	}
	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
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

	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	public Long getSourceId() {
		return sourceId;
	}
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	
	@Override
	public String getSourceNo() {
		// REVIEW Auto-generated method stub
		return null;
	}
	@Override
	public void setSourceNo(String sourceNo) {
		// REVIEW Auto-generated method stub
		
	}
	public Double getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}
	
	
	
}
