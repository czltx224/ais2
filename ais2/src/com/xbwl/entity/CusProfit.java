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
 * CusProfit entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CUS_PROFIT")
public class CusProfit implements java.io.Serializable {

	// Fields

	private Long id;
	private Date proTime;
	private Double fiIncome;
	private Long fiCost;
	private Long cusId;

	// Constructors

	/** default constructor */
	public CusProfit() {
	}

	/** minimal constructor */
	public CusProfit(Long id) {
		this.id = id;
	}

	/** full constructor */
	public CusProfit(Long id, Date proTime, Double fiIncome, Long fiCost,
			Long cusId) {
		this.id = id;
		this.proTime = proTime;
		this.fiIncome = fiIncome;
		this.fiCost = fiCost;
		this.cusId = cusId;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_CUS_PROFIT")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "PRO_TIME", length = 7)
	public Date getProTime() {
		return this.proTime;
	}

	public void setProTime(Date proTime) {
		this.proTime = proTime;
	}

	@Column(name = "FI_INCOME", precision = 10)
	public Double getFiIncome() {
		return this.fiIncome;
	}

	public void setFiIncome(Double fiIncome) {
		this.fiIncome = fiIncome;
	}

	@Column(name = "FI_COST", precision = 10, scale = 0)
	public Long getFiCost() {
		return this.fiCost;
	}

	public void setFiCost(Long fiCost) {
		this.fiCost = fiCost;
	}

	@Column(name = "CUS_ID", precision = 22, scale = 0)
	public Long getCusId() {
		return this.cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

}