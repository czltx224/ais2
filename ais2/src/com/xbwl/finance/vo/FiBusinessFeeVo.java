package com.xbwl.finance.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * author CaoZhili time Oct 17, 2011 4:09:16 PM
 */

public class FiBusinessFeeVo implements Serializable {

	private Long id;
	private String workflowNo;// ���̺�
	private Long belongDepartId;// ��������Id
	private String belongDepartName;// ������������+

	private Long customerId;// ����Id
	private String customerName;// ��������+

	private Double amount;// Ӧ�����
	private Double volume;// ����
	private String remark;// ժҪ
	private String collectionAccount;// �տ��ʺ�
	private String collectionBank;// �տ��˿�����
	private Long businessType;// ҵ�������(0:��˽,1:�Թ�)
	private Long status;// ���״̬(0:����,1:δ���,2:�����)
	private Double rate;// ����
	private Long departId;// ����ҵ����id
	private String departName;// ����ҵ����
	private String createName;// ������
	private Date createTime;// ����ʱ��
	private Date updateTime;// �޸�ʱ��
	private String updateName;// �޸���
	private String ts;// ʱ���
	private Long collectionCustomerId;// �տ����ID
	private String collectionCustomerName;// �տ��������+

	private String settlement;  //���㷽ʽ
	private Double turnover;  //Ӫҵ��
	private Long fiBusinessFeePriceId;  //Э���ID
	
	private String businessMonth;// ҵ���·�(2011-01)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkflowNo() {
		return workflowNo;
	}

	public void setWorkflowNo(String workflowNo) {
		this.workflowNo = workflowNo;
	}

	public Long getBelongDepartId() {
		return belongDepartId;
	}

	public void setBelongDepartId(Long belongDepartId) {
		this.belongDepartId = belongDepartId;
	}

	public String getBelongDepartName() {
		return belongDepartName;
	}

	public void setBelongDepartName(String belongDepartName) {
		this.belongDepartName = belongDepartName;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCollectionAccount() {
		return collectionAccount;
	}

	public void setCollectionAccount(String collectionAccount) {
		this.collectionAccount = collectionAccount;
	}

	public String getCollectionBank() {
		return collectionBank;
	}

	public void setCollectionBank(String collectionBank) {
		this.collectionBank = collectionBank;
	}

	public Long getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Long businessType) {
		this.businessType = businessType;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
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

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public Long getCollectionCustomerId() {
		return collectionCustomerId;
	}

	public void setCollectionCustomerId(Long collectionCustomerId) {
		this.collectionCustomerId = collectionCustomerId;
	}

	public String getCollectionCustomerName() {
		return collectionCustomerName;
	}

	public void setCollectionCustomerName(String collectionCustomerName) {
		this.collectionCustomerName = collectionCustomerName;
	}

	public String getBusinessMonth() {
		return businessMonth;
	}

	public void setBusinessMonth(String businessMonth) {
		this.businessMonth = businessMonth;
	}

	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	public Double getTurnover() {
		return turnover;
	}

	public void setTurnover(Double turnover) {
		this.turnover = turnover;
	}

	public Long getFiBusinessFeePriceId() {
		return fiBusinessFeePriceId;
	}

	public void setFiBusinessFeePriceId(Long fiBusinessFeePriceId) {
		this.fiBusinessFeePriceId = fiBusinessFeePriceId;
	}


}
