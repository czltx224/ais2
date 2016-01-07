package com.xbwl.entity;
// default package

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
 * Customer entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CUSTOMER")
public class Customer implements java.io.Serializable,AuditableEntity{


	private Long id;
	private String cusCode; //客商编码
	private String cusName;//客商名称
	private String cusAdd;//客商地址
	private String custprop;//客商类型
	private String custshortname;//客商简称
	private String email;
	private String engname;//外文名称
	private String fax1;//传真1
	private String fax2;//传真2
	private String legalbody;//法人
	private String linkman1;//联系人1
	private String linkman2;//联系人2
	private String linkman3;//联系人3
	private String memo;//备注
	private String phone1;//电话1
	private String phone2;//电话2
	private String phone3;//电话3
	private String pkAreacl;//地区分类
	private String pkCubasdoc1;//客商总公司编码
	private String trade;//所属行业
	private String url;//web网址
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private String bankNumber;//银行账号
	private String createBank;//开户行
	private String settlement;//结算方式(现结\月结\预付)
	private Long isProjectcustomer;//是否项目客户(0:不是项目客户\1:是项目客户)
	
	private String ediUserId;//EDI对应客商ID(ct_user.user_id)
	private Long status=1l; //客商状态(0：表示停用，1：正常)
	
	private String area;
	private String developLevel;//开发阶段
	private String scopeBusiness;//经营范围
	private String financialPositon;//财务状况
	

	/** default constructor */
	public Customer() {
	}

	/** minimal constructor */
	public Customer(Long id, String cusName) {
		this.id = id;
		this.cusName = cusName;
	}

	
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_CUSTOMER")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "CUS_CODE", unique = true, length = 10)
	public String getCusCode() {
		return this.cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}

	@Column(name = "CUS_NAME", unique = true, nullable = false, length = 200)
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	@Column(name = "CUS_ADD", length = 200)
	public String getCusAdd() {
		return this.cusAdd;
	}

	public void setCusAdd(String cusAdd) {
		this.cusAdd = cusAdd;
	}

	@Column(name = "CUSTPROP",length = 10)
	public String getCustprop() {
		return this.custprop;
	}

	public void setCustprop(String custprop) {
		this.custprop = custprop;
	}

	@Column(name = "CUSTSHORTNAME", length = 100)
	public String getCustshortname() {
		return this.custshortname;
	}

	public void setCustshortname(String custshortname) {
		this.custshortname = custshortname;
	}

	@Column(name = "EMAIL", length = 200)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ENGNAME", length = 100)
	public String getEngname() {
		return this.engname;
	}

	public void setEngname(String engname) {
		this.engname = engname;
	}

	@Column(name = "FAX1", length = 20)
	public String getFax1() {
		return this.fax1;
	}

	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}

	@Column(name = "FAX2", length = 20)
	public String getFax2() {
		return this.fax2;
	}

	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}

	@Column(name = "LEGALBODY", length = 10)
	public String getLegalbody() {
		return this.legalbody;
	}

	public void setLegalbody(String legalbody) {
		this.legalbody = legalbody;
	}

	@Column(name = "LINKMAN1", length = 50)
	public String getLinkman1() {
		return this.linkman1;
	}

	public void setLinkman1(String linkman1) {
		this.linkman1 = linkman1;
	}

	@Column(name = "LINKMAN2", length = 50)
	public String getLinkman2() {
		return this.linkman2;
	}

	public void setLinkman2(String linkman2) {
		this.linkman2 = linkman2;
	}

	@Column(name = "LINKMAN3", length = 50)
	public String getLinkman3() {
		return this.linkman3;
	}

	public void setLinkman3(String linkman3) {
		this.linkman3 = linkman3;
	}

	@Column(name = "MEMO", length = 500)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "PHONE1", length = 13)
	public String getPhone1() {
		return this.phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	@Column(name = "PHONE2", length = 13)
	public String getPhone2() {
		return this.phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	@Column(name = "PHONE3", length = 13)
	public String getPhone3() {
		return this.phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	@Column(name = "PK_AREACL", length = 10)
	public String getPkAreacl() {
		return this.pkAreacl;
	}

	public void setPkAreacl(String pkAreacl) {
		this.pkAreacl = pkAreacl;
	}

	@Column(name = "PK_CUBASDOC1", length = 10)
	public String getPkCubasdoc1() {
		return this.pkCubasdoc1;
	}

	public void setPkCubasdoc1(String pkCubasdoc1) {
		this.pkCubasdoc1 = pkCubasdoc1;
	}

	@Column(name = "TRADE", length = 20)
	public String getTrade() {
		return this.trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	@Column(name = "URL", length = 50)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	@Column(name = "TS")
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "BANK_NUMBER", length = 20)
	public String getBankNumber() {
		return this.bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	@Column(name = "CREATE_BANK", length = 100)
	public String getCreateBank() {
		return this.createBank;
	}

	public void setCreateBank(String createBank) {
		this.createBank = createBank;
	}
	
	@Column(name = "SETTLEMENT", length = 20)
	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	@Column(name = "IS_PROJECTCUSTOMER", precision = 22, scale = 0)
	public Long getIsProjectcustomer() {
		return isProjectcustomer;
	}

	public void setIsProjectcustomer(Long isProjectcustomer) {
		this.isProjectcustomer = isProjectcustomer;
	}

	@Column(name = "EDI_USER_ID", length = 30)
	public String getEdiUserId() {
		return ediUserId;
	}

	public void setEdiUserId(String ediUserId) {
		this.ediUserId = ediUserId;
	}
	
	@Column(name = "STATUS", length = 1)
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	@Column(name = "AREA")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	@Column(name = "DEVELOP_LEVEL")
	public String getDevelopLevel() {
		return developLevel;
	}

	public void setDevelopLevel(String developLevel) {
		this.developLevel = developLevel;
	}
	@Column(name = "SCOPE_BUSINESS")
	public String getScopeBusiness() {
		return scopeBusiness;
	}

	public void setScopeBusiness(String scopeBusiness) {
		this.scopeBusiness = scopeBusiness;
	}
	@Column(name = "FINANCIAL_POSITON")
	public String getFinancialPositon() {
		return financialPositon;
	}

	public void setFinancialPositon(String financialPositon) {
		this.financialPositon = financialPositon;
	}
	
}