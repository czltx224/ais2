package com.xbwl.rbac.entity;
// default package

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * RalasafePrivilege entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RALASAFE_PRIVILEGE", schema = "RALASAFE", uniqueConstraints = @UniqueConstraint(columnNames = "NAME"))
public class RalasafePrivilege implements java.io.Serializable {

	// Fields

	private Long id;
	private Long pid;
	private String description;
	private String name;
	private Long isleaf;
	private Long decisionpolicycombalg;
	private Long querypolicycombalg;
	private Long type;
	private String constantname;
	private String url;
	private String target;
	private Long ordernum;
	private Set<RalasafeRoleprivilege> ralasafeRoleprivileges = new HashSet<RalasafeRoleprivilege>(
			0);

	// Constructors

	/** default constructor */
	public RalasafePrivilege() {
	}

	/** minimal constructor */
	public RalasafePrivilege(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public RalasafePrivilege(Long id, Long pid, String description,
			String name, Long isleaf, Long decisionpolicycombalg,
			Long querypolicycombalg, Long type, String constantname,
			String url, String target, Long ordernum,
			Set<RalasafeRoleprivilege> ralasafeRoleprivileges) {
		this.id = id;
		this.pid = pid;
		this.description = description;
		this.name = name;
		this.isleaf = isleaf;
		this.decisionpolicycombalg = decisionpolicycombalg;
		this.querypolicycombalg = querypolicycombalg;
		this.type = type;
		this.constantname = constantname;
		this.url = url;
		this.target = target;
		this.ordernum = ordernum;
		this.ralasafeRoleprivileges = ralasafeRoleprivileges;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PID", precision = 22, scale = 0)
	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Column(name = "DESCRIPTION", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "NAME", unique = true, nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ISLEAF", precision = 22, scale = 0)
	public Long getIsleaf() {
		return this.isleaf;
	}

	public void setIsleaf(Long isleaf) {
		this.isleaf = isleaf;
	}

	@Column(name = "DECISIONPOLICYCOMBALG", precision = 22, scale = 0)
	public Long getDecisionpolicycombalg() {
		return this.decisionpolicycombalg;
	}

	public void setDecisionpolicycombalg(Long decisionpolicycombalg) {
		this.decisionpolicycombalg = decisionpolicycombalg;
	}

	@Column(name = "QUERYPOLICYCOMBALG", precision = 22, scale = 0)
	public Long getQuerypolicycombalg() {
		return this.querypolicycombalg;
	}

	public void setQuerypolicycombalg(Long querypolicycombalg) {
		this.querypolicycombalg = querypolicycombalg;
	}

	@Column(name = "TYPE", precision = 22, scale = 0)
	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	@Column(name = "CONSTANTNAME", length = 40)
	public String getConstantname() {
		return this.constantname;
	}

	public void setConstantname(String constantname) {
		this.constantname = constantname;
	}

	@Column(name = "URL", length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "TARGET", length = 20)
	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Column(name = "ORDERNUM", precision = 22, scale = 0)
	public Long getOrdernum() {
		return this.ordernum;
	}

	public void setOrdernum(Long ordernum) {
		this.ordernum = ordernum;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ralasafePrivilege")
	public Set<RalasafeRoleprivilege> getRalasafeRoleprivileges() {
		return this.ralasafeRoleprivileges;
	}

	public void setRalasafeRoleprivileges(
			Set<RalasafeRoleprivilege> ralasafeRoleprivileges) {
		this.ralasafeRoleprivileges = ralasafeRoleprivileges;
	}

}