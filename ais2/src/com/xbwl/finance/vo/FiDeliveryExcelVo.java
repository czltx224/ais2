package com.xbwl.finance.vo;
/**
 * author shuw
 * time Oct 21, 2011 2:55:59 PM
 */

public class FiDeliveryExcelVo {
	public String faxMainNo;
	public Double faxWeight;
	public Double faxAmount;
	public Long  batchNo;
	
	public String excelCompany;
	public String excelNo;
	public Double excelWeight;
	public Double excelBanFee;
	public Double excelAmount;
	public Long status;
	public String wrongInfo;
	
	public String getExcelCompany() {
		return excelCompany;
	}
	public void setExcelCompany(String excelCompany) {
		this.excelCompany = excelCompany;
	}
	public Double getExcelBanFee() {
		return excelBanFee;
	}
	public void setExcelBanFee(Double excelBanFee) {
		this.excelBanFee = excelBanFee;
	}
	public String getFaxMainNo() {
		return faxMainNo;
	}
	public void setFaxMainNo(String faxMainNo) {
		this.faxMainNo = faxMainNo;
	}
	public Double getFaxWeight() {
		return faxWeight;
	}
	public void setFaxWeight(Double faxWeight) {
		this.faxWeight = faxWeight;
	}
	public Double getFaxAmount() {
		return faxAmount;
	}
	public void setFaxAmount(Double faxAmount) {
		this.faxAmount = faxAmount;
	}
	public String getExcelNo() {
		return excelNo;
	}
	public void setExcelNo(String excelNo) {
		this.excelNo = excelNo;
	}
	public Double getExcelWeight() {
		return excelWeight;
	}
	public void setExcelWeight(Double excelWeight) {
		this.excelWeight = excelWeight;
	}
	public Double getExcelAmount() {
		return excelAmount;
	}
	public void setExcelAmount(Double excelAmount) {
		this.excelAmount = excelAmount;
	}

	@Override
	public String toString() {
		return excelNo+","+excelCompany+","+excelAmount+","+excelWeight+","+excelBanFee+"¡£";
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getWrongInfo() {
		return wrongInfo;
	}
	public void setWrongInfo(String wrongInfo) {
		this.wrongInfo = wrongInfo;
	}
	
	
}
