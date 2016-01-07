package com.xbwl.flow.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 表单字段查询VO
 *@author LiuHao
 *@time Feb 15, 2012 4:48:44 PM
 */
public class FlowFormfieldVo {
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
	private String fieldTypeName;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFormId() {
		return this.formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Long getHtmlType() {
		return this.htmlType;
	}

	public void setHtmlType(Long htmlType) {
		this.htmlType = htmlType;
	}

	public Long getFieldType() {
		return this.fieldType;
	}

	public void setFieldType(Long fieldType) {
		this.fieldType = fieldType;
	}

	public Long getFieldAttr() {
		return this.fieldAttr;
	}

	public void setFieldAttr(Long fieldAttr) {
		this.fieldAttr = fieldAttr;
	}

	public String getFieldCheck() {
		return this.fieldCheck;
	}

	public void setFieldCheck(String fieldCheck) {
		this.fieldCheck = fieldCheck;
	}

	public String getLabelName() {
		return this.labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Long getOrderFields() {
		return this.orderFields;
	}

	public void setOrderFields(Long orderFields) {
		this.orderFields = orderFields;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	/**
	 * @return the fieldTypeName
	 */
	public String getFieldTypeName() {
		return fieldTypeName;
	}

	/**
	 * @param fieldTypeName the fieldTypeName to set
	 */
	public void setFieldTypeName(String fieldTypeName) {
		this.fieldTypeName = fieldTypeName;
	}
	
	
	
}
