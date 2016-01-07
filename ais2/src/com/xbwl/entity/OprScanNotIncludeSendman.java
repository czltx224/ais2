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
 * ǩ�պͻص�ȷ�ձ����޳��ͻ�Աʵ����
 * OprScanNotIncludeSendman entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_SCAN_NOT_INCLUDE_SENDMAN", schema = "AISUSER")
public class OprScanNotIncludeSendman implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;//����
	private Long userId;//�ͻ�ԱID���û�ID��
	private String userName;//�ͻ�Ա���ƣ��û����ƣ�
	private String remark;//��ע
	private String createName;//������
	private Date createTime;//����ʱ��
	private String updateName;//�޸���
	private Date updateTime;//�޸�ʱ��
	private String ts;//ʱ���
	private Long departId;//����ID
	private String departName;//��������

	// Constructors

	/** default constructor */
	public OprScanNotIncludeSendman() {
	}

	/** minimal constructor */
	public OprScanNotIncludeSendman(Long id, String userName) {
		this.id = id;
		this.userName = userName;
	}

	/** full constructor */
	public OprScanNotIncludeSendman(Long id, Long userId, String userName,
			String remark, String createName, Date createTime,
			String updateName, Date updateTime, String ts) {
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.remark = remark;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_SCAN_NOT_SENDMAN")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "USER_ID", precision = 22, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", nullable = false, length = 100)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "REMARK", length = 4000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CREATE_NAME", length = 50)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_NAME", length = 50)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm")
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
	
	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "DEPART_NAME", length = 100)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}
}