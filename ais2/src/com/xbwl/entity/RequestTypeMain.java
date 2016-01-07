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

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * RequestTypeMain entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "REQUEST_TYPE_MAIN")
public class RequestTypeMain implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private String requestType;//需求类别
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;

	// Constructors

	/** default constructor */
	public RequestTypeMain() {
	}

	/** minimal constructor */
	public RequestTypeMain(Long id) {
		this.id = id;
	}

	/** full constructor */
	public RequestTypeMain(Long id, String requestType, String createName,
			Date createTime, String updateName, Date updateTime, String ts) {
		this.id = id;
		this.requestType = requestType;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_REQUEST_TYPE_MAIN")
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "REQUEST_TYPE", length = 50)
	public String getRequestType() {
		return this.requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd")
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

	@JSON(format="yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

}