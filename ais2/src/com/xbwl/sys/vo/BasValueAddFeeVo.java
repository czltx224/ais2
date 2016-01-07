package com.xbwl.sys.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author CaoZhili
 * time Jun 28, 2011 6:37:39 PM
 */
public class BasValueAddFeeVo implements java.io.Serializable{
	
	private Long id;//���1
	private String feeName;//���������2
	private String feeSubject;//��ƿ�Ŀ3
	private Double feeCount;//�շ�����4
	private String autoFee;//1.�Զ��շѣ�0.���Զ��շ�5
	private String createName;//������6
	private Date createTime;//����ʱ��7
	private String updateName;//�޸���8
	private Date updateTime;//�޸�ʱ��9
	private String ts;//ʱ���10
	private String typeCode;//�����˴��� 0�ջ��ˣ�1����,��˭ȥ���ͨ�������ֵ����ã�����д����11
	private String typeName;//���ʽ����
	private String payRule;//�շѹ��� SQL���ʽ��TEXT��ʽ��ע��SQLע�룩12
	private String feeLink;//�շѻ��� ���������ֵ䣬���������� ����¼�� �ִ� ���� 13
	
	
	
	public BasValueAddFeeVo() {
		super();
	}
	public BasValueAddFeeVo(Long id) {
		super();
		this.id = id;
	}
	public BasValueAddFeeVo(Long id, String feeName, String feeSubject,
			Double feeCount, String autoFee, String createName, Date createTime,
			String updateName, Date updateTime, String ts, String typeCode,
			String typeName, String payRule, String feeLink) {
		super();
		this.id = id;
		this.feeName = feeName;
		this.feeSubject = feeSubject;
		this.feeCount = feeCount;
		this.autoFee = autoFee;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.typeCode = typeCode;
		this.typeName = typeName;
		this.payRule = payRule;
		this.feeLink = feeLink;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public String getFeeSubject() {
		return feeSubject;
	}
	public void setFeeSubject(String feeSubject) {
		this.feeSubject = feeSubject;
	}
	public Double getFeeCount() {
		return feeCount;
	}
	public void setFeeCount(Double feeCount) {
		this.feeCount = feeCount;
	}
	public String getAutoFee() {
		return autoFee;
	}
	public void setAutoFee(String autoFee) {
		this.autoFee = autoFee;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getPayRule() {
		return payRule;
	}
	public void setPayRule(String payRule) {
		this.payRule = payRule;
	}
	public String getFeeLink() {
		return feeLink;
	}
	public void setFeeLink(String feeLink) {
		this.feeLink = feeLink;
	}
	
}
