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
import javax.persistence.UniqueConstraint;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * SysCommonMenu entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_COMMON_MENU", schema = "AISUSER", uniqueConstraints = @UniqueConstraint(columnNames = "USER_ID"))
public class SysCommonMenu implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Integer accessValue;  //
	private String name;  //菜单名
	private String href;       //路径
	private Integer nodeId;  //权限值ID
	private String createName;  
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private Long userId;
	private String userName;  //使用人
	private  Long sortNum;

	// Constructors

	/** default constructor */
	public SysCommonMenu() {
	}

	/** minimal constructor */
	public SysCommonMenu(Long id, Long userId) {
		this.id = id;
		this.userId = userId;
	}

	/** full constructor */
	public SysCommonMenu(Long id, Integer accessValue, String name, String href,
			String createName, Date createTime, String updateName,
			Date updateTime, String ts, Long userId, String userName) {
		this.id = id;
		this.accessValue = accessValue;
		this.name = name;
		this.href = href;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.userId = userId;
		this.userName = userName;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName="SEQ_SYS_COMMON_MENU")
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ACCESS_VALUE", length = 10)
	public Integer getAccessValue() {
		return this.accessValue;
	}

	public void setAccessValue(Integer accessValue) {
		this.accessValue = accessValue;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "HREF", length = 200)
	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
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

	@JSON(format="yyyy-MM-dd HH:mm:ss")
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

	@Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", length = 50)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "NODE_ID")
	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "SORT_NUM")
	public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
	}

}