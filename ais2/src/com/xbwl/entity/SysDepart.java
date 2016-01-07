package com.xbwl.entity;
// default package

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;
import com.xbwl.common.utils.XbwlInt;
import com.xbwl.rbac.entity.SysUser;

/**
 * SysDepart entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_DEPART")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"}) 
public class SysDepart implements java.io.Serializable,AuditableEntity {

	// Fields
	@XbwlInt(autoDepart=false)
	private Long departId;
	@XbwlInt(autoDepart=false)
	private String departName;
	private String departNo;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private Long isBussinessDepa;//是否是业务部门
	private SysStation sysStation;
	private String ts;
	private String addr;//部门地址
	private String telephone;//部门电话
	private String owntakeType;//自提类型
	private Long isCusDepart;//是否为客服部门
	
	
	private SysDepart parent;
	private Set<SysDepart> departs=new HashSet<SysDepart>(0);

	// Constructors

	/** default constructor */
	public SysDepart() {
	}

	/** minimal constructor */
	public SysDepart(Long departId) {
		this.departId = departId;
	}


	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_SYS_DEPART")
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "DEPART_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "DEPART_NAME", length = 20)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}




	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
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

	
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	
	@ManyToOne(fetch=FetchType.LAZY )
	@JoinColumn(name="PARENT_ID")
	public SysDepart getParent() {
		return parent;
	}

	public void setParent(SysDepart parent) {
		this.parent = parent;
	}
	
	@OneToMany(cascade={CascadeType.REMOVE,CascadeType.REFRESH},fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_ID")
	@Fetch(FetchMode.SUBSELECT)
	@JSON(serialize=false)
	public Set<SysDepart> getDeparts() {
		return departs;
	}

	public void setDeparts(Set<SysDepart> departs) {
		this.departs = departs;
	}
	
	@Transient
	public boolean isLeaf(){
		return this.departs.size()==0;
	}
	@Transient
	public boolean isRoot(){
		return this.getParent()==null;
	}
	@Column(name = "IS_BUSSINESS_DEPA")
	public Long getIsBussinessDepa() {
		return isBussinessDepa;
	}

	public void setIsBussinessDepa(Long isBussinessDepa) {
		this.isBussinessDepa = isBussinessDepa;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LEAD_STATION")
	public SysStation getSysStation() {
		return sysStation;
	}

	public void setSysStation(SysStation sysStation) {
		this.sysStation = sysStation;
	}
	
	@Column(name = "TS")
	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	@Column(name = "ADDR")
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	@Column(name = "TELPHONE")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the departNo
	 */
	@Column(name = "DEPART_NO")
	public String getDepartNo() {
		return departNo;
	}

	/**
	 * @param departNo the departNo to set
	 */
	public void setDepartNo(String departNo) {
		this.departNo = departNo;
	}

	/**
	 * @return the owntakeType
	 */
	@Column(name = "OWN_TAKETYPE")
	public String getOwntakeType() {
		return owntakeType;
	}

	/**
	 * @param owntakeType the owntakeType to set
	 */
	public void setOwntakeType(String owntakeType) {
		this.owntakeType = owntakeType;
	}

	/**
	 * @return the isCusDepart
	 */
	@Column(name = "IS_CUS_DEPART")
	public Long getIsCusDepart() {
		return isCusDepart;
	}

	/**
	 * @param isCusDepart the isCusDepart to set
	 */
	public void setIsCusDepart(Long isCusDepart) {
		this.isCusDepart = isCusDepart;
	}
	
	
}