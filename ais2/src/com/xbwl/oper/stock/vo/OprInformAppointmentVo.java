package com.xbwl.oper.stock.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author CaoZhili time Jul 18, 2011 2:39:22 PM
 */
public class OprInformAppointmentVo implements java.io.Serializable {

	private Long dno;// 配送单号
	private Long cusId;// 代理公司客商编码
	private String cpName;// 代理公司客商名称
	private String flightNo;// 航班号
	private Date flightDate;// 航班日期
	private String flightTime;// 航班时间
	private String trafficMode;// 运输方式
	private String flightMainNo;// 航空主单号
	private String subNo;// 分单号
	private String distributionMode;// 配送方式
	private String takeMode;// 提货方式
	private String receiptType;// 回单类型
	private String consignee;// 收货人姓名
	private String consigneeTel;// 收货人电话
	private String consigneePho;// 收货人手机
	private String city;// 收货人所在市
	private String town;// 收货人所在区或者镇
	private String addr;// 收货人地址
	private Long piece;// 件数
	private Double cusWeight;// 计费重量
	private Double bulk;// 体积
	private String goodStatus;// 货物状态
	private Double cpRate;// 代理应付费率
	private Double cpFee;// 代理应付费（预付）
	private Double paymentCollection;
	private Double consigneeRate;// 收货人应付费率
	private Double consigneeFee;// 收货人应付费（到付）(提送费)
	private String customerService;// 客服员
	private Double cusValueAddFee;// 客户增值服务费
	private Double cpValueAddFee;// 增值服务费总额
	private String areaType;// 收货人地址类型

	private Long oprInformAppointmentId;// 通知预约表中的id
	private Long informNum;// 通知次数
	private Date lastInformTime;// 最后通知时间
	private String lastServiceName;// 最后通知客服员
	private String lastInformCus;// 最后通知客户
	private Long lastInformResult;// 最后通知结果
	private Long notifyStatus;// 预约状态 预约成功后要修改此状态（来自opr_status 表）
	private Long reachStatus;

	private String request;
	private Long stockPiece;
	private Long realPiece;

	private String informType;

	private String requestStage;// 执行阶段
	private Double countFee;// 收货人应付合计费用
	private String ts;
	private Long departId;
	private Long outStatus;

	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	public Long getCusId() {
		return cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
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

	@JSON(format = "yyyy-MM-dd hh:mm:ss")
	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	public String getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

	public String getTrafficMode() {
		return trafficMode;
	}

	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
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

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
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

	public String getConsigneePho() {
		return consigneePho;
	}

	public void setConsigneePho(String consigneePho) {
		this.consigneePho = consigneePho;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Long getPiece() {
		return piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	public Double getCusWeight() {
		return cusWeight;
	}

	public void setCusWeight(Double cusWeight) {
		this.cusWeight = cusWeight;
	}

	public Double getBulk() {
		return bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

	public Double getCpRate() {
		return cpRate;
	}

	public void setCpRate(Double cpRate) {
		this.cpRate = cpRate;
	}

	public Double getCpFee() {
		return cpFee;
	}

	public void setCpFee(Double cpFee) {
		this.cpFee = cpFee;
	}

	public Double getConsigneeRate() {
		return consigneeRate;
	}

	public void setConsigneeRate(Double consigneeRate) {
		this.consigneeRate = consigneeRate;
	}

	public Double getConsigneeFee() {
		return consigneeFee;
	}

	public void setConsigneeFee(Double consigneeFee) {
		this.consigneeFee = consigneeFee;
	}

	public String getCustomerService() {
		return customerService;
	}

	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public Long getInformNum() {
		return informNum;
	}

	public void setInformNum(Long informNum) {
		this.informNum = informNum;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public Long getStockPiece() {
		return stockPiece;
	}

	public void setStockPiece(Long stockPiece) {
		this.stockPiece = stockPiece;
	}

	public Long getRealPiece() {
		return realPiece;
	}

	public void setRealPiece(Long realPiece) {
		this.realPiece = realPiece;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getLastInformTime() {
		return lastInformTime;
	}

	public void setLastInformTime(Date lastInformTime) {
		this.lastInformTime = lastInformTime;
	}

	public String getLastServiceName() {
		return lastServiceName;
	}

	public void setLastServiceName(String lastServiceName) {
		this.lastServiceName = lastServiceName;
	}

	public String getLastInformCus() {
		return lastInformCus;
	}

	public void setLastInformCus(String lastInformCus) {
		this.lastInformCus = lastInformCus;
	}

	public Long getLastInformResult() {
		return lastInformResult;
	}

	public void setLastInformResult(Long lastInformResult) {
		this.lastInformResult = lastInformResult;
	}

	public String getGoodStatus() {
		return goodStatus;
	}

	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}

	public Double getCountFee() {
		// 写出计算公式
		this.countFee = new Double(0);
		return this.getCpFee() + this.getConsigneeFee()
				+ this.getPaymentCollection() + this.getCpValueAddFee();
		// return this.countFee+1;
	}

	public void setCountFee(Double countFee) {
		this.countFee = countFee;
	}

	public Long getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(Long notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public Long getReachStatus() {
		return reachStatus;
	}

	public void setReachStatus(Long reachStatus) {
		this.reachStatus = reachStatus;
	}

	public Double getPaymentCollection() {
		return paymentCollection;
	}

	public void setPaymentCollection(Double paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

	public String getRequestStage() {
		return requestStage;
	}

	public void setRequestStage(String requestStage) {
		this.requestStage = requestStage;
	}

	public Double getCusValueAddFee() {
		return cusValueAddFee;
	}

	public void setCusValueAddFee(Double cusValueAddFee) {
		this.cusValueAddFee = cusValueAddFee;
	}

	public Double getCpValueAddFee() {
		return cpValueAddFee;
	}

	public void setCpValueAddFee(Double cpValueAddFee) {
		this.cpValueAddFee = cpValueAddFee;
	}

	public Long getOprInformAppointmentId() {
		return oprInformAppointmentId;
	}

	public void setOprInformAppointmentId(Long oprInformAppointmentId) {
		this.oprInformAppointmentId = oprInformAppointmentId;
	}

	public String getInformType() {
		return informType;
	}

	public void setInformType(String informType) {
		this.informType = informType;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public Long getOutStatus() {
		return outStatus;
	}

	public void setOutStatus(Long outStatus) {
		this.outStatus = outStatus;
	}

	public OprInformAppointmentVo() {
		super();
	}
	
	
}
