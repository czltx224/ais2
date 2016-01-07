package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * 运单标签打印实体类
 * @project ais2.0
 * @author czl
 * @Time Feb 17, 2012 9:43:54 AM
 */
@XBlinkAlias("billLading")
public class OprWaybillLabelBean extends PrintBean {

	private String flightMainNo;//主单号
	private String addr;//收货人地址
	private String consignee;//收货人名称
	private String startDepart;//始发部门
	private String transMode;//运输方式
	private String dno;//配送单号
	private String piece;//录单件数
	private String faxInTime;//录单时间
	private String isRound;//是否需要圈起来
	
	private String printName;// 打印人
	private Long printNum;// 打印次数
	private String printTime;// 打印时间
	private String printId;// 打印记录表ID
	private String sourceNo;// 来源ID
	
	public String getFlightMainNo() {
		return flightMainNo;
	}
	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getStartDepart() {
		return startDepart;
	}
	public void setStartDepart(String startDepart) {
		this.startDepart = startDepart;
	}
	public String getTransMode() {
		return transMode;
	}
	public void setTransMode(String transMode) {
		this.transMode = transMode;
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
	public String getDno() {
		return dno;
	}
	public void setDno(String dno) {
		this.dno = dno;
	}
	public String getPiece() {
		return piece;
	}
	public void setPiece(String piece) {
		this.piece = piece;
	}
	public String getFaxInTime() {
		return faxInTime;
	}
	public void setFaxInTime(String faxInTime) {
		this.faxInTime = faxInTime;
	}
	public String getIsRound() {
		return isRound;
	}
	public void setIsRound(String isRound) {
		this.isRound = isRound;
	}
}
