package com.xbwl.finance.vo;

import com.xbwl.entity.FiPaymentabnormal;

public class FiPaymentabnormalVo extends FiPaymentabnormal {
	private Long paymentType; //�ո�����(�տ/���)
	private String costType; //��������:���ջ���/�������ͷ�/������ֵ��/Ԥ�����ͷ�/Ԥ����ֵ��/��������/����
	private String documentsType; //���ݴ���:����\�ɱ�\����\Ԥ���\���ջ���
	private String documentsSmalltype; //����С�ࣺ���͵�/���˵�/���͵�/Ԥ��
	private Long documentsNo; //����С���Ӧ�ĵ���
	private String penyType; //��������(�ֽᡢ�½�)
	private String contacts; //������λ:û�ڿ��̵����еĿ��̣��ڲ��ͻ���
	private Long customerId; //����id
	private String customerName; //���̱�����
	private String sourceData; //������Դ
	private Long sourceNo; //��Դ����
	private String typeName; //�쳣����


	public Long getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Long paymentType) {
		this.paymentType = paymentType;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
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

	public Long getDocumentsNo() {
		return documentsNo;
	}

	public void setDocumentsNo(Long documentsNo) {
		this.documentsNo = documentsNo;
	}

	public String getPenyType() {
		return penyType;
	}

	public void setPenyType(String penyType) {
		this.penyType = penyType;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}
