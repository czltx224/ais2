package com.xbwl.oper.fax.vo;
/**
 * 传真录入返回的信息
 * @project ais_edi
 * @author czl
 * @Time Feb 11, 2012 3:08:19 PM
 */
public class FaxReturnMsg {

	private String returnMsg;//返回信息
	private Long dno;//返回配送单号
	private Long consigneeId;//返回收获人ID
	
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public Long getDno() {
		return dno;
	}
	public void setDno(Long dno) {
		this.dno = dno;
	}
	public Long getConsigneeId() {
		return consigneeId;
	}
	public void setConsigneeId(Long consigneeId) {
		this.consigneeId = consigneeId;
	}
}
