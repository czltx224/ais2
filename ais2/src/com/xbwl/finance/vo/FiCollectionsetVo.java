package com.xbwl.finance.vo;

import com.xbwl.entity.FiCollectionset;

public class FiCollectionsetVo extends FiCollectionset {
	private String departName;// ���ű�����
	private String cusName;// ���̱�����

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
