package com.xbwl.entity;

import java.io.Serializable;
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
 * OprBillladingPrintManager entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_BILLLADING_PRINT_MANAGER")
public class OprBillladingPrintManager implements Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long sonderzug;//是否专车
	private String takeMode;//提货方式
	private String distributionMode;//配送方式
	private String departName;//业务部门名称
	private Long departId;//业务部门ID
	private String complaintTel;//投诉电话
	private String leftContext;//左上角广告
	private String title;//标题
	private String createName;// 创建人
	private Date createTime;//创建时间
	private String updateName;//修改人
	private Date updateTime;//修改时间
	private String ts;//时间戳

	// Constructors

	/** default constructor */
	public OprBillladingPrintManager() {
	}

	/** minimal constructor */
	public OprBillladingPrintManager(Long id, String departName, Long departId) {
		this.id = id;
		this.departName = departName;
		this.departId = departId;
	}

	/** full constructor */
	public OprBillladingPrintManager(Long id, Long sonderzug, String takeMode,
			String distributionMode, String departName, Long departId,
			String leftContext, String complaintTel, String title,
			String createName, Date createTime, String updateName,
			Date updateTime, String ts) {
		this.id = id;
		this.sonderzug = sonderzug;
		this.takeMode = takeMode;
		this.distributionMode = distributionMode;
		this.departName = departName;
		this.departId = departId;
		this.leftContext = leftContext;
		this.complaintTel = complaintTel;
		this.title = title;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_BILLLADING_PRINT ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SONDERZUG", precision = 22, scale = 0)
	public Long getSonderzug() {
		return this.sonderzug;
	}

	public void setSonderzug(Long sonderzug) {
		this.sonderzug = sonderzug;
	}

	@Column(name = "TAKE_MODE", length = 20)
	public String getTakeMode() {
		return this.takeMode;
	}

	public void setTakeMode(String takeMode) {
		this.takeMode = takeMode;
	}

	@Column(name = "DISTRIBUTION_MODE", length = 20)
	public String getDistributionMode() {
		return this.distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	@Column(name = "DEPART_NAME", nullable = false, length = 50)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "DEPART_ID", nullable = false, precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "LEFT_CONTEXT", length = 200)
	public String getLeftContext() {
		return this.leftContext;
	}

	public void setLeftContext(String leftContext) {
		this.leftContext = leftContext;
	}

	@Column(name = "COMPLAINT_TEL", length = 100)
	public String getComplaintTel() {
		return this.complaintTel;
	}

	public void setComplaintTel(String complaintTel) {
		this.complaintTel = complaintTel;
	}

	@Column(name = "TITLE", length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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

}