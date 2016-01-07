package com.xbwl.report.print.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.xblink.annotation.XBlinkAlias;

/**
 * author shuw
 * ���˵���ӡ
 * time Dec 7, 2011 5:00:36 PM
 */
@XBlinkAlias("billLading")
public class FiReconAccountBean extends PrintBean {

	private String customerPeople;    // ������ϵ��
	private String customerPhone;    // ����绰
	private String customerInfo;    // ������
	private String customerName;  //����
	private String flightMainNo;  //������
	private String subNo;
	private Long dno;
	private Long  piece;
	private Double weight;
	private Double bulk;
	private Double paymentAmount;  // ���ջ���
	private Double  cpFee;   //Ԥ�����ͷ�
	private Double cpValueAddFee;   //Ԥ����ֵ��
	private String  consignee;
	private String createTime;

	private String costType; //��������  �ֶ���Ҫ�������ֶ�����ϸ�������
	private Double amount;  //����
	private String createBank; //��������
	private String accountNum;  //�˺�
	private String accountName;  //�˺����� 
	private String createBank2; //��������
	private String accountNum2;  //�˺�
	private String accountName2;  //�˺����� 
	private String receconPeople ; //����Ա
	private String phone;
	
	private String departName;
	private Long reconId;  //����ID

	private String printName;// ��ӡ��
	private Long printNum=0l;// ��ӡ����
	private String printTime;// ��ӡʱ��
	private String printId;// ��ӡ��¼��ID
	private String sourceNo;// ��ԴID
	
	public FiReconAccountBean(String customerName ,String flightMainNo, String subNo, Long dno,
			Long piece, Double weight, String consignee, Date createTime,String costType,Double amount) {
		super();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		this.customerName=customerName;
		this.flightMainNo = flightMainNo;
		this.subNo = subNo;
		this.dno = dno;
		this.piece = piece;
		this.weight = weight;
		this.consignee = consignee;
		this.createTime = simpleDateFormat.format(createTime);
		this.costType=costType;
		this.amount=amount;
	}
	
	public  FiReconAccountBean() {
		super();
	}

	public String getCustomerPeople() {
		return customerPeople;
	}

	public void setCustomerPeople(String customerPeople) {
		this.customerPeople = customerPeople;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(String customerInfo) {
		this.customerInfo = customerInfo;
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

	public String getSubNo() {
		return subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	public Long getPiece() {
		return piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Double getCpFee() {
		return cpFee;
	}

	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}

	public Double getCpValueAddFee() {
		return cpValueAddFee;
	}

	public void setCpValueAddFee(Double cpValueAddFee) {
		this.cpValueAddFee = cpValueAddFee;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateBank() {
		return createBank;
	}

	public void setCreateBank(String createBank) {
		this.createBank = createBank;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCreateBank2() {
		return createBank2;
	}

	public void setCreateBank2(String createBank2) {
		this.createBank2 = createBank2;
	}

	public String getAccountNum2() {
		return accountNum2;
	}

	public void setAccountNum2(String accountNum2) {
		this.accountNum2 = accountNum2;
	}

	public String getAccountName2() {
		return accountName2;
	}

	public void setAccountName2(String accountName2) {
		this.accountName2 = accountName2;
	}

	public String getRececonPeople() {
		return receconPeople;
	}

	public void setRececonPeople(String receconPeople) {
		this.receconPeople = receconPeople;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
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

	public Long getReconId() {
		return reconId;
	}

	public void setReconId(Long reconId) {
		this.reconId = reconId;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBulk() {
		return bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}



}
