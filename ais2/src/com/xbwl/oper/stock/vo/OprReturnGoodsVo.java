package com.xbwl.oper.stock.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author CaoZhili time Jul 30, 2011 10:54:30 AM
 */
public class OprReturnGoodsVo implements Serializable {

	private Long id;// 序号
	private Long dno;// 配送单号
	private Long returnNum;// 返货件数
	private String returnType;// 返货类型
	private Long outNo;// 返货单号
	private String dutyParty;// 责任方
	private Double consigneeFee;// 提送费
	private Double paymentCollection;// 代收款
	private String returnComment;// 返货备注
	private Double returnCost;// 返货成本
	private String returnDepartName;// 返货部门名称
	private Long returnDepart;// 返货部门
	private String createName;// 创建人
	private Date createTime;// 创建时间
	private String updateName;// 修改人
	private Date updateTime;// 修改时间
	private String ts;// 时间戳
	private String outType;// 送货类型
	private Long status;//0,删除，1，正常，2，返货入库，3，返货出库
	private Long auditStatus;//审核状态 0、未审核，1、已审核

	private String cpName;// 代理公司客商名称
	private String flightNo;// 航班号
	private String consignee;// 收货人姓名
	private String consigneeTel;// 收货人电话
	private String addr;// 收货人地址
	private Double cqWeight;// 计费重量
	private String realGoWhere;// 去向
	private Double cusValueAddFee;// 客户增值服务费
	private String distributionMode;// 配送方式
	private String takeMode;// 提送方式
	private Double bulk;// 体积
	private Long faxPiece;//录单件
	private Double disWeight;//折合重量

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	public Long getReturnNum() {
		return returnNum;
	}

	public void setReturnNum(Long returnNum) {
		this.returnNum = returnNum;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public Long getOutNo() {
		return outNo;
	}

	public void setOutNo(Long outNo) {
		this.outNo = outNo;
	}

	public String getDutyParty() {
		return dutyParty;
	}

	public void setDutyParty(String dutyParty) {
		this.dutyParty = dutyParty;
	}

	public Double getConsigneeFee() {
		return consigneeFee;
	}

	public void setConsigneeFee(Double consigneeFee) {
		this.consigneeFee = consigneeFee;
	}

	public Double getPaymentCollection() {
		return paymentCollection;
	}

	public void setPaymentCollection(Double paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

	public String getReturnComment() {
		return returnComment;
	}

	public void setReturnComment(String returnComment) {
		this.returnComment = returnComment;
	}

	public Double getReturnCost() {
		return returnCost;
	}

	public void setReturnCost(Double returnCost) {
		this.returnCost = returnCost;
	}

	public String getReturnDepartName() {
		return returnDepartName;
	}

	public void setReturnDepartName(String returnDepartName) {
		this.returnDepartName = returnDepartName;
	}

	public Long getReturnDepart() {
		return returnDepart;
	}

	public void setReturnDepart(Long returnDepart) {
		this.returnDepart = returnDepart;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
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

	public String getRealGoWhere() {
		return realGoWhere;
	}

	public void setRealGoWhere(String realGoWhere) {
		this.realGoWhere = realGoWhere;
	}

	public Double getCusValueAddFee() {
		return cusValueAddFee;
	}

	public void setCusValueAddFee(Double cusValueAddFee) {
		this.cusValueAddFee = cusValueAddFee;
	}

	public String getDistributionMode() {
		return distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getTakeMode() {
		return takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	public Double getBulk() {
		return bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

	public Long getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Long auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Long getFaxPiece() {
		return faxPiece;
	}

	public void setFaxPiece(Long faxPiece) {
		this.faxPiece = faxPiece;
	}

	public Double getDisWeight() {
		return disWeight;
	}

	public void setDisWeight(Double disWeight) {
		this.disWeight = disWeight;
	}
	
}
