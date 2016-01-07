package com.xbwl.finance.vo;

import com.xbwl.entity.FiReceivabledetail;

/**
 * @ClassName: FiReceivabledetailVo
 * @Description: �����˿���ϸVO�� ���͵���OprFaxIn�����̱�Customer������Ƿ������:FiArrearset
 * @author oysz
 * @date Jul 14, 2011 5:04:13 PM
 */
public class FiReceivabledetailVo extends FiReceivabledetail {

	private String custprop;// ���̱���������
	private String reconciliationUser;// ����Ƿ������:����Ա
	private Long billingCycle; // ����Ƿ������:����/��������
	
	private Long piece;// ����
	private Double cusWeight;// �Ʒ�����
	private Double bulk;// ���
	
	public String getCustprop() {
		return custprop;
	}
	public void setCustprop(String custprop) {
		this.custprop = custprop;
	}
	public String getReconciliationUser() {
		return reconciliationUser;
	}
	public void setReconciliationUser(String reconciliationUser) {
		this.reconciliationUser = reconciliationUser;
	}
	public Long getBillingCycle() {
		return billingCycle;
	}
	public void setBillingCycle(Long billingCycle) {
		this.billingCycle = billingCycle;
	}
	
	public Long getPiece() {
		return piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	public Double getCusWeight() {
		return cusWeight;
	}

	public void setCusWeight(Double cusWeight) {
		this.cusWeight = cusWeight;
	}

	public Double getBulk() {
		return bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

}
