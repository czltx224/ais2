package com.xbwl.oper.stock.vo;

import com.xbwl.entity.OprLoadingbrigadTime;

public class OprLoadingbrigadTimeVo extends OprLoadingbrigadTime {

	private String loadingbrigadName;//装卸组名称
	private String groupName;//分拨组名称
	
	public String getLoadingbrigadName() {
		return loadingbrigadName;
	}
	public void setLoadingbrigadName(String loadingbrigadName) {
		this.loadingbrigadName = loadingbrigadName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}
