package com.xbwl.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ProductAnalyse entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PRODUCT_ANALYSE")
public class ProductAnalyse implements java.io.Serializable {

	// Fields

	private Long id;
	private String productType;//��Ʒ����
	private Long productTicket;//Ʊ��
	private Long productPiece;//����
	private Double productWeight;//����
	private Double productIncome;//����
	private String trafficMode;//���䷲��
	private Date countTime;//ͳ��ʱ��

	// Constructors

	/** default constructor */
	public ProductAnalyse() {
	}

	/** minimal constructor */
	public ProductAnalyse(Long id) {
		this.id = id;
	}

	// Property accessors
	@Id
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

	@Column(name = "PRODUCT_TICKET", precision = 22, scale = 0)
	public Long getProductTicket() {
		return this.productTicket;
	}

	public void setProductTicket(Long productTicket) {
		this.productTicket = productTicket;
	}

	@Column(name = "PRODUCT_PIECE", precision = 22, scale = 0)
	public Long getProductPiece() {
		return this.productPiece;
	}

	public void setProductPiece(Long productPiece) {
		this.productPiece = productPiece;
	}

	@Column(name = "PRODUCT_WEIGHT", precision = 10)
	public Double getProductWeight() {
		return this.productWeight;
	}

	public void setProductWeight(Double productWeight) {
		this.productWeight = productWeight;
	}

	@Column(name = "PRODUCT_INCOME", precision = 10)
	public Double getProductIncome() {
		return this.productIncome;
	}

	public void setProductIncome(Double productIncome) {
		this.productIncome = productIncome;
	}

	@Column(name = "TRAFFIC_MODE", length = 20)
	public String getTrafficMode() {
		return this.trafficMode;
	}

	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "COUNT_TIME", length = 7)
	public Date getCountTime() {
		return this.countTime;
	}

	public void setCountTime(Date countTime) {
		this.countTime = countTime;
	}

}