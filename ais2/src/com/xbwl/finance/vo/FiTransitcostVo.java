package com.xbwl.finance.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.entity.FiTransitcost;

/**
 * author shuw
 * time Oct 7, 2011 2:07:23 PM
 */

public class FiTransitcostVo{

	private Long tid;
	private Long dno;
	private Long status;
	private String departName;
	private Long departId;
	private String reviewUser;
	private Date reviewDate;
	private String reviewRemark;
	private String ts;
	
	private String gowhere;//供应商
	private Long gowhereId;//供应商
	private String distributionMode;//配送方式
	private String takeMode;//提货方式
	private String addr;//收货人地址
	private Double cqWeight;//代理重量
	private String areaRank;
	private Long piece;//件数
	private Double traFee;//中转费
	private Long feeAuditStatus;
	private Long confirmStatus;// 回单确认状态
	
	private String cpName;
	
	private Long createDepartId;
	private String createDepartName;
	private Long payStatus;
	private Double cpFee;
	private Double consigneeFee;
	
	private Double totalFee; 
	
	private Double deficitFee;
	private Date createTime;
	private Long cashStatus;
	private Long batchNo;
	private String filghtMainNo;
	private String conginee;
	private String subNo;
	
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

	public Long getDno() {
		return dno;
	}
	public void setDno(Long dno) {
		this.dno = dno;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}

	public String getReviewUser() {
		return reviewUser;
	}
	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	public String getReviewRemark() {
		return reviewRemark;
	}
	public void setReviewRemark(String reviewRemark) {
		this.reviewRemark = reviewRemark;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public String getDistributionMode() {
		return distributionMode;
	}
	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}
	public String getTakeMode() {
		return takeMode;
	}
	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Double getCqWeight() {
		return cqWeight;
	}
	public void setCqWeight(Double cqWeight) {
		this.cqWeight = cqWeight;
	}
	public String getAreaRank() {
		return areaRank;
	}
	public void setAreaRank(String areaRank) {
		this.areaRank = areaRank;
	}
	public Double getTraFee() {
		return traFee;
	}
	public void setTraFee(Double traFee) {
		this.traFee = traFee;
	}
	public Long getFeeAuditStatus() {
		return feeAuditStatus;
	}
	public void setFeeAuditStatus(Long feeAuditStatus) {
		this.feeAuditStatus = feeAuditStatus;
	}
	public Long getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(Long confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public String getGowhere() {
		return gowhere;
	}
	public void setGowhere(String gowhere) {
		this.gowhere = gowhere;
	}
	public Long getPiece() {
		return piece;
	}
	public void setPiece(Long piece) {
		this.piece = piece;
	}
	public Long getCashStatus() {
		return cashStatus;
	}
	public void setCashStatus(Long cashStatus) {
		this.cashStatus = cashStatus;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getGowhereId() {
		return gowhereId;
	}
	public void setGowhereId(Long gowhereId) {
		this.gowhereId = gowhereId;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	public Long getCreateDepartId() {
		return createDepartId;
	}
	public void setCreateDepartId(Long createDepartId) {
		this.createDepartId = createDepartId;
	}
	public String getCreateDepartName() {
		return createDepartName;
	}
	public void setCreateDepartName(String createDepartName) {
		this.createDepartName = createDepartName;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public Double getCpFee() {
		return cpFee;
	}
	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}
	public Double getConsigneeFee() {
		return consigneeFee;
	}
	public void setConsigneeFee(Double consigneeFee) {
		this.consigneeFee = consigneeFee;
	}
	public Double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	public Long getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Long payStatus) {
		this.payStatus = payStatus;
	}
	public Long getTid() {
		return tid;
	}
	public void setTid(Long tid) {
		this.tid = tid;
	}
	public Double getDeficitFee() {
		return deficitFee;
	}
	public void setDeficitFee(Double deficitFee) {
		this.deficitFee = deficitFee;
	}
	public Long getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}
}
