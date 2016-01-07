package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * author CaoZhili time Oct 27, 2011 6:50:32 PM
 * 实配清单实体类
 */
@XBlinkAlias("billLading")
public class OvermemoListBean extends PrintBean {

	private String startDepartName;// 出发部门
	private String overmemoId;// 实配单号
	private String dno;// 配送单号
	private String consigneeInfo;// 收货人信息
	private String toWhere;// 实配去向
	private String autostowMode;// 配载方式
	private String piece;// 件数
	private String weight;// 重量
	private String cqName;// 发货代理
	private String flightMainNo;// 主单号
	private String subNo;// 分单号
	private String realPiece;// 实配件数
	private String signNun;// 签单份数
	// 到付运费，提送货费，应付成本
	private String consigneeFee;// 提送货费（到付提送费consigneeFee+到付增值服务费cusValueAddFee）
	private String paymentCollection;// 到付运费(代收货款)
	private String traFee;//应付成本（中转费）
	private String request;//客户要求
	private String cpFee;//预付提送费（预付提送费cpFee+预付增值服务费cpValueAddFee）

	private String sendName;// 送货员
	private String autostowName;// 配载员
	private String sendNameTel;// 送货员电话
	private String totalTicket;// 总票数
	private String totalPiece;// 总件数
	private String totalWeight;// 总重量
	private String loadingGroup;// 装车组
	private String driverName;
	private String dispatchGroup;// 分拨组
	private String routeNumber;// 车次号
	private String cargoName;//配载员
	private String shouru;//收入 到付+预付+专车+增值

	private String printName;// 打印人
	private Long printNum;// 打印次数
	private String printTime;// 打印时间
	private String printId;// 打印记录表ID
	private String sourceNo;// 来源ID

	public OvermemoListBean() {
		this.toWhere = "";
		this.signNun = "";
	}

	public String getStartDepartName() {
		return startDepartName;
	}

	public void setStartDepartName(String startDepartName) {
		this.startDepartName = startDepartName;
	}

	public String getOvermemoId() {
		return overmemoId;
	}

	public void setOvermemoId(String overmemoId) {
		this.overmemoId = overmemoId;
	}

	public String getDno() {
		return dno;
	}

	public void setDno(String dno) {
		this.dno = dno;
	}

	public String getConsigneeInfo() {
		return consigneeInfo;
	}

	public void setConsigneeInfo(String consigneeInfo) {
		this.consigneeInfo = consigneeInfo;
	}

	public String getToWhere() {
		return toWhere;
	}

	public void setToWhere(String toWhere) {
		this.toWhere = toWhere;
	}

	public String getAutostowMode() {
		return autostowMode;
	}

	public void setAutostowMode(String autostowMode) {
		this.autostowMode = autostowMode;
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

	public String getCqName() {
		return cqName;
	}

	public void setCqName(String cqName) {
		this.cqName = cqName;
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

	public String getRealPiece() {
		return realPiece;
	}

	public void setRealPiece(String realPiece) {
		this.realPiece = realPiece;
	}

	public String getSignNun() {
		return signNun;
	}

	public void setSignNun(String signNun) {
		this.signNun = signNun;
	}

	public String getConsigneeFee() {
		return consigneeFee;
	}

	public void setConsigneeFee(String consigneeFee) {
		this.consigneeFee = consigneeFee;
	}

	public String getPaymentCollection() {
		return paymentCollection;
	}

	public void setPaymentCollection(String paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getAutostowName() {
		return autostowName;
	}

	public void setAutostowName(String autostowName) {
		this.autostowName = autostowName;
	}

	public String getSendNameTel() {
		return sendNameTel;
	}

	public void setSendNameTel(String sendNameTel) {
		this.sendNameTel = sendNameTel;
	}

	public String getTotalTicket() {
		return totalTicket;
	}

	public void setTotalTicket(String totalTicket) {
		this.totalTicket = totalTicket;
	}

	public String getTotalPiece() {
		return totalPiece;
	}

	public void setTotalPiece(String totalPiece) {
		this.totalPiece = totalPiece;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getLoadingGroup() {
		return loadingGroup;
	}

	public void setLoadingGroup(String loadingGroup) {
		this.loadingGroup = loadingGroup;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDispatchGroup() {
		return dispatchGroup;
	}

	public void setDispatchGroup(String dispatchGroup) {
		this.dispatchGroup = dispatchGroup;
	}

	public String getRouteNumber() {
		return routeNumber;
	}

	public void setRouteNumber(String routeNumber) {
		this.routeNumber = routeNumber;
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
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

	public String getTraFee() {
		return traFee;
	}

	public void setTraFee(String traFee) {
		this.traFee = traFee;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getCpFee() {
		return cpFee;
	}

	public void setCpFee(String cpFee) {
		this.cpFee = cpFee;
	}

	public String getShouru() {
		return shouru;
	}

	public void setShouru(String shouru) {
		this.shouru = shouru;
	}
}
