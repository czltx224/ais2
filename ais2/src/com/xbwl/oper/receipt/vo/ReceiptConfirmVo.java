package com.xbwl.oper.receipt.vo;

import java.util.Date;

public class ReceiptConfirmVo implements java.io.Serializable {

	private Long dno;//配送单号
	private String remark;//备注
	private Long confirmNum;//确收份数
	private String confirmMan;//确收人
	private Long confirmStatus;//回单确认状态 0：未确认，1：正常，2：异常
	private String curStatus;//回单当前状态
	private Date confirmTime;//确收时间
	
	public Long getDno() {
		return dno;
	}
	public void setDno(Long dno) {
		this.dno = dno;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Long getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(Long confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public String getCurStatus() {
		return curStatus;
	}
	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
}
