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
 * FlowForminfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FLOW_FORMINFO")
public class FlowForminfo implements java.io.Serializable ,AuditableEntity{

	// Fields

	private Long id;
	private Long objType;//������(1:ʵ�ʱ�,2:�����)
	private Long tableType;//ʵ�ʱ�����(1:����\2:��ϸ��)
	private String objName;//������
	private String objDesc;//������
	private String objTablename;//���ݿ����
	private Long status;//״̬(0:����,1:����)
	private Date createTime;//����ʱ��
	private String createName;//������
	private Date updateTime;//�޸�ʱ��
	private String updateName;//�޸���
	private String ts;

	// Constructors

	/** default constructor */
	public FlowForminfo() {
	}

	/** minimal constructor */
	public FlowForminfo(Long id) {
		this.id = id;
	}


	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FLOW_FORMINFO",allocationSize=1)
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OBJ_TYPE", precision = 10, scale = 0)
	public Long getObjType() {
		return this.objType;
	}

	public void setObjType(Long objType) {
		this.objType = objType;
	}

	@Column(name = "TABLE_TYPE")
	public Long getTableType() {
		return this.tableType;
	}

	public void setTableType(Long tableType) {
		this.tableType = tableType;
	}

	@Column(name = "OBJ_NAME", length = 256)
	public String getObjName() {
		return this.objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	@Column(name = "OBJ_DESC", length = 1024)
	public String getObjDesc() {
		return this.objDesc;
	}

	public void setObjDesc(String objDesc) {
		this.objDesc = objDesc;
	}

	@Column(name = "OBJ_TABLENAME", length = 32)
	public String getObjTablename() {
		return this.objTablename;
	}

	public void setObjTablename(String objTablename) {
		this.objTablename = objTablename;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATE_TIME", length = 7)
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

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

}