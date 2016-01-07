package com.xbwl.rbac.vo;

import java.util.List;


public class CommonMenuTree {
	private String id;
	
	private boolean leaf;
	
	private String text;
	
	private String href1;
	
	private boolean checked;
	
	private int orderNum;
	
	private List<CommonMenuTree> children;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<CommonMenuTree> getChildren() {
		return children;
	}

	public void setChildren(List<CommonMenuTree> children) {
		this.children = children;
	}

	public String getHref1() {
		return href1;
	}

	public void setHref1(String href) {
		this.href1 = href;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = leaf;
	}
	

}
