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
 * OprHistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "EDI_OPR_HISTORY",schema = "AISUSER")
public class EDIOprHistory implements java.io.Serializable {

	// Fields

	private Long id;
	private String oprName;//节点名称
	private Long oprNode;//节点编号
	private String oprComment;//操作内容
	private Date oprTime;//操作时间
	private String oprMan;//操作人
	private String oprDepart;//操作部门
	private Long dno;//配送单号
	private Long oprType;//节点类型

	// Constructors

	/** default constructor */
	public EDIOprHistory() {
	}

	/** minimal constructor */
	public EDIOprHistory(Long id) {
		this.id = id;
	}

	/** full constructor */
	public EDIOprHistory(Long id, String oprName, Long oprNode, String oprComment,
			Date oprTime, String oprMan, String oprDepart, Long dno,
			Long oprType) {
		this.id = id;
		this.oprName = oprName;
		this.oprNode = oprNode;
		this.oprComment = oprComment;
		this.oprTime = oprTime;
		this.oprMan = oprMan;
		this.oprDepart = oprDepart;
		this.dno = dno;
		this.oprType = oprType;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "LXY.SEQ_OPR_HISTORY_ID ")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OPR_NAME")
	public String getOprName() {
		return this.oprName;
	}

	public void setOprName(String oprName) {
		this.oprName = oprName;
	}

	@Column(name = "OPR_NODE", precision = 22, scale = 0)
	public Long getOprNode() {
		return this.oprNode;
	}

	public void setOprNode(Long oprNode) {
		this.oprNode = oprNode;
	}

	@Column(name = "OPR_COMMENT")
	public String getOprComment() {
		return this.oprComment;
	}

	public void setOprComment(String oprComment) {
		this.oprComment = oprComment;
	}

	@JSON(format = "yyyy-MM-dd HH:mm")
	@Column(name = "OPR_TIME")
	public Date getOprTime() {
		return this.oprTime;
	}

	public void setOprTime(Date oprTime) {
		this.oprTime = oprTime;
	}

	@Column(name = "OPR_MAN")
	public String getOprMan() {
		return this.oprMan;
	}

	public void setOprMan(String oprMan) {
		this.oprMan = oprMan;
	}

	@Column(name = "OPR_DEPART")
	public String getOprDepart() {
		return this.oprDepart;
	}

	public void setOprDepart(String oprDepart) {
		this.oprDepart = oprDepart;
	}

	@Column(name = "D_NO", precision = 22, scale = 0)
	public Long getDno() {
		return this.dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "OPR_TYPE", precision = 22, scale = 0)
	public Long getOprType() {
		return this.oprType;
	}

	public void setOprType(Long oprType) {
		this.oprType = oprType;
	}

}