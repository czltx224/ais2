package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

@XBlinkAlias("billLading")
public class FiPaidBean  extends PrintBean{
	private Long paidId=0L;//实收实付ID
	private String paymentType; //收付类型(1:收款单/2:付款单)(必输参数)
	private String documentsSmalltype; //单据小类：配送单/对账单/配送单/预存款单(必输参数)
	private String costType; //费用类型
	private Long documentsNo; //单据小类对应的单号(必输参数)
	private String settlementAmount; // 实收付金额
	private String customerName; //客商表名称(必输参数)

	private String flightMainNo;//航空主单号
	private Long piece;// 件数
	private Double cusWeight;// 计费重量
	private Double bulk;// 体积
	private String sourceData; //数据来源(必输参数)
	private Long sourceId; //来源单号(必输参数)
	private String addr;//收货人地址
	private String consignee;//收货人姓名
	private String consigneeTel;//收货人电话和手机 
	
	private String printName;// 打印人
	private Long printNum=0l;// 打印次数
	private String printTime;// 打印时间
	private String printId;// 打印记录表ID
	
	private Double sumAmount=0.0; // 实收付总金额
	
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
