package com.xbwl.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

/**
 * OprChangeMain entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_CHANGE_MAIN")
public class OprChangeMain implements java.io.Serializable {

	// Fields

	private Long id;
	private Long isSystem;//是否为系统默认
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private String remark;//备注
	private Long status;//状态
	private Long dno;//配送单号
	private Long departId;//部门ID
	private String departName;//部门名称
	private Set<OprChangeDetail> oprChangeDetail=new HashSet<OprChangeDetail>(0);

	// Constructors

	/** default constructor */
	public OprChangeMain() {
	}

	/** minimal constructor */
	public OprChangeMain(Long id) {
		this.id = id;
	}
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_CHANGE_MAIN")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "IS_SYSTEM", precision = 1, scale = 0)
	public Long getIsSystem() {
		return this.isSystem;
	}

	public void setIsSystem(Long isSystem) {
		this.isSystem = isSystem;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS", length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "STATUS", precision = 1, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "D_NO", precision = 10, scale = 0)
	/**
	 * @return the dno
	 */
	public Long getDno() {
		return dno;
	}

	/**
	 * @param dno the dno to set
	 */
	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "DEPART_ID", precision = 10, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	/**
	 * @return the oprChangeDetail
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "oprChangeMain")
	public Set<OprChangeDetail> getOprChangeDetail() {
		return oprChangeDetail;
	}

	/**
	 * @param oprChangeDetail the oprChangeDetail to set
	 */
	public void setOprChangeDetail(Set<OprChangeDetail> oprChangeDetail) {
		this.oprChangeDetail = oprChangeDetail;
	}

	
	
}