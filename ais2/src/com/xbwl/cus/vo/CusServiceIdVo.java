package com.xbwl.cus.vo;

/**
 * 指派客服员参数VO
 * @author CZL
 * @DATE 2012-06-28
 */
public class CusServiceIdVo implements java.io.Serializable{

	private Long cusRecordId;// 客户ID
	private String userCode;// 客服工号

	public Long getCusRecordId() {
		return cusRecordId;
	}

	public void setCusRecordId(Long cusRecordId) {
		this.cusRecordId = cusRecordId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
