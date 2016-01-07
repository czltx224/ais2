package com.xbwl.finance.vo;

import com.xbwl.entity.OprValueAddFee;

/**
 * author shuw
 * time Nov 18, 2011 10:02:38 AM
 */

public class OprValueAddFeeVo extends OprValueAddFee {

	private String consignee;
	private String flightMainNo;
	private String subNo;
	private Long customerId;
	private String customerName;
	
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getFlightMainNo() {
		return flightMainNo;
	}
	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}
	public String getSubNo() {
		return subNo;
	}
	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
