package com.xbwl.oper.receipt.vo;

import java.util.Date;

public class ReceiptConfirmVo implements java.io.Serializable {

	private Long dno;//���͵���
	private String remark;//��ע
	private Long confirmNum;//ȷ�շ���
	private String confirmMan;//ȷ����
	private Long confirmStatus;//�ص�ȷ��״̬ 0��δȷ�ϣ�1��������2���쳣
	private String curStatus;//�ص���ǰ״̬
	private Date confirmTime;//ȷ��ʱ��
	
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
