package com.xbwl.finance.vo;

import com.xbwl.entity.FiPayment;

public class FiPaymentVo extends FiPayment {
	private String cusName;// 客商表名称

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
}
