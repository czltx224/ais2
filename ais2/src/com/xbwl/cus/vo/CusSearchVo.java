package com.xbwl.cus.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * author CaoZhili time Oct 24, 2011 2:02:19 PM
 */

public class CusSearchVo implements Serializable {

	private Long id;
	private String tableCh;// ��������
	private String tableEn;// ��Ӣ����
	private String searchStatement;// ��ѯ����SQL
	private String createName;// ������
	private Date createTime;// ����ʱ��
	private String updateName;// �޸���
	private Date updateTime;// �޸�ʱ��
	private String ts;// ʱ���
	private String departCode;// ���ű���
	private String title;// ��ѯ����
	private String searchChinese;// ��ѯ�����������

	private String departName;// ��������

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
