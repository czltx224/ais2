package com.xbwl.entity;
// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * OprPrint entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_PRINT", schema = "AISUSER")
public class OprPrint implements java.io.Serializable {

	// Fields

	private Long id;
	private Date printTime;
	private String printMan;
	private String sourceNo;
	
	private String sourceclass;

	// Constructors

	/** default constructor */
	public OprPrint() {
	}

	/** minimal constructor */
	public OprPrint(Long id) {
		this.id = id;
	}

	/** full constructor */
	public OprPrint(Long id, Date printTime, String printMan) {
		this.id = id;
		this.printTime = printTime;
		this.printMan = printMan;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_PRINT ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PRINT_TIME", length = 7)
	public Date getPrintTime() {
		return this.printTime;
	}

	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	@Column(name = "PRINT_MAN", length = 20)
	public String getPrintMan() {
		return this.printMan;
	}

	public void setPrintMan(String printMan) {
		this.printMan = printMan;
	}

	public String getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}

	public String getSourceclass() {
		return sourceclass;
	}

	public void setSourceclass(String sourceclass) {
		this.sourceclass = sourceclass;
	}

}