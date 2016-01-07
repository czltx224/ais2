package com.xbwl.sys.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author CaoZhili
 * time Jun 28, 2011 6:37:39 PM
 */
public class BasValueAddFeeVo implements java.io.Serializable{
	
	private Long id;//序号1
	private String feeName;//服务费名称2
	private String feeSubject;//会计科目3
	private Double feeCount;//收费数额4
	private String autoFee;//1.自动收费，0.非自动收费5
	private String createName;//创建人6
	private Date createTime;//创建时间7
	private String updateName;//修改人8
	private Date updateTime;//修改时间9
	private String ts;//时间戳10
	private String typeCode;//付款人代号 0收货人，1代理，,由谁去付款（通过数据字典配置，不能写死）11
	private String typeName;//付款方式名称
	private String payRule;//收费规则 SQL表达式（TEXT格式，注意SQL注入）12
	private String feeLink;//收费环节 来自数据字典，大致类型有 传真录入 仓存 出库 13
	
	
	
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
