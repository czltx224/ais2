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
 * CusCollection entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CUS_COLLECTION")
public class CusCollection implements java.io.Serializable, AuditableEntity {

	// Fields

	private Long id;
	private Long cusId;//客商ID
	private String cusName;//客商名称
	private String remark;//催款备注
	private Long money;//催款金额
	private Date repaymentDate;//代理承诺还款日期
	private String collectionFile;//催款附件
	private String collectionLink;//催款联系人
	public String collectionLinkTel;//催款联系电话
	private Date collectionDtime;//催款时间
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	
	private Long departId;

	// Constructors

	/** default constructor */
	public CusCollection() {
	}

	/** minimal constructor */
	public CusCollection(Long id) {
		this.id = id;
	}

	/** full constructor */
	public CusCollection(Long id, Long cusId, String cusName, String remark,
			Long money, Date repaymentDate, String collectionFile,
			String collectionLink, Date collectionDtime, String createName,
			Date createTime, String updateName, Date updateTime, String ts) {
		this.id = id;
		this.cusId = cusId;
		this.cusName = cusName;
		this.remark = remark;
		this.money = money;
		this.repaymentDate = repaymentDate;
		this.collectionFile = collectionFile;
		this.collectionLink = collectionLink;
		this.collectionDtime = collectionDtime;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_CUS_COLLECTION ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUS_ID", precision = 22, scale = 0)
	public Long getCusId() {
		return this.cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	@Column(name = "CUS_NAME", length = 200)
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	@Column(name = "REMARK", length = 2000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "MONEY", precision = 22, scale = 0)
	public Long getMoney() {
		return this.money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "REPAYMENT_DATE", length = 7)
	public Date getRepaymentDate() {
		return this.repaymentDate;
	}

	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	@Column(name = "COLLECTION_FILE", length = 200)
	public String getCollectionFile() {
		return this.collectionFile;
	}

	public void setCollectionFile(String collectionFile) {
		this.collectionFile = collectionFile;
	}

	@Column(name = "COLLECTION_LINK", length = 200)
	public String getCollectionLink() {
		return this.collectionLink;
	}

	public void setCollectionLink(String collectionLink) {
		this.collectionLink = collectionLink;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "COLLECTION_DTIME", length = 7)
	public Date getCollectionDtime() {
		return this.collectionDtime;
	}

	public void setCollectionDtime(Date collectionDtime) {
		this.collectionDtime = collectionDtime;
	}

	@Column(name = "CREATE_NAME", length = 200)
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

	@Column(name = "UPDATE_NAME", length = 200)
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
	 * @return the collectionLinkTel
	 */
	@Column(name = "COLLECTION_LINK_TEL")
	public String getCollectionLinkTel() {
		return collectionLinkTel;
	}

	/**
	 * @param collectionLinkTel the collectionLinkTel to set
	 */
	public void setCollectionLinkTel(String collectionLinkTel) {
		this.collectionLinkTel = collectionLinkTel;
	}

	/**
	 * @return the departId
	 */
	@Column(name = "DEPART_ID")
	public Long getDepartId() {
		return departId;
	}

	/**
	 * @param departId the departId to set
	 */
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
}