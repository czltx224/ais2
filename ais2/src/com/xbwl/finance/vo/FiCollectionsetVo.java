package com.xbwl.finance.vo;

import com.xbwl.entity.FiCollectionset;

public class FiCollectionsetVo extends FiCollectionset {
	private String departName;// 部门表名称
	private String cusName;// 客商表名称

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
}
