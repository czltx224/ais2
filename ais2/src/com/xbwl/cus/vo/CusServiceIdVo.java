package com.xbwl.cus.vo;

/**
 * ָ�ɿͷ�Ա����VO
 * @author CZL
 * @DATE 2012-06-28
 */
public class CusServiceIdVo implements java.io.Serializable{

	private Long cusRecordId;// �ͻ�ID
	private String userCode;// �ͷ�����

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
