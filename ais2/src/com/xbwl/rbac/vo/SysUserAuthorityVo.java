package com.xbwl.rbac.vo;
/**
 * author shuw
 * time Apr 10, 2012 9:53:35 AM
 */

public class SysUserAuthorityVo extends SysUserVo{
	
	private String  userLevelString;  //�û��ȼ���ʾ
	
	private Long userLevel;     //�û��ȼ�

	public String getUserLevelString() {
		return userLevelString;
	}

	public void setUserLevelString(String userLevelString) {
		this.userLevelString = userLevelString;
	}

	public Long getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Long userLevel) {
		this.userLevel = userLevel;
	}
}
