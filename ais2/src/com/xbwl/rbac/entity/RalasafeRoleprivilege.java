package com.xbwl.rbac.entity;
// default package

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * RalasafeRoleprivilege entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RALASAFE_ROLEPRIVILEGE", schema = "RALASAFE")
public class RalasafeRoleprivilege implements java.io.Serializable {

	// Fields

	private RalasafeRoleprivilegeId id;
	private RalasafePrivilege ralasafePrivilege;
	private RalasafeRole ralasafeRole;

	// Constructors

	/** default constructor */
	public RalasafeRoleprivilege() {
	}

	/** full constructor */
	public RalasafeRoleprivilege(RalasafeRoleprivilegeId id,
			RalasafePrivilege ralasafePrivilege, RalasafeRole ralasafeRole) {
		this.id = id;
		this.ralasafePrivilege = ralasafePrivilege;
		this.ralasafeRole = ralasafeRole;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "roleid", column = @Column(name = "ROLEID", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "privilegeid", column = @Column(name = "PRIVILEGEID", nullable = false, precision = 22, scale = 0)) })
	public RalasafeRoleprivilegeId getId() {
		return this.id;
	}

	public void setId(RalasafeRoleprivilegeId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRIVILEGEID", nullable = false, insertable = false, updatable = false)
	public RalasafePrivilege getRalasafePrivilege() {
		return this.ralasafePrivilege;
	}

	public void setRalasafePrivilege(RalasafePrivilege ralasafePrivilege) {
		this.ralasafePrivilege = ralasafePrivilege;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLEID", nullable = false, insertable = false, updatable = false)
	public RalasafeRole getRalasafeRole() {
		return this.ralasafeRole;
	}

	public void setRalasafeRole(RalasafeRole ralasafeRole) {
		this.ralasafeRole = ralasafeRole;
	}

}