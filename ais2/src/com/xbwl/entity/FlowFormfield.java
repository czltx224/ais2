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
 * FlowFormfield entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FLOW_FORMFIELD")
public class FlowFormfield implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long formId;//表单ID
	private String fieldName;//字段名称
	private Long htmlType;//字段类型
	private Long fieldType;//表现形式
	private Long fieldAttr;//文本长度
	private String fieldCheck;//字段验证
	private String labelName;//显示名称
	private Long orderFields;//排序字段
	private Long status;//状态
	private Date createTime;//创建时间
	private String createName;//创建人
	private Date updateTime;//修改时间
	private String updateName;//修改人
	private String ts;

	// Constructors

	/** default constructor */
	public FlowFormfield() {
	}

	/** minimal constructor */
	public FlowFormfield(Long id) {
		this.id = id;
	}
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FLOW_FORMFIELD",allocationSize=1)
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "FORM_ID", precision = 10, scale = 0)
	public Long getFormId() {
		return this.formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	@Column(name = "FIELD_NAME", length = 50)
	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Column(name = "HTML_TYPE", precision = 10, scale = 0)
	public Long getHtmlType() {
		return this.htmlType;
	}

	public void setHtmlType(Long htmlType) {
		this.htmlType = htmlType;
	}

	@Column(name = "FIELD_TYPE", length = 32)
	public Long getFieldType() {
		return this.fieldType;
	}

	public void setFieldType(Long fieldType) {
		this.fieldType = fieldType;
	}

	@Column(name = "FIELD_ATTR", precision = 10, scale = 0)
	public Long getFieldAttr() {
		return this.fieldAttr;
	}

	public void setFieldAttr(Long fieldAttr) {
		this.fieldAttr = fieldAttr;
	}

	@Column(name = "FIELD_CHECK", length = 50)
	public String getFieldCheck() {
		return this.fieldCheck;
	}

	public void setFieldCheck(String fieldCheck) {
		this.fieldCheck = fieldCheck;
	}

	@Column(name = "LABEL_NAME", length = 50)
	public String getLabelName() {
		return this.labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	@Column(name = "ORDER_FIELDS", precision = 10, scale = 0)
	public Long getOrderFields() {
		return this.orderFields;
	}

	public void setOrderFields(Long orderFields) {
		this.orderFields = orderFields;
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