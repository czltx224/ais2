package com.xbwl.finance.vo;

import com.xbwl.entity.FiDeliverycost;

/**
 * author shuw
 * time Oct 8, 2011 6:12:41 PM
 */

public class FiDeliverycostVo extends FiDeliverycost {
	
	private String cusName;
	private String feeType;
	
	
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

}
