package com.xbwl.finance.vo;

import com.xbwl.entity.FiCollectiondetail;

public class FiCollectiondetailVo extends FiCollectiondetail {

	private String custprop;// ���̱���������
	private Long billingCycle; // ���ջ����������:����/��������
	private String departName; //ϵͳ���ű���������
	
	public String getCustprop() {
		return custprop;
	}
	public void setCustprop(String custprop) {
		this.custprop = custprop;
	}

	public Long getBillingCycle() {
		return billingCycle;
	}
	public void setBillingCycle(Long billingCycle) {
		this.billingCycle = billingCycle;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}

}
