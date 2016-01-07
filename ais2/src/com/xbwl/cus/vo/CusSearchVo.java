package com.xbwl.cus.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author CaoZhili time Oct 24, 2011 2:02:19 PM
 */

public class CusSearchVo implements Serializable {

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

	private String departName;// 部门名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableCh() {
		return tableCh;
	}

	public void setTableCh(String tableCh) {
		this.tableCh = tableCh;
	}

	public String getTableEn() {
		return tableEn;
	}

	public void setTableEn(String tableEn) {
		this.tableEn = tableEn;
	}

	public String getSearchStatement() {
		return searchStatement;
	}

	public void setSearchStatement(String searchStatement) {
		this.searchStatement = searchStatement;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")  
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")  
	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getDepartCode() {
		return departCode;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSearchChinese() {
		return searchChinese;
	}

	public void setSearchChinese(String searchChinese) {
		this.searchChinese = searchChinese;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

}
