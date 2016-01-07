package com.xbwl.finance.vo;

import com.xbwl.entity.FiCollectiondetail;

public class FiCollectiondetailVo extends FiCollectiondetail {

	private String custprop;// 客商表：客商类型
	private Long billingCycle; // 代收货款结算设置:对账/结算周期
	private String departName; //系统部门表：所属部门
	
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
