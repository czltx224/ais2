package com.xbwl.oper.reports.vo;


public class SendGoodsDetailCountVo implements java.io.Serializable{

	private String ampiece;//上午送货票数
	private String pmpiece;//下午送货票数
	private String amcountpiece;//上午折合票数
	private String pmcountpiece;//下午折合票数
	private String amweight;//上午送货重量
	private String pmweight;//下午送货重量
	private String qianshou;//签收总数
	private String amqiandan;//上午签收数
	private String pmqiandan;//下午签收数
	private String amshouru;//上午送货收入
	private String pmshouru;//下午送货收入
	private String returnNum;//返货件数
	private String stopFee;//停车费
	
	public String getAmpiece() {
		return ampiece;
	}
	public void setAmpiece(String ampiece) {
		this.ampiece = ampiece;
	}
	public String getPmpiece() {
		return pmpiece;
	}
	public void setPmpiece(String pmpiece) {
		this.pmpiece = pmpiece;
	}
	public String getAmcountpiece() {
		return amcountpiece;
	}
	public void setAmcountpiece(String amcountpiece) {
		this.amcountpiece = amcountpiece;
	}
	public String getPmcountpiece() {
		return pmcountpiece;
	}
	public void setPmcountpiece(String pmcountpiece) {
		this.pmcountpiece = pmcountpiece;
	}
	public String getAmweight() {
		return amweight;
	}
	public void setAmweight(String amweight) {
		this.amweight = amweight;
	}
	public String getPmweight() {
		return pmweight;
	}
	public void setPmweight(String pmweight) {
		this.pmweight = pmweight;
	}
	public String getQianshou() {
		return qianshou;
	}
	public void setQianshou(String qianshou) {
		this.qianshou = qianshou;
	}
	public String getAmqiandan() {
		return amqiandan;
	}
	public void setAmqiandan(String amqiandan) {
		this.amqiandan = amqiandan;
	}
	public String getPmqiandan() {
		return pmqiandan;
	}
	public void setPmqiandan(String pmqiandan) {
		this.pmqiandan = pmqiandan;
	}
	public String getAmshouru() {
		return amshouru;
	}
	public void setAmshouru(String amshouru) {
		this.amshouru = amshouru;
	}
	public String getPmshouru() {
		return pmshouru;
	}
	public void setPmshouru(String pmshouru) {
		this.pmshouru = pmshouru;
	}
	public String getReturnNum() {
		return returnNum;
	}
	public void setReturnNum(String returnNum) {
		this.returnNum = returnNum;
	}
	public String getStopFee() {
		return stopFee;
	}
	public void setStopFee(String stopFee) {
		this.stopFee = stopFee;
	}
}
