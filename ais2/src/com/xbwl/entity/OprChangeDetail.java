package com.xbwl.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

/**
 * OprChangeDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_CHANGE_DETAIL")
public class OprChangeDetail implements java.io.Serializable {

	// Fields

	private Long id;
	//private Long oprChangeMainId;
	private String changeField;//改变属性名称
	private String changeFieldZh;//改变属性中文名称
	private String changePre;//改变前的值
	private String changePost;//改变后的值
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private OprChangeMain oprChangeMain;
	private String ts;

	// Constructors

	/** default constructor */
	public OprChangeDetail() {
	}

	/** minimal constructor */
	public OprChangeDetail(Long id) {
		this.id = id;
	}
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_CHANGE_DETAIL")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
/*
	@Column(name = "OPR_CHANGE_MAIN_ID", precision = 10, scale = 0)
	public Long getOprChangeMainId() {
		return this.oprChangeMainId;
	}

	public void setOprChangeMainId(Long oprChangeMainId) {
		this.oprChangeMainId = oprChangeMainId;
	}
*/
	@Column(name = "CHANGE_FIELD", length = 100)
	public String getChangeField() {
		return this.changeField;
	}

	public void setChangeField(String changeField) {
		this.changeField = changeField;
	}

	@Column(name = "CHANGE_FIELD_ZH", length = 100)
	public String getChangeFieldZh() {
		return this.changeFieldZh;
	}

	public void setChangeFieldZh(String changeFieldZh) {
		this.changeFieldZh = changeFieldZh;
	}

	@Column(name = "CHANGE_PRE", length = 500)
	public String getChangePre() {
		return this.changePre;
	}

	public void setChangePre(String changePre) {
		this.changePre = changePre;
	}

	@Column(name = "CHANGE_POST", length = 500)
	public String getChangePost() {
		return this.changePost;
	}

	public void setChangePost(String changePost) {
		this.changePost = changePost;
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

	/**
	 * @return the oprChangeMain
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPR_CHANGE_MAIN_ID")
	public OprChangeMain getOprChangeMain() {
		return oprChangeMain;
	}

	/**
	 * @param oprChangeMain the oprChangeMain to set
	 */
	public void setOprChangeMain(OprChangeMain oprChangeMain) {
		this.oprChangeMain = oprChangeMain;
	}
	
}