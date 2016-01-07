package com.xbwl.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CusServiceId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class CusServiceId implements java.io.Serializable {

	// Fields

	private Long cusRecordId;//客户ID
	private String userCode;//客服工号

	// Constructors

	/** default constructor */
	public CusServiceId() {
	}

	/** full constructor */
	public CusServiceId(Long cusRecordId, String userCode) {
		this.cusRecordId = cusRecordId;
		this.userCode = userCode;
	}

	// Property accessors

	@Column(name = "CUS_RECORD_ID", nullable = false, precision = 22, scale = 0)
	public Long getCusRecordId() {
		return this.cusRecordId;
	}

	public void setCusRecordId(Long cusRecordId) {
		this.cusRecordId = cusRecordId;
	}

	@Column(name = "USER_CODE", nullable = false, length = 20)
	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CusServiceId))
			return false;
		CusServiceId castOther = (CusServiceId) other;

		return ((this.getCusRecordId() == castOther.getCusRecordId()) || (this
				.getCusRecordId() != null
				&& castOther.getCusRecordId() != null && this.getCusRecordId()
				.equals(castOther.getCusRecordId())))
				&& ((this.getUserCode() == castOther.getUserCode()) || (this
						.getUserCode() != null
						&& castOther.getUserCode() != null && this
						.getUserCode().equals(castOther.getUserCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCusRecordId() == null ? 0 : this.getCusRecordId()
						.hashCode());
		result = 37 * result
				+ (getUserCode() == null ? 0 : this.getUserCode().hashCode());
		return result;
	}

}