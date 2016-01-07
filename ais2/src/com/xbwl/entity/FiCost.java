package com.xbwl.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
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
 * FiCost entity.
 * 成本表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_COST")
public class FiCost implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String costType;   // 成本大类(签单成本\提货成本\中转成本\外发成本\其他成本)
	private String costTypeDetail;  //成本小类（提货签单\送货签单\干线签单\中转增派\专车\返货成本）
	private Double costAmount;  // 成本金额
	private String dataSource;            // 数据源
	private String sourceSignNo;       //来源单号
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private String departName;  //创建人部门
	private Long departId;            // 创建部门
	private Long dno;  //配送单号
	
	private Long status=1l;
	// Constructors
	@Column(name = "STATUS", precision = 10)
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	/** default constructor */
	public FiCost() {
	}

	/** full constructor */
	public FiCost(Long id, String costType, String costTypeDetail,
			Double costAmount, String dataSource, String sourceSignNo,
			String createName, Date createTime, String updateName,
			Date updateTime, String ts, String createDept, Long createDeptid) {
		this.id = id;
		this.costType = costType;
		this.costTypeDetail = costTypeDetail;
		this.costAmount = costAmount;
		this.dataSource = dataSource;
		this.sourceSignNo = sourceSignNo;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
 
	}

	// Property accessors

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_COST")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "COST_TYPE", length = 20)
	public String getCostType() {
		return this.costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	@Column(name = "COST_TYPE_DETAIL", length = 30)
	public String getCostTypeDetail() {
		return this.costTypeDetail;
	}

	public void setCostTypeDetail(String costTypeDetail) {
		this.costTypeDetail = costTypeDetail;
	}

	@Column(name = "COST_AMOUNT", precision = 10)
	public Double getCostAmount() {
		return this.costAmount;
	}

	public void setCostAmount(Double costAmount) {
		this.costAmount = costAmount;
	}

	@Column(name = "DATA_SOURCE", length = 30)
	public String getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	@Column(name = "SOURCE_SIGN_NO", length = 50)
	public String getSourceSignNo() {
		return this.sourceSignNo;
	}

	public void setSourceSignNo(String sourceSignNo) {
		this.sourceSignNo = sourceSignNo;
	}

	@Column(name = "CREATE_NAME", length = 10)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME", length = 10)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "D_NO")
	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}
}