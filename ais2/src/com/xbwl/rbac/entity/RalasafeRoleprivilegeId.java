package com.xbwl.rbac.entity;
// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * RalasafeRoleprivilegeId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class RalasafeRoleprivilegeId implements java.io.Serializable {

	// Fields

	private Long roleid;
	private Long privilegeid;

	// Constructors

	/** default constructor */
	public RalasafeRoleprivilegeId() {
	}

	/** full constructor */
	public RalasafeRoleprivilegeId(Long roleid, Long privilegeid) {
		this.roleid = roleid;
		this.privilegeid = privilegeid;
	}

	// Property accessors

	@Column(name = "ROLEID", nullable = false, precision = 22, scale = 0)
	public Long getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	@Column(name = "PRIVILEGEID", nullable = false, precision = 22, scale = 0)
	public Long getPrivilegeid() {
		return this.privilegeid;
	}

	public void setPrivilegeid(Long privilegeid) {
		this.privilegeid = privilegeid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RalasafeRoleprivilegeId))
			return false;
		RalasafeRoleprivilegeId castOther = (RalasafeRoleprivilegeId) other;

		return ((this.getRoleid() == castOther.getRoleid()) || (this
				.getRoleid() != null
				&& castOther.getRoleid() != null && this.getRoleid().equals(
				castOther.getRoleid())))
				&& ((this.getPrivilegeid() == castOther.getPrivilegeid()) || (this
						.getPrivilegeid() != null
						&& castOther.getPrivilegeid() != null && this
						.getPrivilegeid().equals(castOther.getPrivilegeid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRoleid() == null ? 0 : this.getRoleid().hashCode());
		result = 37
				* result
				+ (getPrivilegeid() == null ? 0 : this.getPrivilegeid()
						.hashCode());
		return result;
	}

}