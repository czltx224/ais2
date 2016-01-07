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
 * BasTreatyChangeList entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BAS_TREATY_CHANGE_LIST")
public class BasTreatyChangeList implements java.io.Serializable {

	// Fields

	private Long id;
	private String tableName;
	private String chinaName;
	private Long doNo;
	private String updateBefore;
	private String updateFater;
	private String updateContent;
	private String updateName;
	private Date updateTime;
	private Long departId;
	private String departName;

	// Constructors

	/** default constructor */
	public BasTreatyChangeList() {
	}

	/** full constructor */
	public BasTreatyChangeList(Long id, String tableName, Long doNo,
			String updateBefore, String updateFater, String updateContent,
			String updateName, Date updateTime, Long departId, String departName) {
		this.id = id;
		this.tableName = tableName;
		this.doNo = doNo;
		this.updateBefore = updateBefore;
		this.updateFater = updateFater;
		this.updateContent = updateContent;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.departId = departId;
		this.departName = departName;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "generator", sequenceName="SEQ_BAS_TREATY_CHANGE_LIST")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id =id;
	}

	@Column(name = "TABLE_NAME", nullable = false, length = 50)
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	@Column(name = "CHINA_NAME", nullable = false, length = 50)
	public String getChinaName() {
		return chinaName;
	}

	public void setChinaName(String chinaName) {
		this.chinaName = chinaName;
	}

	@Column(name = "DO_NO", nullable = false, precision = 10, scale = 0)
	public Long getDoNo() {
		return this.doNo;
	}

	public void setDoNo(Long doNo) {
		this.doNo = doNo;
	}

	@Column(name = "UPDATE_BEFORE", nullable = false, length = 500)
	public String getUpdateBefore() {
		return this.updateBefore;
	}

	public void setUpdateBefore(String updateBefore) {
		this.updateBefore = updateBefore;
	}

	@Column(name = "UPDATE_FATER", nullable = false, length = 500)
	public String getUpdateFater() {
		return this.updateFater;
	}

	public void setUpdateFater(String updateFater) {
		this.updateFater = updateFater;
	}

	@Column(name = "UPDATE_CONTENT", nullable = false, length = 500)
	public String getUpdateContent() {
		return this.updateContent;
	}

	public void setUpdateContent(String updateContent) {
		this.updateContent = updateContent;
	}

	@Column(name = "UPDATE_NAME", nullable = false, length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", nullable = false, length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "DEPART_ID", nullable = false, precision = 10, scale = 0)
	public Long getDepartId() {
		return this.departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	@Column(name = "DEPART_NAME", nullable = false, length = 20)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

}