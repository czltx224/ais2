package com.xbwl.finance.vo;

import com.xbwl.entity.FiAdvanceset;

public class fiAdvancesetVo extends FiAdvanceset {
	private String departName;// 所属部门表名称

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}
}
