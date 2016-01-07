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
import com.xbwl.common.utils.XbwlInt;

/**
 * OprSign entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_SIGN")
public class OprSign implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long dno;//配送单号
	private String signMan;//签收人
	private String identityCard;//身份证号码
	private String replaceSign;//代签人
	private String reIdentityCard;//代签身份证号码
	private String scanAdd;//扫描身份证地址
	private String createName;//创建人
	private Date createTime;//创建时间
	private Date updateTime;//修改人
	private String updateName;//修改时间
	private String ts;
	
	@XbwlInt(autoDepart=false)
	private Long departId;//签收部门
	private String signSource;//签收来源
	private String cardType;//证件类型
	private String remark;//异常备注
	
	private Long isSignException;//是否签收异常 0：正常，1：异常
	

	// Constructors

	/** default constructor */
	public OprSign() {
	}


	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_SIGN")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "D_NO", nullable = false, precision = 22, scale = 0)
	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long DNo) {
		this.dno = DNo;
	}

	@Column(name = "SIGN_MAN", nullable = false, length = 200)
	public String getSignMan() {
		return this.signMan;
	}

	public void setSignMan(String signMan) {
		this.signMan = signMan;
	}

	@Column(name = "IDENTITY_CARD", length = 15)
	public String getIdentityCard() {
		return this.identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	@Column(name = "REPLACE_SIGN", length = 50)
	public String getReplaceSign() {
		return this.replaceSign;
	}

	public void setReplaceSign(String replaceSign) {
		this.replaceSign = replaceSign;
	}

	@Column(name = "RE_IDENTITY_CARD", length = 15)
	public String getReIdentityCard() {
		return this.reIdentityCard;
	}

	public void setReIdentityCard(String reIdentityCard) {
		this.reIdentityCard = reIdentityCard;
	}

	@Column(name = "SCAN_ADD", length = 200)
	public String getScanAdd() {
		return this.scanAdd;
	}

	public void setScanAdd(String scanAdd) {
		this.scanAdd = scanAdd;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
	@JSON(format = "yyyy-MM-dd")
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JSON(format = "yyyy-MM-dd")
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "TS", length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	/**
	 * @return the signSource
	 */
	@Column(name = "SIGN_SOURCE")
	public String getSignSource() {
		return signSource;
	}

	/**
	 * @param signSource the signSource to set
	 */
	public void setSignSource(String signSource) {
		this.signSource = signSource;
	}


	/**
	 * @return the cardType
	 */
	@Column(name = "CARD_TYPE")
	public String getCardType() {
		return cardType;
	}


	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}


	/**
	 * @return the remark
	 */
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}


	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "IS_SIGN_EXCEPTION")
	public Long getIsSignException() {
		return isSignException;
	}


	public void setIsSignException(Long isSignException) {
		this.isSignException = isSignException;
	}
	
}