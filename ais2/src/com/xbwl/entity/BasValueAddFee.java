package com.xbwl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * BasValueAddFee entity.
 * Caozhili
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BAS_VALUE_ADD_FEE")
public class BasValueAddFee implements java.io.Serializable,AuditableEntity{

	// Fields

	private Long id;//序号1
	private String feeName;//服务费名称2
	private String feeSubject;//会计科目 来自数据字典3
	private Double feeCount;//收费数额4
	private Long autoFee;//1.自动收费，0.非自动收费5
	private String createName;//创建人6
	private Date createTime;//创建时间7
	private String updateName;//修改人8
	private Date updateTime;//修改时间9
	private String ts;//时间戳10
	private String payMan;//付款人 0收货人，1代理，,由谁去付款（通过数据字典配置，不能写死）11
	private String payRule;//收费规则 SQL表达式（TEXT格式，注意SQL注入）12
	private String feeLink;//收费环节 来自数据字典，大致类型有 传真录入 仓存 出库 13

	// Constructors

	/** default constructor */
	public BasValueAddFee() {
	}

	/** minimal constructor */
	public BasValueAddFee(Long id) {
		this.id = id;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_BAS_VALUE_ADD_FEE ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "FEE_NAME", length = 20)
	public String getFeeName() {
		return this.feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	@Column(name = "FEE_SUBJECT", length = 20)
	public String getFeeSubject() {
		return this.feeSubject;
	}

	public void setFeeSubject(String feeSubject) {
		this.feeSubject = feeSubject;
	}

	@Column(name = "FEE_COUNT", precision = 22, scale = 0)
	public Double getFeeCount() {
		return this.feeCount;
	}

	public void setFeeCount(Double feeCount) {
		this.feeCount = feeCount;
	}

	@Column(name = "AUTO_FEE")
	public Long getAutoFee() {
		return this.autoFee;
	}

	public void setAutoFee(Long autoFee) {
		this.autoFee = autoFee;
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

	@Column(name = "TS")
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "PAY_MAN", length = 20)
	public String getPayMan() {
		return this.payMan;
	}

	public void setPayMan(String payMan) {
		this.payMan = payMan;
	}

	@Column(name = "PAY_RULE", length = 500)
	public String getPayRule() {
		return this.payRule;
	}

	public void setPayRule(String payRule) {
		this.payRule = payRule;
	}

	@Column(name = "FEE_LINK", length = 20)
	public String getFeeLink() {
		return this.feeLink;
	}

	public void setFeeLink(String feeLink) {
		this.feeLink = feeLink;
	}

}