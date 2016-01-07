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
 * OprRequestDo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_REQUEST_DO")
public class OprRequestDo implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;// 序号
	private Long dno;// 配送单号
	private String requestStage;// 执行阶段
	private String request;// 执行要求
	private String oprMan;// 执行人
	private Long isOpr;// 是否执行
	private String requestType;// 类型
	private String createName;// 创建人
	private String remark;// 备注
	private Date createTime;// 创建时间
	private String updateName;// 修改人
	private Date updateTime;// 修改时间
	private String ts;// 时间戳
    private Long isException;//是否异常
	// Constructors

	/** default constructor */
	public OprRequestDo() {
		this.isOpr=0l;
	}

	/** minimal constructor */
	public OprRequestDo(Long id, Long dno, String requestStage, String request,
			Long isOpr, String requestType) {
		this.id = id;
		this.dno = dno;
		this.requestStage = requestStage;
		this.request = request;
		this.isOpr = isOpr;
		this.requestType = requestType;
	}

	/** full constructor */
	public OprRequestDo(Long id, Long dno, String requestStage, String request,
			String oprMan, Long isOpr, String requestType, String createName,
			String remark, Date createTime, String updateName, Date updateTime,
			String ts) {
		this.id = id;
		this.dno = dno;
		this.requestStage = requestStage;
		this.request = request;
		this.oprMan = oprMan;
		this.isOpr = isOpr;
		this.requestType = requestType;
		this.createName = createName;
		this.remark = remark;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_REQUEST_DO")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "D_NO", nullable = false)
	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	@Column(name = "REQUEST_STAGE", nullable = false)
	public String getRequestStage() {
		return this.requestStage;
	}

	public void setRequestStage(String requestStage) {
		this.requestStage = requestStage;
	}

	@Column(name = "REQUEST", nullable = false)
	public String getRequest() {
		return this.request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	@Column(name = "OPR_MAN")
	public String getOprMan() {
		return this.oprMan;
	}

	public void setOprMan(String oprMan) {
		this.oprMan = oprMan;
	}

	@Column(name = "IS_OPR")
	public Long getIsOpr() {
		return this.isOpr;
	}

	public void setIsOpr(Long isOpr) {
		this.isOpr = isOpr;
	}

	@Column(name = "REQUEST_TYPE")
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

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME")
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

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS")
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	@Column(name = "IS_EXCEPTION")
	public Long getIsException() {
		return isException;
	}

	public void setIsException(Long isException) {
		this.isException = isException;
	}
	
}