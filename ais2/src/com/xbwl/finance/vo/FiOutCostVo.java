package com.xbwl.finance.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 *@author LiuHao
 *@time Aug 29, 2011 6:20:02 PM
 */
public class FiOutCostVo {
	private Long id;
	private Long dno;//配送单号
	private String outcostNo;//外发单号
	private Long customerId;//客商ID
	private String customerName;//客商名称
	private Double amount;//外发成本
	private Long outuserId;//外发员
	private Long isdelete;//是否删除
	private String remark;//备注
	private String updateName;
	private Date updateTime;
	private String createName;
	private Date createTime;
	private Long departId;//创建部门ID
	private String departName;//创建部门
	private String ts;
	private String takeMode;//提货方式
	private Long piece;//件数
	private Double cusWeight;//重量
	private Double paymentCollection;//代收货款
	private String addr;
	private Double cusFee;
	private Long status;//状态 0未审核 1已审核
	private String distributionMode;
	
	private String sourceData;
	private Long sourceNo;
	private Double sourceAmount;
	
	private Long payStatus;
	
	private Double cpFee;
	private String filghtMainNo;
	private String conginee;
	private String subNo;
	
	private Double totalFee; // 总收入 
	private Date outStockDate;  //出库时间
	private Double deficitFee;// 亏损
	
	private String cusDepartName;
	private String cusDepartCode;
	
	private Long batchNo;
	private String cpName;
	
	private String reviewUser;//审核人
	private Date reviewDate;//审核时间
	
	public String getReviewUser() {
		return reviewUser;
	}
	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}
	public Date getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the dno
	 */
	public Long getDno() {
		return dno;
	}
	/**
	 * @param dno the dno to set
	 */
	public void setDno(Long dno) {
		this.dno = dno;
	}
	/**
	 * @return the outcostNo
	 */
	public String getOutcostNo() {
		return outcostNo;
	}
	/**
	 * @param outcostNo the outcostNo to set
	 */
	public void setOutcostNo(String outcostNo) {
		this.outcostNo = outcostNo;
	}
	/**
	 * @return the customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/**
	 * @return the outuserId
	 */
	public Long getOutuserId() {
		return outuserId;
	}
	/**
	 * @param outuserId the outuserId to set
	 */
	public void setOutuserId(Long outuserId) {
		this.outuserId = outuserId;
	}
	/**
	 * @return the isdelete
	 */
	public Long getIsdelete() {
		return isdelete;
	}
	/**
	 * @param isdelete the isdelete to set
	 */
	public void setIsdelete(Long isdelete) {
		this.isdelete = isdelete;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the updateName
	 */
	public String getUpdateName() {
		return updateName;
	}
	/**
	 * @param updateName the updateName to set
	 */
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	/**
	 * @return the updateTime
	 */
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the createName
	 */
	public String getCreateName() {
		return createName;
	}
	/**
	 * @param createName the createName to set
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	/**
	 * @return the createTime
	 */
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the createDeptid
	 */

	/**
	 * @return the ts
	 */
	public String getTs() {
		return ts;
	}
	/**
	 * @param ts the ts to set
	 */
	public void setTs(String ts) {
		this.ts = ts;
	}
	/**
	 * @return the takeMode
	 */
	public String getTakeMode() {
		return takeMode;
	}
	/**
	 * @param takeMode the takeMode to set
	 */
	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}
	/**
	 * @return the piece
	 */
	public Long getPiece() {
		return piece;
	}
	/**
	 * @param piece the piece to set
	 */
	public void setPiece(Long piece) {
		this.piece = piece;
	}
	/**
	 * @return the cusWeight
	 */
	public Double getCusWeight() {
		return cusWeight;
	}
	/**
	 * @param cusWeight the cusWeight to set
	 */
	public void setCusWeight(Double cusWeight) {
		this.cusWeight = cusWeight;
	}
	/**
	 * @return the paymentCollection
	 */
	public Double getPaymentCollection() {
		return paymentCollection;
	}
	/**
	 * @param paymentCollection the paymentCollection to set
	 */
	public void setPaymentCollection(Double paymentCollection) {
		this.paymentCollection = paymentCollection;
	}
	/**
	 * @return the cusFee
	 */
	public Double getCusFee() {
		return cusFee;
	}
	/**
	 * @param cusFee the cusFee to set
	 */
	public void setCusFee(Double cusFee) {
		this.cusFee = cusFee;
	}
	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}
	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	/**
	 * @return the status
	 */
	public Long getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}
	/**
	 * @return the distributionMode
	 */
	public String getDistributionMode() {
		return distributionMode;
	}
	/**
	 * @param distributionMode the distributionMode to set
	 */
	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
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
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public String getFilghtMainNo() {
		return filghtMainNo;
	}
	public void setFilghtMainNo(String filghtMainNo) {
		this.filghtMainNo = filghtMainNo;
	}
	public String getConginee() {
		return conginee;
	}
	public void setConginee(String conginee) {
		this.conginee = conginee;
	}
	public String getSubNo() {
		return subNo;
	}
	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}
	public Double getCpFee() {
		return cpFee;
	}
	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}
	public Double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	public Double getDeficitFee() {
		return deficitFee;
	}
	public void setDeficitFee(Double deficitFee) {
		this.deficitFee = deficitFee;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getOutStockDate() {
		return outStockDate;
	}
	public void setOutStockDate(Date outStockDate) {
		this.outStockDate = outStockDate;
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
	public Double getSourceAmount() {
		return sourceAmount;
	}
	public void setSourceAmount(Double sourceAmount) {
		this.sourceAmount = sourceAmount;
	}
	public Long getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Long payStatus) {
		this.payStatus = payStatus;
	}
	public Long getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}
	public String getCusDepartName() {
		return cusDepartName;
	}
	public void setCusDepartName(String cusDepartName) {
		this.cusDepartName = cusDepartName;
	}
	public String getCusDepartCode() {
		return cusDepartCode;
	}
	public void setCusDepartCode(String cusDepartCode) {
		this.cusDepartCode = cusDepartCode;
	}
	
}
