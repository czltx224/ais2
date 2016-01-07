package com.xbwl.entity;

import static javax.persistence.GenerationType.SEQUENCE;

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
 * TbImages entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_IMAGES")
public class TbImages implements java.io.Serializable {

	// Fields

	private Long id;
	private String billno;   //配送单号
	private String imgpath;       //图片名称
	private String username;        //上传人
	private Date senddate;          //上传日期

	// Constructors

	/** default constructor */
	public TbImages() {
	}

	/** minimal constructor */
	public TbImages(Long id, String billno, String imgpath) {
		this.id = id;
		this.billno = billno;
		this.imgpath = imgpath;
	}

	/** full constructor */
	public TbImages(Long id, String billno, String imgpath, String username,
			Date senddate) {
		this.id = id;
		this.billno = billno;
		this.imgpath = imgpath;
		this.username = username;
		this.senddate = senddate;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName="SEQ_TB_IMAGES")
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "BILLNO", nullable = false, length = 20)
	public String getBillno() {
		return this.billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	@Column(name = "IMGPATH", nullable = false, length = 50)
	public String getImgpath() {
		return this.imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	@Column(name = "USERNAME", length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SENDDATE", length = 7)
	public Date getSenddate() {
		return this.senddate;
	}

	public void setSenddate(Date senddate) {
		this.senddate = senddate;
	}

}