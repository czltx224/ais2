package com.xbwl.rbac.vo;

import java.util.List;


public class MenuTree {
	private String id;
	
	private boolean leaf;
	
	private String text;
	
	private String href1;
	
	private int orderNum;
	
	private List<MenuTree> children;
	

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

	public List<MenuTree> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTree> children) {
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
	

}
