package com.xbwl.report.print.bean;

import org.xblink.annotation.XBlinkAlias;

/**
 * author CaoZhili time Nov 2, 2011 10:25:26 AM
 * 车辆签单实体类
 */
@XBlinkAlias("billLading")
public class CarDriverMsgReportBean extends PrintBean {

	private String signRouteNo;// 签单号
	private String overmemoNo;// 交接单号
	private String carCode;// 车牌号
	private String useCarDepart;// 用车部门
	private String printDepart;//打印部门
	private String useCarResult;// 用途
	private String useCarType;// 用车类型
	private String useCarDate;// 用车日期
	
	private String sendGoodsMan;//送货人
	private String carManTel;//车主电话

	private String printName;// 打印人
	private Long printNum;// 打印次数
	private String printTime;// 打印时间
	private String printId;// 打印记录表ID
	private String sourceNo;// 来源ID

	public String getSignRouteNo() {
		return signRouteNo;
	}

	public void setSignRouteNo(String signRouteNo) {
		this.signRouteNo = signRouteNo;
	}

	public String getOvermemoNo() {
		return overmemoNo;
	}

	public void setOvermemoNo(String overmemoNo) {
		this.overmemoNo = overmemoNo;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getUseCarDepart() {
		return useCarDepart;
	}

	public void setUseCarDepart(String useCarDepart) {
		this.useCarDepart = useCarDepart;
	}

	public String getUseCarResult() {
		return useCarResult;
	}

	public void setUseCarResult(String useCarResult) {
		this.useCarResult = useCarResult;
	}

	public String getUseCarType() {
		return useCarType;
	}

	public void setUseCarType(String useCarType) {
		this.useCarType = useCarType;
	}

	public String getUseCarDate() {
		return useCarDate;
	}

	public void setUseCarDate(String useCarDate) {
		this.useCarDate = useCarDate;
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

	public String getPrintDepart() {
		return printDepart;
	}

	public void setPrintDepart(String printDepart) {
		this.printDepart = printDepart;
	}

	public String getSendGoodsMan() {
		return sendGoodsMan;
	}

	public void setSendGoodsMan(String sendGoodsMan) {
		this.sendGoodsMan = sendGoodsMan;
	}

	public String getCarManTel() {
		return carManTel;
	}

	public void setCarManTel(String carManTel) {
		this.carManTel = carManTel;
	}
}
