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
 * �ɱ���
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FI_COST")
public class FiCost implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String costType;   // �ɱ�����(ǩ���ɱ�\����ɱ�\��ת�ɱ�\�ⷢ�ɱ�\�����ɱ�)
	private String costTypeDetail;  //�ɱ�С�ࣨ���ǩ��\�ͻ�ǩ��\����ǩ��\��ת����\ר��\�����ɱ���
	private Double costAmount;  // �ɱ����
	private String dataSource;            // ����Դ
	private String sourceSignNo;       //��Դ����
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private String departName;  //�����˲���
	private Long departId;            // ��������
	private Long dno;  //���͵���
	
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