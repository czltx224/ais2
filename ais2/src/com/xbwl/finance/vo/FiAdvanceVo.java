package com.xbwl.finance.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.entity.FiAdvance;

/**
 * author shuw
 * time Sep 15, 2011 11:05:32 AM
 */

public class FiAdvanceVo extends FiAdvance {
	private Long id;
	private Long customerId;//客商ID(必输参数)
	private String customerName;//客商名称(必输参数)
	private Long settlementType;//收付类型(1:存款、2:取款)(必输参数)
	private Double settlementAmount; //结算金额(必输参数)
	private Double settlementBalance;//余额
	private String sourceData;//数据来源(必输参数)
	private Long sourceNo;//来源单号(必输参数)
	private String remark;//备注
	private String updateName;
	private Date updateTime;
	private String createName;
	private Date createTime;
	private Long createDeptid;
	private String createDept;
	private Long status;//状态(0:作废,1:正常)
	
	private Long fiAdvanceId;//预付款设置Id
	private Long departId;//所属部门
	private Long accountNum; //账号
	private String accountName;//账号名称
	private String bank;//开户行
	private String departName;  //部门名称
	private Double openingBalance;//余额
	
	public Double getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(Double openingBalance) {
		this.openingBalance = openingBalance;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getSettlementType() {
		return settlementType;
	}
	public void setSettlementType(Long settlementType) {
		this.settlementType = settlementType;
	}
	public Double getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public Double getSettlementBalance() {
		return settlementBalance;
	}
	public void setSettlementBalance(Double settlementBalance) {
		this.settlementBalance = settlementBalance;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateDeptid() {
		return createDeptid;
	}
	public void setCreateDeptid(Long createDeptid) {
		this.createDeptid = createDeptid;
	}
	public String getCreateDept() {
		return createDept;
	}
	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getFiAdvanceId() {
		return fiAdvanceId;
	}
	public void setFiAdvanceId(Long fiAdvanceId) {
		this.fiAdvanceId = fiAdvanceId;
	}
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	public Long getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(Long accountNum) {
		this.accountNum = accountNum;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	
	

}
