package com.xbwl.oper.stock.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
public class OprExceptionStockVo implements java.io.Serializable {

	private Long id;
	private Long dno;// 配送单号
	private Long piece;// 异常库存件数
	private Double weight;// 异常库存重量
	private String consignee;// 收货人
	private String cpName;// 发货代理
	private String exceptionEnterName;// 异常入库人
	private Date exceptionEnterTime;// 异常入库时间
	private String exceptionOutName;// 异常出库人
	private Date exceptionOutTime;// 异常出库时间
	private Long exceptionStatus;// 状态0：作废，1：正常，2：入库，3：出库
	private Long departId;// 业务部门ID
	private String departName;// 业务部门
	private String consigneeAddr;// 收货人地址
	private String outStockNo;// 出库单号
	private String gowhere;// 原去向
	private String distributionMode;// 配送方式
	private String returnType;// 返货类型
	private String sourceType;// 来源类型 分为返货，正常转异常，等等，来源数据字典
	private Double configneeFee;// 到付总额
	private Double cpFee;// 预付总额
	private Double paymentCollection;// 代收货款
	private String outStockObj;// 返货出库对象
	private String sourceNo;// 来源单号
	private Double addConfigneeFee;// 追加到付提送费
	private Double addCpFee;// 追加预付提送费
	private Double outCost;// 出库成本
	private String createName;// 创建人
	private Date createTime;// 创建时间
	private String updateName;// 修改人
	private Date updateTime;// 修改时间
	private String ts;// 时间戳

	private String flightNo;// 航班号
	private Date flightDate;// 航班日期
	private String flightMainNo;// 航空主单号
	private String subNo;// 分单号
	private String takeMode;// 提货方式
	private Date faxCreateDate;// 传真时间
	private String goods;// 品名
	private Long faxPiece;// 录单件数
	private Double cusWeight;// 计费重量
	private Double faxBulk;// 体积
	private String goodsStatus;// 货物当前状态

	private String outSender;//送货员
	private String outLoad;//出库途径
	private String outStockObjName;//出库对象名称
	private String outRemark;//出库备注
	
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

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getExceptionEnterName() {
		return exceptionEnterName;
	}

	public void setExceptionEnterName(String exceptionEnterName) {
		this.exceptionEnterName = exceptionEnterName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")  
	public Date getExceptionEnterTime() {
		return exceptionEnterTime;
	}

	public void setExceptionEnterTime(Date exceptionEnterTime) {
		this.exceptionEnterTime = exceptionEnterTime;
	}

	public String getExceptionOutName() {
		return exceptionOutName;
	}

	public void setExceptionOutName(String exceptionOutName) {
		this.exceptionOutName = exceptionOutName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")  
	public Date getExceptionOutTime() {
		return exceptionOutTime;
	}

	public void setExceptionOutTime(Date exceptionOutTime) {
		this.exceptionOutTime = exceptionOutTime;
	}

	public Long getExceptionStatus() {
		return exceptionStatus;
	}

	public void setExceptionStatus(Long exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
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

	public String getConsigneeAddr() {
		return consigneeAddr;
	}

	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	public String getOutStockNo() {
		return outStockNo;
	}

	public void setOutStockNo(String outStockNo) {
		this.outStockNo = outStockNo;
	}

	public String getGowhere() {
		return gowhere;
	}

	public void setGowhere(String gowhere) {
		this.gowhere = gowhere;
	}

	public String getDistributionMode() {
		return distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public Double getConfigneeFee() {
		return configneeFee;
	}

	public void setConfigneeFee(Double configneeFee) {
		this.configneeFee = configneeFee;
	}

	public Double getCpFee() {
		return cpFee;
	}

	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}

	public Double getPaymentCollection() {
		return paymentCollection;
	}

	public void setPaymentCollection(Double paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

	public String getOutStockObj() {
		return outStockObj;
	}

	public void setOutStockObj(String outStockObj) {
		this.outStockObj = outStockObj;
	}

	public String getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}

	public Double getAddConfigneeFee() {
		return addConfigneeFee;
	}

	public void setAddConfigneeFee(Double addConfigneeFee) {
		this.addConfigneeFee = addConfigneeFee;
	}

	public Double getAddCpFee() {
		return addCpFee;
	}

	public void setAddCpFee(Double addCpFee) {
		this.addCpFee = addCpFee;
	}

	public Double getOutCost() {
		return outCost;
	}

	public void setOutCost(Double outCost) {
		this.outCost = outCost;
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

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")  
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

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	@JSON(format="yyyy-MM-dd")  
	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
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

	public String getTakeMode() {
		return takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")  
	public Date getFaxCreateDate() {
		return faxCreateDate;
	}

	public void setFaxCreateDate(Date faxCreateDate) {
		this.faxCreateDate = faxCreateDate;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public Long getFaxPiece() {
		return faxPiece;
	}

	public void setFaxPiece(Long faxPiece) {
		this.faxPiece = faxPiece;
	}

	public Double getCusWeight() {
		return cusWeight;
	}

	public void setCusWeight(Double cusWeight) {
		this.cusWeight = cusWeight;
	}

	public Double getFaxBulk() {
		return faxBulk;
	}

	public void setFaxBulk(Double faxBulk) {
		this.faxBulk = faxBulk;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public String getOutSender() {
		return outSender;
	}

	public void setOutSender(String outSender) {
		this.outSender = outSender;
	}

	public String getOutLoad() {
		return outLoad;
	}

	public void setOutLoad(String outLoad) {
		this.outLoad = outLoad;
	}

	public String getOutStockObjName() {
		return outStockObjName;
	}

	public void setOutStockObjName(String outStockObjName) {
		this.outStockObjName = outStockObjName;
	}

	public String getOutRemark() {
		return outRemark;
	}

	public void setOutRemark(String outRemark) {
		this.outRemark = outRemark;
	}

}
