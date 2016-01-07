package com.xbwl.rbac.entity;
/**
 * @author Administrator
 * @createTime 6:04:04 PM
 * @updateName Administrator
 * @updateTime 6:04:04 PM
 * 
 */

public class SysRole {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
