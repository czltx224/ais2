package com.xbwl.finance.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author shuw
 * time Oct 13, 2011 10:23:26 AM
 */

public class FiIncomeVo {
	private Long id;
	private Long  dno;
	private String sourceData;  //������Դ
	private Long therNo;   //��������
	private String customerName;   //����
	private Long customerId;
	private Long piece;
	private Double cqWeight;
	private Date createTime;
	private Date accounting;    //���ʱ��
	private Long certiStatus; 
	private String certiNo;
	private String flightMainNo;
	private Double therAmount;  //��������
	private Double yfzzAmount;  //Ԥ����ֵ��
	private Double dfzzAmount;   //������ֵ��
	private Double yfdsAmount;  // Ԥ�����ͷ�
	private Double dfdsAmount;  //�������ͷ�
	private Double yfzcAmount;  //Ԥ��ר����
	private Double dfzcAmount; //����ר����
	private Double totalAmount;  //�ܽ��
	private Long departId;   //���벿��
	private String departName;  //���벿��
	private String  inDepart;  //¼������
	private  Long inDepartId;  //¼������ID
	private String createName;
	
	private Long serviceDepartId;
	private String serviceDepartName;  //�ͷ�����
	private Long cashStatus;  //����״̬
	
	public Long getCashStatus() {
		return cashStatus;
	}
	public void setCashStatus(Long cashStatus) {
		this.cashStatus = cashStatus;
	}
	public Long getServiceDepartId() {
		return serviceDepartId;
	}
	public void setServiceDepartId(Long serviceDepartId) {
		this.serviceDepartId = serviceDepartId;
	}
	public String getServiceDepartName() {
		return serviceDepartName;
	}
	public void setServiceDepartName(String serviceDepartName) {
		this.serviceDepartName = serviceDepartName;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Long getDno() {
		return dno;
	}
	public void setDno(Long dno) {
		this.dno = dno;
	}
	public String getSourceData() {
		return sourceData;
	}
	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}
	public Long getTherNo() {
		return therNo;
	}
	public void setTherNo(Long therNo) {
		this.therNo = therNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getPiece() {
		return piece;
	}
	public void setPiece(Long piece) {
		this.piece = piece;
	}
	public Double getCqWeight() {
		return cqWeight;
	}
	public void setCqWeight(Double cqWeight) {
		this.cqWeight = cqWeight;
	}
	
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getAccounting() {
		return accounting;
	}
	public void setAccounting(Date accounting) {
		this.accounting = accounting;
	}
	public Long getCertiStatus() {
		return certiStatus;
	}
	public void setCertiStatus(Long certiStatus) {
		this.certiStatus = certiStatus;
	}
	public String getCertiNo() {
		return certiNo;
	}
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}
	public String getFlightMainNo() {
		return flightMainNo;
	}
	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}
	public Double getTherAmount() {
		return therAmount;
	}
	public void setTherAmount(Double therAmount) {
		this.therAmount = therAmount;
	}
	public Double getYfzzAmount() {
		return yfzzAmount;
	}
	public void setYfzzAmount(Double yfzzAmount) {
		this.yfzzAmount = yfzzAmount;
	}
	public Double getDfzzAmount() {
		return dfzzAmount;
	}
	public void setDfzzAmount(Double dfzzAmount) {
		this.dfzzAmount = dfzzAmount;
	}
	public Double getYfdsAmount() {
		return yfdsAmount;
	}
	public void setYfdsAmount(Double yfdsAmount) {
		this.yfdsAmount = yfdsAmount;
	}
	public Double getDfdsAmount() {
		return dfdsAmount;
	}
	public void setDfdsAmount(Double dfdsAmount) {
		this.dfdsAmount = dfdsAmount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	public Double getYfzcAmount() {
		return yfzcAmount;
	}
	public void setYfzcAmount(Double yfzcAmount) {
		this.yfzcAmount = yfzcAmount;
	}
	public Double getDfzcAmount() {
		return dfzcAmount;
	}
	public void setDfzcAmount(Double dfzcAmount) {
		this.dfzcAmount = dfzcAmount;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public void setInDepart(String inDepart) {
		this.inDepart = inDepart;
	}
	public void setInDepartId(Long inDepartId) {
		this.inDepartId = inDepartId;
	}
	public String getInDepart() {
		return inDepart;
	}
	public Long getInDepartId() {
		return inDepartId;
	}
}
