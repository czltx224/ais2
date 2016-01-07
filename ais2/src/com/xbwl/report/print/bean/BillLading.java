package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * @author Administrator
 * @createTime 10:52:48 AM
 * @updateName Administrator
 * @updateTime 10:52:48 AM
 * 提货单实体类
 */
@XBlinkAlias("billLading")
public class BillLading extends PrintBean {
	// 左上角文字
	private String context;

	// 投诉电话
	private String complainTel;

	// 客服员名字
	private String cusService;

	// 客服电话
	private String cusServiceTel;
	private String title;//标题
	
	private String toWhere;//去向
	private String startDepartName;//始发部门
	private String traceTo;//实际去向
	private String cqName;//发货代理
	private String flightInfo;//航班信息
	private String reachDate;//到达日期
	private String takeMode;//提货方式
	private String dno;//配送单号
	private String flightMainNo;//主单号
	private String subNo;//分单号
	private String consigneeInfo;//收货人信息
	private String piece;//件数
	private String weight;//重量
	private String consigneeFee;//提送费
	private String cpValueAddFee;//增值服务费(仓储费)
	private String paymentCollection;//到付运费(代收货款)
	private String countFee;//合计
	private String signType;//签单类型
	private String request;//送货要求(个性化要求)
	private String signRequire;//签收要求
	private String cardId;//签收人证件
	private String isUrgent;//特殊客户(VIP 专车 等通知放货)  默认普通
	private String distributionMode;//配送方式
	private String morningflag;//是否早班
	private String specialSign;//特殊字符(项，VIP，重)
	private String stockAddFee;//仓储增值服务费
	private String signMan;//签收人
	private String signTime;//签收时间
	private String bulk;//体积

	private String printName;// 打印人
	private Long printNum;// 打印次数
	private String printTime;// 打印时间
	private String printId;// 打印记录表ID
	private String sourceNo;// 来源ID

	public BillLading(){
		this.printNum=0l;
		this.cardId="";
		this.reachDate="";
		this.request="";
		this.signRequire="";
		this.signMan="";
		this.signTime="";
	}
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getComplainTel() {
		return complainTel;
	}

	public void setComplainTel(String complainTel) {
		this.complainTel = complainTel;
	}

	public String getCusService() {
		return cusService;
	}

	public void setCusService(String cusService) {
		this.cusService = cusService;
	}

	public String getCusServiceTel() {
		return cusServiceTel;
	}

	public void setCusServiceTel(String cusServiceTel) {
		this.cusServiceTel = cusServiceTel;
	}

	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

	public Long getPrintNum() {
		return printNum;
	}

	public void setPrintNum(Long printNum) {
		this.printNum = printNum;
	}

	public String getPrintTime() {
		return printTime;
	}

	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}

	public String getPrintId() {
		return printId;
	}

	public void setPrintId(String printId) {
		this.printId = printId;
	}

	public String getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}

	public String getToWhere() {
		return toWhere;
	}

	public void setToWhere(String toWhere) {
		this.toWhere = toWhere;
	}

	public String getStartDepartName() {
		return startDepartName;
	}

	public void setStartDepartName(String startDepartName) {
		this.startDepartName = startDepartName;
	}

	public String getTraceTo() {
		return traceTo;
	}

	public void setTraceTo(String traceTo) {
		this.traceTo = traceTo;
	}

	public String getCqName() {
		return cqName;
	}

	public void setCqName(String cqName) {
		this.cqName = cqName;
	}

	public String getFlightInfo() {
		return flightInfo;
	}

	public void setFlightInfo(String flightInfo) {
		this.flightInfo = flightInfo;
	}

	public String getReachDate() {
		return reachDate;
	}

	public void setReachDate(String reachDate) {
		this.reachDate = reachDate;
	}

	public String getTakeMode() {
		return takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	public String getDno() {
		return dno;
	}

	public void setDno(String dno) {
		this.dno = dno;
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

	public String getConsigneeInfo() {
		return consigneeInfo;
	}

	public void setConsigneeInfo(String consigneeInfo) {
		this.consigneeInfo = consigneeInfo;
	}

	public String getPiece() {
		return piece;
	}

	public void setPiece(String piece) {
		this.piece = piece;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getConsigneeFee() {
		return consigneeFee;
	}

	public void setConsigneeFee(String consigneeFee) {
		this.consigneeFee = consigneeFee;
	}

	public String getCpValueAddFee() {
		return cpValueAddFee;
	}

	public void setCpValueAddFee(String cpValueAddFee) {
		this.cpValueAddFee = cpValueAddFee;
	}

	public String getPaymentCollection() {
		return paymentCollection;
	}

	public void setPaymentCollection(String paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

	public String getCountFee() {
		return countFee;
	}

	public void setCountFee(String countFee) {
		this.countFee = countFee;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getSignRequire() {
		return signRequire;
	}

	public void setSignRequire(String signRequire) {
		this.signRequire = signRequire;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getIsUrgent() {
		return isUrgent;
	}

	public void setIsUrgent(String isUrgent) {
		this.isUrgent = isUrgent;
	}

	public String getMorningflag() {
		return morningflag;
	}

	public void setMorningflag(String morningflag) {
		this.morningflag = morningflag;
	}

	public String getDistributionMode() {
		return distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	public String getSpecialSign() {
		return specialSign;
	}

	public void setSpecialSign(String specialSign) {
		this.specialSign = specialSign;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStockAddFee() {
		return stockAddFee;
	}

	public void setStockAddFee(String stockAddFee) {
		this.stockAddFee = stockAddFee;
	}

	public String getSignMan() {
		return signMan;
	}

	public void setSignMan(String signMan) {
		this.signMan = signMan;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getBulk() {
		return bulk;
	}

	public void setBulk(String bulk) {
		this.bulk = bulk;
	}
}
