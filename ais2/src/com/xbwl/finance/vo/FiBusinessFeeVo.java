package com.xbwl.finance.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * author CaoZhili time Oct 17, 2011 4:09:16 PM
 */

public class FiBusinessFeeVo implements Serializable {

	private Long id;
	private String workflowNo;// 流程号
	private Long belongDepartId;// 所属部门Id
	private String belongDepartName;// 所属部门名称+

	private Long customerId;// 客商Id
	private String customerName;// 客商名称+

	private Double amount;// 应付金额
	private Double volume;// 货量
	private String remark;// 摘要
	private String collectionAccount;// 收款帐号
	private String collectionBank;// 收款人开户行
	private Long businessType;// 业务费类型(0:对私,1:对公)
	private Long status;// 审核状态(0:作废,1:未审核,2:已审核)
	private Double rate;// 费率
	private Long departId;// 创建业务部门id
	private String departName;// 创建业务部门
	private String createName;// 创建人
	private Date createTime;// 创建时间
	private Date updateTime;// 修改时间
	private String updateName;// 修改人
	private String ts;// 时间戳
	private Long collectionCustomerId;// 收款客商ID
	private String collectionCustomerName;// 收款客商名称+

	private String settlement;  //计算方式
	private Double turnover;  //营业额
	private Long fiBusinessFeePriceId;  //协议价ID
	
	private String businessMonth;// 业务月份(2011-01)

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
