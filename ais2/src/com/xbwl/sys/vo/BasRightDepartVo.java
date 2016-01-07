package com.xbwl.sys.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * BasRightDepart entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class BasRightDepartVo implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;// 用户ID
	private Long rightDepartid;// 权限部门ID(来自部门表)
	private String createName;// 创建人
	private Date createTime;// 创建时间
	private String updateName;// 修改人
	private Date updateTime;// 修改时间
	private String ts;// 时间戳

	private String departName;// 部门名称
	private String userName;// 用户名称

	// Constructors

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	/** default constructor */
	public BasRightDepartVo() {
	}

	/** minimal constructor */
	public BasRightDepartVo(Long id) {
		this.id = id;
	}

	/** full constructor */

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRightDepartid() {
		return this.rightDepartid;
	}

	public void setRightDepartid(Long rightDepartid) {
		this.rightDepartid = rightDepartid;
	}

	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd hh:mm:ss")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format = "yyyy-MM-dd hh:mm:ss")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
