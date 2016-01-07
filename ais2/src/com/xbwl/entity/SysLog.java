package com.xbwl.entity;

// Generated 2010-11-26 11:23:15 by Hibernate Tools 3.2.1.GA
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts2.json.annotations.JSON;

/**
 * 系统日志类
 * @author yab
 */
@Entity
@Table(name = "SYS_LOG")
public class SysLog implements Serializable {

	private static final long serialVersionUID = 9158121894294668798L;

	private Long id;
	private String moduleName;
	private String logType;
	private String operAccount;
	private String operDetail;
	private String operStatus;
	private String clientIp;

	private Date createdtime;
	private Long invokeTime;
	
	private Long startMem;
	private Long endMem;
	
	private Long startTotal;
	private Long endTotal;

	public Long getStartMem() {
		return startMem;
	}

	public void setStartMem(Long startMem) {
		this.startMem = startMem;
	}

	public Long getEndMem() {
		return endMem;
	}

	public void setEndMem(Long endMem) {
		this.endMem = endMem;
	}

	public SysLog() {
	}

	public SysLog(Long id, Date createdtime) {
		this.id = id;
		this.createdtime = createdtime;
	}

	public SysLog(Long id, String moduleName, String logType, String operAccount,
			String operDetail, String operStatus, String clientIp,
			Date createdtime) {
		this.id = id;
		this.moduleName = moduleName;
		this.logType = logType;
		this.operAccount = operAccount;
		this.operDetail = operDetail;
		this.operStatus = operStatus;
		this.clientIp = clientIp;
		this.createdtime = createdtime;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sysLogGenerator")
	@SequenceGenerator(name = "sysLogGenerator", sequenceName = "SEQ_SYS_LOG")
	@Column(name = "ID", precision = 9, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "MODULE_NAME", length = 50)
	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Column(name = "LOG_TYPE")
	public String getLogType() {
		return this.logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	@Column(name = "OPER_ACCOUNT", length = 50)
	public String getOperAccount() {
		return this.operAccount;
	}

	public void setOperAccount(String operAccount) {
		this.operAccount = operAccount;
	}

	@Column(name = "OPER_DETAIL", length = 1024)
	public String getOperDetail() {
		return this.operDetail;
	}

	public void setOperDetail(String operDetail) {
		this.operDetail = operDetail;
	}

	@Column(name = "OPER_STATUS", length = 50)
	public String getOperStatus() {
		return this.operStatus;
	}

	public void setOperStatus(String operStatus) {
		this.operStatus = operStatus;
	}

	@Column(name = "CLIENT_IP", length = 64)
	public String getClientIp() {
		return this.clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDTIME", nullable = false, length = 7)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}
	
	@Column(name="INVOKE_TIME")
	public Long getInvokeTime() {
		return invokeTime;
	}

	public void setInvokeTime(Long invokeTime) {
		this.invokeTime = invokeTime;
	}
	
	public String toString() {
		return new ReflectionToStringBuilder(this,ToStringStyle.SHORT_PREFIX_STYLE){
			protected boolean accept(Field field) {
				return super.accept(field);
			}
		}.toString();
	}

	public Long getStartTotal() {
		return startTotal;
	}

	public void setStartTotal(Long startTotal) {
		this.startTotal = startTotal;
	}

	public Long getEndTotal() {
		return endTotal;
	}

	public void setEndTotal(Long endTotal) {
		this.endTotal = endTotal;
	}

}
