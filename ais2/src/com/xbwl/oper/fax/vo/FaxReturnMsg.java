package com.xbwl.oper.fax.vo;
/**
 * ����¼�뷵�ص���Ϣ
 * @project ais_edi
 * @author czl
 * @Time Feb 11, 2012 3:08:19 PM
 */
public class FaxReturnMsg {

	private String returnMsg;//������Ϣ
	private Long dno;//�������͵���
	private Long consigneeId;//�����ջ���ID
	
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
