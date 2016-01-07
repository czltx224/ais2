package com.xbwl.entity;
import java.util.Date;

import javax.persistence.Column;
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
 * ConsigneeDealPrice entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CONSIGNEE_DEAL_PRICE")
public class ConsigneeDealPrice implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String cusName;//收货人名称
	private String dispatchAgency;//发货代理
	private String contactWay;//联系电话
	private String ismobile;//是否是手机
	private Double cityOwnPrice;//市内自提价格
	private Double cityOwnMinPrice;//市内自提最低一票
	private Double flyOwnPrice;//机场自提价格
	private Double flyOwnMinPrice;//机场自提最低一票
	private Double citySendPrice;//市内送货价格
	private Double citySendMinPrice;//市内送货最低一票
	private String cusAddr;//客户地址
	private String sendRequire;//送货要求
	private Date startTime;//协议开始时间
	private Date stopTime;//协议终止时间
	private String isstop;//是否停用
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long departId;
	private String departName;

	// Constructors

	/** default constructor */
	public ConsigneeDealPrice() {
	}

	/** minimal constructor */
	public ConsigneeDealPrice(Long id) {
		this.id = id;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_CONSIGNEE_DEAL_PRICE ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUS_NAME", length = 20)
	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	@Column(name = "DISPATCH_AGENCY", length = 20)
	public String getDispatchAgency() {
		return this.dispatchAgency;
	}

	public void setDispatchAgency(String dispatchAgency) {
		this.dispatchAgency = dispatchAgency;
	}

	@Column(name = "CONTACT_WAY", length = 20)
	public String getContactWay() {
		return this.contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	@Column(name = "ISMOBILE", length = 1)
	public String getIsmobile() {
		return this.ismobile;
	}

	public void setIsmobile(String ismobile) {
		this.ismobile = ismobile;
	}

	@Column(name = "CITY_OWN_PRICE", precision = 126, scale = 0)
	public Double getCityOwnPrice() {
		return this.cityOwnPrice;
	}

	public void setCityOwnPrice(Double cityOwnPrice) {
		this.cityOwnPrice = cityOwnPrice;
	}

	@Column(name = "CITY_OWN_MIN_PRICE", precision = 126, scale = 0)
	public Double getCityOwnMinPrice() {
		return this.cityOwnMinPrice;
	}

	public void setCityOwnMinPrice(Double cityOwnMinPrice) {
		this.cityOwnMinPrice = cityOwnMinPrice;
	}

	@Column(name = "FLY_OWN_PRICE", precision = 126, scale = 0)
	public Double getFlyOwnPrice() {
		return this.flyOwnPrice;
	}

	public void setFlyOwnPrice(Double flyOwnPrice) {
		this.flyOwnPrice = flyOwnPrice;
	}

	@Column(name = "FLY_OWN_MIN_PRICE", precision = 126, scale = 0)
	public Double getFlyOwnMinPrice() {
		return this.flyOwnMinPrice;
	}

	public void setFlyOwnMinPrice(Double flyOwnMinPrice) {
		this.flyOwnMinPrice = flyOwnMinPrice;
	}

	@Column(name = "CITY_SEND_PRICE", precision = 126, scale = 0)
	public Double getCitySendPrice() {
		return this.citySendPrice;
	}

	public void setCitySendPrice(Double citySendPrice) {
		this.citySendPrice = citySendPrice;
	}

	@Column(name = "CITY_SEND_MIN_PRICE")
	public Double getCitySendMinPrice() {
		return this.citySendMinPrice;
	}

	public void setCitySendMinPrice(Double citySendMinPrice) {
		this.citySendMinPrice = citySendMinPrice;
	}

	@Column(name = "CUS_ADDR", length = 200)
	public String getCusAddr() {
		return this.cusAddr;
	}

	public void setCusAddr(String cusAddr) {
		this.cusAddr = cusAddr;
	}

	@Column(name = "SEND_REQUIRE", length = 200)
	public String getSendRequire() {
		return this.sendRequire;
	}

	public void setSendRequire(String sendRequire) {
		this.sendRequire = sendRequire;
	}

	@Temporal(TemporalType.DATE)
	@JSON(format="yyyy-MM-dd")
	@Column(name = "START_TIME", length = 7)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.DATE)
	@JSON(format="yyyy-MM-dd")
	@Column(name = "STOP_TIME", length = 7)
	public Date getStopTime() {
		return this.stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	@Column(name = "ISSTOP", length = 1)
	public String getIsstop() {
		return this.isstop;
	}

	public void setIsstop(String isstop) {
		this.isstop = isstop;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Temporal(TemporalType.DATE)
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

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS", length = 7)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	@Column(name = "DEPART_ID")
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	@Column(name = "DEPART_NAME")
	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}
	
}