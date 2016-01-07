package com.xbwl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

/**
 * DigitalChinaExchange entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DIGITAL_CHINA_EXCHANGE")
public class DigitalChinaExchange implements java.io.Serializable {

	// Fields

	private Long id;
	private String otherId;
	private Long dno;
	private String type;
	private Date createTime;
	private String free1;
	private String free2;

	// Constructors

	/** default constructor */
	public DigitalChinaExchange() {
	}

	/** minimal constructor */
	public DigitalChinaExchange(Long id) {
		this.id = id;
	}

	/** full constructor */
	public DigitalChinaExchange(Long id, String otherId, Long dno, String type,
			Date createTime, String free1, String free2) {
		this.id = id;
		this.otherId = otherId;
		this.dno = dno;
		this.type = type;
		this.createTime = createTime;
		this.free1 = free1;
		this.free2 = free2;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_DIGITAL_CHINA_EXCHANGE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OTHER_ID", length = 50)
	public String getOtherId() {
		return this.otherId;
	}

	public void setOtherId(String otherId) {
		this.otherId = otherId;
	}

	@Column(name = "D_NO", precision = 22, scale = 0)
	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "TYPE", length = 1)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JSON(format="yyyy-MM-dd hh24:mm")
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "FREE1", length = 200)
	public String getFree1() {
		return this.free1;
	}

	public void setFree1(String free1) {
		this.free1 = free1;
	}

	@Column(name = "FREE2", length = 200)
	public String getFree2() {
		return this.free2;
	}

	public void setFree2(String free2) {
		this.free2 = free2;
	}

}