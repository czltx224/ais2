package com.xbwl.oper.receipt.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author CaoZhili time Jul 25, 2011 6:10:23 PM
 * 
 * 回单VO
 */
public class OprReceiptVo implements Serializable {

	private Long id;// 序号
	private Long dno;// 配送单号
	private Long printNo;// 打印单据号
	private Long printNum;// 打印次数
	private String receiptType;// 回单类型
	private Long reachStatus;// 拆单状态
	private Long reachNum;// 拆单份数
	private String reachMan;// 拆单人 即点到人
	private Date reachTime;// 拆单时间
	private Long getStatus;// 领单状态
	private String getMan;// 领单人 即出库人
	private Date getTime;// 领单时间
	private Long confirmStatus;// 回单确认状态 (0:未确认，1：正常，2：异常)
	private Long confirmNum;// 确收份数
	private String confirmMan;// 确收人
	private Date confirmTime;// 确收时间
	private String confirmRemark;// 确收备注(异常)
	private Long outStatus;// 回单寄出状态
	private String outWay;// 回单寄出途径 来自数据字典，主要包括 ，传真，邮寄，快递等等
	private String outNo;// 回单寄出单号
	private String outMan;// 回单寄出人
	private Date outTime;// 回单寄出时间
	private String outCompany;// 回单寄出公司
	private Double outCost;// 回单寄出费用
	private Long scanStauts;// 回单扫描状态
	private String scanMan;// 回单扫描人
	private Date scanTime;// 回单扫描时间
	private String scanAddr;// 回单扫描地址
	private String createName;// 创建人
	private Date createTime;// 创建时间
	private String updateName;// 修改人
	private Date updateTime;// 修改时间
	private String ts;// 时间戳
	private String curStatus;// 回单当前状态

	private String distributionMode;// 配送方式
	private String gowhere;// 去向

	private String cpName;// 代理公司客商名称
	private Long prewiredId;// 预配单号
	private Long overmemoId;// 实配单号
	private String request;// 特殊要求
	private Long urgentStatus;
	
	private String goodsStatus;

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

	public Long getPrintNo() {
		return printNo;
	}

	public void setPrintNo(Long printNo) {
		this.printNo = printNo;
	}

	public Long getPrintNum() {
		return printNum;
	}

	public void setPrintNum(Long printNum) {
		this.printNum = printNum;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public Long getReachStatus() {
		return reachStatus;
	}

	public void setReachStatus(Long reachStatus) {
		this.reachStatus = reachStatus;
	}

	public Long getReachNum() {
		return reachNum;
	}

	public void setReachNum(Long reachNum) {
		this.reachNum = reachNum;
	}

	public String getReachMan() {
		return reachMan;
	}

	public void setReachMan(String reachMan) {
		this.reachMan = reachMan;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getReachTime() {
		return reachTime;
	}

	public void setReachTime(Date reachTime) {
		this.reachTime = reachTime;
	}

	public Long getGetStatus() {
		return getStatus;
	}

	public void setGetStatus(Long getStatus) {
		this.getStatus = getStatus;
	}

	public String getGetMan() {
		return getMan;
	}

	public void setGetMan(String getMan) {
		this.getMan = getMan;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getGetTime() {
		return getTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}

	public Long getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(Long confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public Long getConfirmNum() {
		return confirmNum;
	}

	public void setConfirmNum(Long confirmNum) {
		this.confirmNum = confirmNum;
	}

	public String getConfirmMan() {
		return confirmMan;
	}

	public void setConfirmMan(String confirmMan) {
		this.confirmMan = confirmMan;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getConfirmRemark() {
		return confirmRemark;
	}

	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}

	public Long getOutStatus() {
		return outStatus;
	}

	public void setOutStatus(Long outStatus) {
		this.outStatus = outStatus;
	}

	public String getOutWay() {
		return outWay;
	}

	public void setOutWay(String outWay) {
		this.outWay = outWay;
	}

	public String getOutNo() {
		return outNo;
	}

	public void setOutNo(String outNo) {
		this.outNo = outNo;
	}

	public String getOutCompany() {
		return outCompany;
	}

	public void setOutCompany(String outCompany) {
		this.outCompany = outCompany;
	}

	public Double getOutCost() {
		return outCost;
	}

	public void setOutCost(Double outCost) {
		this.outCost = outCost;
	}

	public Long getScanStauts() {
		return scanStauts;
	}

	public void setScanStauts(Long scanStauts) {
		this.scanStauts = scanStauts;
	}

	public String getScanMan() {
		return scanMan;
	}

	public void setScanMan(String scanMan) {
		this.scanMan = scanMan;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getScanTime() {
		return scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

	public String getScanAddr() {
		return scanAddr;
	}

	public void setScanAddr(String scanAddr) {
		this.scanAddr = scanAddr;
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

	public String getCurStatus() {
		return curStatus;
	}

	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}

	public String getDistributionMode() {
		return distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	public String getGowhere() {
		return gowhere;
	}

	public void setGowhere(String gowhere) {
		this.gowhere = gowhere;
	}

	public String getOutMan() {
		return outMan;
	}

	public void setOutMan(String outMan) {
		this.outMan = outMan;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public Long getPrewiredId() {
		return prewiredId;
	}

	public void setPrewiredId(Long prewiredId) {
		this.prewiredId = prewiredId;
	}

	public Long getOvermemoId() {
		return overmemoId;
	}

	public void setOvermemoId(Long overmemoId) {
		this.overmemoId = overmemoId;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public Long getUrgentStatus() {
		return urgentStatus;
	}

	public void setUrgentStatus(Long urgentStatus) {
		this.urgentStatus = urgentStatus;
	}
	
}
