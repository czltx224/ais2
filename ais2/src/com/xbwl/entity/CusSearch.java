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
 * CusSearch entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CUS_SEARCH")
public class CusSearch implements java.io.Serializable, AuditableEntity {

	// Fields

	private Long id;
	private String tableCh;// 表中文名
	private String tableEn;// 表英文名
	private String searchStatement;// 查询条件SQL
	private String createName;// 创建人
	private Date createTime;// 创建时间
	private String updateName;// 修改人
	private Date updateTime;// 修改时间
	private String ts;// 时间戳
	private String departCode;// 部门编码

	private String title;// 查询名称
	private String searchChinese;// 查询语句中文描述

	// Constructors

	/** default constructor */
	public CusSearch() {
	}

	/** minimal constructor */
	public CusSearch(Long id) {
		this.id = id;
	}

	/** full constructor */
	public CusSearch(Long id, String tableCh, String tableEn,
			String searchStatement, String createName, Date createTime,
			String updateName, Date updateTime, String ts, String departCode) {
		this.id = id;
		this.tableCh = tableCh;
		this.tableEn = tableEn;
		this.searchStatement = searchStatement;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.departCode = departCode;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_CUS_SEARCH")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TABLE_CH", length = 50)
	public String getTableCh() {
		return this.tableCh;
	}

	public void setTableCh(String tableCh) {
		this.tableCh = tableCh;
	}

	@Column(name = "TABLE_EN", length = 50)
	public String getTableEn() {
		return this.tableEn;
	}

	public void setTableEn(String tableEn) {
		this.tableEn = tableEn;
	}

	@Column(name = "SEARCH_STATEMENT", length = 2000)
	public String getSearchStatement() {
		return this.searchStatement;
	}

	public void setSearchStatement(String searchStatement) {
		this.searchStatement = searchStatement;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd")
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

	@JSON(format = "yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "TS", length = 20)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Column(name = "DEPART_CODE", length = 20)
	public String getDepartCode() {
		return this.departCode;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}

	@Column(name = "TITLE", length = 50)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "SEARCH_CHINESE", length = 2000)
	public String getSearchChinese() {
		return searchChinese;
	}

	public void setSearchChinese(String searchChinese) {
		this.searchChinese = searchChinese;
	}

}