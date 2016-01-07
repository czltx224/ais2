package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * @author czl
 * @createTime 2012-03-16 03:19 AM
 * 
 * 更改通知单打印实体类
 */
@XBlinkAlias("billLading")
public class OprFaxChangeBean extends PrintBean{

	private String changeTitle;//更改通知单标题
	private String changeDepart;//更改部门
	private String changeName;//更改人
	private String changeDate;//更改日期
	private String changeNo;//更改单号
	
	private String cusName;//代理公司
	private String dno;//配送单号
	private String subNo;//分单号
	private String flightMainNo;//主单号
	private String consignee;//收货人姓名
	private String piece;//件数
	private String weight;//重量
	private String weightFee;//计费重量
	private String payWay;//付款方式
	private String goWhere;//去向
	private String distributionMode;//配送方式
	private String takeMode;//提货方式
	private String changeContent;//更改内容
	private String xbwlImagePath;//图片路径
	
	private String printName;// 打印人
	private Long printNum;// 打印次数
	private String printTime;// 打印时间
	private String printId;// 打印记录表ID
	private String sourceNo;// 来源ID
	
	public String getChangeTitle() {
		return changeTitle;
	}
	public void setChangeTitle(String changeTitle) {
		this.changeTitle = changeTitle;
	}
	public String getChangeDepart() {
		return changeDepart;
	}
	public void setChangeDepart(String changeDepart) {
		this.changeDepart = changeDepart;
	}
	public String getChangeName() {
		return changeName;
	}
	public void setChangeName(String changeName) {
		this.changeName = changeName;
	}
	public String getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}
	public String getChangeNo() {
		return changeNo;
	}
	public void setChangeNo(String changeNo) {
		this.changeNo = changeNo;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getDno() {
		return dno;
	}
	public void setDno(String dno) {
		this.dno = dno;
	}
	public String getSubNo() {
		return subNo;
	}
	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}
	public String getFlightMainNo() {
		return flightMainNo;
	}
	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
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
	public String getWeightFee() {
		return weightFee;
	}
	public void setWeightFee(String weightFee) {
		this.weightFee = weightFee;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getGoWhere() {
		return goWhere;
	}
	public void setGoWhere(String goWhere) {
		this.goWhere = goWhere;
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
	public String getChangeContent() {
		return changeContent;
	}
	public void setChangeContent(String changeContent) {
		this.changeContent = changeContent;
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
	public String getXbwlImagePath() {
		return xbwlImagePath;
	}
	public void setXbwlImagePath(String xbwlImagePath) {
		this.xbwlImagePath = xbwlImagePath;
	}
}
