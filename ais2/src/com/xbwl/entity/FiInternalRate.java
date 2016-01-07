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
 * FiInternalRate entity.
 * 
 * @author MyEclipse Persistence Tools
 * �ڲ�����ʵ����
 */
@Entity
@Table(name = "FI_INTERNAL_RATE")
public class FiInternalRate implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;//����ID
	private Long startDepartId;//ʼ������id
	private String startDepartName;//ʼ������
	private Long endDepartId;//���ﲿ��id
	private String endDepartName;//���ﲿ��
	private Double xbFrommention;//�°�����
	private Double xbFrommentionLowest;//�°��������һƱ
	private Double xbCity;//�°������ͻ�
	private Double xbCityLowest;//�°������ͻ����һƱ
	private Double xbSuburbs;//�°���ͻ�
	private Double xbSuburbsLowest;//�°���ͻ����һƱ
	private Double transitFrommention;//��ת����
	private Double transitFrommentionLowest;//��ת�������һƱ
	private Double transitCity;//��ת�����ͻ�
	private Double transitCityLowest;//��ת�����ͻ����һƱ
	private Double transitSuburbs;//��ת�����ͻ�
	private Double transitSuburbsLowest;//��ת�����ͻ����һƱ
	private Double outFrommention;//�ⷢ����
	private Double outFrommentionLowest;//�ⷢ�������һƱ
	private Double outDelivery;//�ⷢ�ͻ�
	private Double outDeliveryLowest;//�ⷢ�ͻ����һƱ
	private String remark;//��ע
	private Long departId;//��������id
	private String departName;//��������
	private Date createTime;//����ʱ��
	private String createName;//������
	private Date updateTime;//�޸�ʱ��
	private String updateName;//�޸���
	private Long status;//״̬��0���ϣ�1����
	private String ts;//ʱ���

	// Constructors

	/** default constructor */
	public FiInternalRate() {
	}

	/** minimal constructor */
	public FiInternalRate(Long id, Long status) {
		this.id = id;
		this.status = status;
	}

	/** full constructor */
	public FiInternalRate(Long id, Long startDepartId, String startDepartName,
			Long endDepartId, String endDepartName, Double xbFrommention,
			Double xbFrommentionLowest, Double xbCity, Double xbCityLowest,
			Double xbSuburbs, Double xbSuburbsLowest,
			Double transitFrommention, Double transitFrommentionLowest,
			Double transitCity, Double transitCityLowest,
			Double transitSuburbs, Double transitSuburbsLowest,
			Double outFrommention, Double outFrommentionLowest,
			Double outDelivery, Double outDeliveryLowest, String remark,
			Long departId, String departName, Date createTime,
			String createName, Date updateTime, String updateName, Long status,
			String ts) {
		this.id = id;
		this.startDepartId = startDepartId;
		this.startDepartName = startDepartName;
		this.endDepartId = endDepartId;
		this.endDepartName = endDepartName;
		this.xbFrommention = xbFrommention;
		this.xbFrommentionLowest = xbFrommentionLowest;
		this.xbCity = xbCity;
		this.xbCityLowest = xbCityLowest;
		this.xbSuburbs = xbSuburbs;
		this.xbSuburbsLowest = xbSuburbsLowest;
		this.transitFrommention = transitFrommention;
		this.transitFrommentionLowest = transitFrommentionLowest;
		this.transitCity = transitCity;
		this.transitCityLowest = transitCityLowest;
		this.transitSuburbs = transitSuburbs;
		this.transitSuburbsLowest = transitSuburbsLowest;
		this.outFrommention = outFrommention;
		this.outFrommentionLowest = outFrommentionLowest;
		this.outDelivery = outDelivery;
		this.outDeliveryLowest = outDeliveryLowest;
		this.remark = remark;
		this.departId = departId;
		this.departName = departName;
		this.createTime = createTime;
		this.createName = createName;
		this.updateTime = updateTime;
		this.updateName = updateName;
		this.status = status;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_FI_INTERNAL_RATE ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "START_DEPART_ID", precision = 22, scale = 0)
	public Long getStartDepartId() {
		return this.startDepartId;
	}

	public void setStartDepartId(Long startDepartId) {
		this.startDepartId = startDepartId;
	}

	@Column(name = "START_DEPART_NAME", length = 50)
	public String getStartDepartName() {
		return this.startDepartName;
	}

	public void setStartDepartName(String startDepartName) {
		this.startDepartName = startDepartName;
	}

	@Column(name = "END_DEPART_ID", precision = 22, scale = 0)
	public Long getEndDepartId() {
		return this.endDepartId;
	}

	public void setEndDepartId(Long endDepartId) {
		this.endDepartId = endDepartId;
	}

	@Column(name = "END_DEPART_NAME", length = 50)
	public String getEndDepartName() {
		return this.endDepartName;
	}

	public void setEndDepartName(String endDepartName) {
		this.endDepartName = endDepartName;
	}

	@Column(name = "XB_FROMMENTION", precision = 10)
	public Double getXbFrommention() {
		return this.xbFrommention;
	}

	public void setXbFrommention(Double xbFrommention) {
		this.xbFrommention = xbFrommention;
	}

	@Column(name = "XB_FROMMENTION_LOWEST", precision = 10)
	public Double getXbFrommentionLowest() {
		return this.xbFrommentionLowest;
	}

	public void setXbFrommentionLowest(Double xbFrommentionLowest) {
		this.xbFrommentionLowest = xbFrommentionLowest;
	}

	@Column(name = "XB_CITY", precision = 10)
	public Double getXbCity() {
		return this.xbCity;
	}

	public void setXbCity(Double xbCity) {
		this.xbCity = xbCity;
	}

	@Column(name = "XB_CITY_LOWEST", precision = 10)
	public Double getXbCityLowest() {
		return this.xbCityLowest;
	}

	public void setXbCityLowest(Double xbCityLowest) {
		this.xbCityLowest = xbCityLowest;
	}

	@Column(name = "XB_SUBURBS", precision = 10)
	public Double getXbSuburbs() {
		return this.xbSuburbs;
	}

	public void setXbSuburbs(Double xbSuburbs) {
		this.xbSuburbs = xbSuburbs;
	}

	@Column(name = "XB_SUBURBS_LOWEST", precision = 10)
	public Double getXbSuburbsLowest() {
		return this.xbSuburbsLowest;
	}

	public void setXbSuburbsLowest(Double xbSuburbsLowest) {
		this.xbSuburbsLowest = xbSuburbsLowest;
	}

	@Column(name = "TRANSIT_FROMMENTION", precision = 10)
	public Double getTransitFrommention() {
		return this.transitFrommention;
	}

	public void setTransitFrommention(Double transitFrommention) {
		this.transitFrommention = transitFrommention;
	}

	@Column(name = "TRANSIT_FROMMENTION_LOWEST", precision = 10)
	public Double getTransitFrommentionLowest() {
		return this.transitFrommentionLowest;
	}

	public void setTransitFrommentionLowest(Double transitFrommentionLowest) {
		this.transitFrommentionLowest = transitFrommentionLowest;
	}

	@Column(name = "TRANSIT_CITY", precision = 10)
	public Double getTransitCity() {
		return this.transitCity;
	}

	public void setTransitCity(Double transitCity) {
		this.transitCity = transitCity;
	}

	@Column(name = "TRANSIT_CITY_LOWEST", precision = 10)
	public Double getTransitCityLowest() {
		return this.transitCityLowest;
	}

	public void setTransitCityLowest(Double transitCityLowest) {
		this.transitCityLowest = transitCityLowest;
	}

	@Column(name = "TRANSIT_SUBURBS", precision = 10)
	public Double getTransitSuburbs() {
		return this.transitSuburbs;
	}

	public void setTransitSuburbs(Double transitSuburbs) {
		this.transitSuburbs = transitSuburbs;
	}

	@Column(name = "TRANSIT_SUBURBS_LOWEST", precision = 10)
	public Double getTransitSuburbsLowest() {
		return this.transitSuburbsLowest;
	}

	public void setTransitSuburbsLowest(Double transitSuburbsLowest) {
		this.transitSuburbsLowest = transitSuburbsLowest;
	}

	@Column(name = "OUT_FROMMENTION", precision = 10)
	public Double getOutFrommention() {
		return this.outFrommention;
	}

	public void setOutFrommention(Double outFrommention) {
		this.outFrommention = outFrommention;
	}

	@Column(name = "OUT_FROMMENTION_LOWEST", precision = 10)
	public Double getOutFrommentionLowest() {
		return this.outFrommentionLowest;
	}

	public void setOutFrommentionLowest(Double outFrommentionLowest) {
		this.outFrommentionLowest = outFrommentionLowest;
	}

	@Column(name = "OUT_DELIVERY", precision = 10)
	public Double getOutDelivery() {
		return this.outDelivery;
	}

	public void setOutDelivery(Double outDelivery) {
		this.outDelivery = outDelivery;
	}

	@Column(name = "OUT_DELIVERY_LOWEST", precision = 10)
	public Double getOutDeliveryLowest() {
		return this.outDeliveryLowest;
	}

	public void setOutDeliveryLowest(Double outDeliveryLowest) {
		this.outDeliveryLowest = outDeliveryLowest;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "DEPART_ID", precision = 22, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "DEPART_NAME", length = 50)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
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

	@Column(name = "STATUS", nullable = false, precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

}