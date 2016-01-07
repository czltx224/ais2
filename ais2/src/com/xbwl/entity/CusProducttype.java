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

/**
 * CusProducttype entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CUS_PRODUCTTYPE")
public class CusProducttype implements java.io.Serializable {

	// Fields

	private Long id;
	private String productType;
	private Date procudeTime;
	private Double proWeight;

	// Constructors

	/** default constructor */
	public CusProducttype() {
	}

	/** minimal constructor */
	public CusProducttype(Long id) {
		this.id = id;
	}

	/** full constructor */
	public CusProducttype(Long id, String productType, Date procudeTime,
			Double proWeight) {
		this.id = id;
		this.productType = productType;
		this.procudeTime = procudeTime;
		this.proWeight = proWeight;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_CUS_PRODUCT_TYPE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PRODUCT_TYPE", length = 20)
	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "PROCUDE_TIME", length = 7)
	public Date getProcudeTime() {
		return this.procudeTime;
	}

	public void setProcudeTime(Date procudeTime) {
		this.procudeTime = procudeTime;
	}

	@Column(name = "PRO_WEIGHT", precision = 7, scale = 1)
	public Double getProWeight() {
		return this.proWeight;
	}

	public void setProWeight(Double proWeight) {
		this.proWeight = proWeight;
	}

}