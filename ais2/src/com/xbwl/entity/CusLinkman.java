package com.xbwl.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * CusLinkman entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CUS_LINKMAN")
public class CusLinkman implements java.io.Serializable , AuditableEntity{

	// Fields

	private Long id;
	private String name;//联系人名称
	private String tel;//电话
	private Long sex;//性别
	private String duty;//所在企业职责
	private Date birthday;//生日
	private Long isPivot;//是否关键人物
	private String hobbiesInterests;//爱好
	private String linkman;//关联联系人
	private String relation;//关系
	private Date lastVisistTime;//最后沟通时间
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long cusRecordId;
	private Long status;
	private Long departId;
	
	private String phone;//电话
	// Constructors

	/** default constructor */
	public CusLinkman() {
	}
	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_CUS_LINKMAN")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TEL", length = 100)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "SEX", precision = 22, scale = 0)
	public Long getSex() {
		return this.sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	@Column(name = "DUTY", length = 200)
	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "BIRTHDAY", length = 7)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "IS_PIVOT", precision = 22, scale = 0)
	public Long getIsPivot() {
		return this.isPivot;
	}

	public void setIsPivot(Long isPivot) {
		this.isPivot = isPivot;
	}

	@Column(name = "HOBBIES_INTERESTS", length = 200)
	public String getHobbiesInterests() {
		return this.hobbiesInterests;
	}

	public void setHobbiesInterests(String hobbiesInterests) {
		this.hobbiesInterests = hobbiesInterests;
	}

	@Column(name = "LINKMAN", length = 200)
	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	@Column(name = "RELATION", length = 20)
	public String getRelation() {
		return this.relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "LAST_VISIST_TIME", length = 7)
	public Date getLastVisistTime() {
		return this.lastVisistTime;
	}

	public void setLastVisistTime(Date lastVisistTime) {
		this.lastVisistTime = lastVisistTime;
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

	@JSON(format="yyyy-MM-dd")
	@Column(name = "UPDATE_NAME", length = 7)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "UPDATE_TIME", length = 20)
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

	@Column(name = "CUS_RECORD_ID", nullable = false, precision = 22, scale = 0)
	public Long getCusRecordId() {
		return this.cusRecordId;
	}

	public void setCusRecordId(Long cusRecordId) {
		this.cusRecordId = cusRecordId;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	/**
	 * @return the departId
	 */
	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return departId;
	}
	/**
	 * @param departId the departId to set
	 */
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	/**
	 * @return the phone
	 */
	@Column(name = "PHONE", precision = 22, scale = 0)
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}