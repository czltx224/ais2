package com.xbwl.rbac.vo;

import java.util.List;
import java.util.Set;


public class DepartTree {
	private String id;
	
	private boolean leaf;
	
	private String text;
	
	private List<DepartTree> children;
	


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

	public List<DepartTree> getChildren() {
		return children;
	}

	public void setChildren(List<DepartTree> children) {
		this.children = children;
	}

	

}
