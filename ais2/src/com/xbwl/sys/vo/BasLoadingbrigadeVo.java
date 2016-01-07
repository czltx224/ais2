package com.xbwl.sys.vo;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

public class BasLoadingbrigadeVo implements java.io.Serializable {
    // Fields
	private Long id;
	private String loadingName;
	private Long departId;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private String departName;
	private Long manCount;
	private Long type;
	
	public Long getType() {
		return type;
	}




	public void setType(Long type) {
		this.type = type;
	}




	public BasLoadingbrigadeVo() {
		super();
	}




	public BasLoadingbrigadeVo(Long id, String loadingName, Long departId,
			String createName, Date createTime, String updateName,
			Date updateTime, String ts, String departName, Long manCount) {
		super();
		this.id = id;
		this.loadingName = loadingName;
		this.departId = departId;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.departName = departName;
		this.manCount = manCount;
	}




	public Long getManCount() {
		return manCount;
	}




	public void setManCount(Long manCount) {
		this.manCount = manCount;
	}




	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getLoadingName() {
		return loadingName;
	}


	public void setLoadingName(String loadingName) {
		this.loadingName = loadingName;
	}


	public Long getDepartId() {
		return departId;
	}


	public void setDepartId(Long departId) {
		this.departId = departId;
	}


	public String getCreateName() {
		return createName;
	}


	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd")
	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getUpdateName() {
		return updateName;
	}


	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd")
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


	public String getDepartName() {
		return departName;
	}


	public void setDepartName(String departName) {
		this.departName = departName;
	}
 
	

   // Constructors
}
