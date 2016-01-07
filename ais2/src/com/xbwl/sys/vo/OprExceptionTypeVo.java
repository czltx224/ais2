package com.xbwl.sys.vo;

import java.util.Date;

/**
 * author shuw
 * time Aug 24, 2011 2:29:36 PM
 */

public class OprExceptionTypeVo {
	private Long id;
	private Long nodeId;
	private String nodeName;
	private Long isOpen;
	private Long isSmsAgency;
	private Long isSmsCusd;
	private String typeName;
	private String parentType;
	private String dealFlow;
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String ts;
	private String remark;
	private String doDepartName;
	private Long doDepartId;
	private Long bussDepartId;
	
	private Long parentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Long getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Long isOpen) {
		this.isOpen = isOpen;
	}

	public Long getIsSmsAgency() {
		return isSmsAgency;
	}

	public void setIsSmsAgency(Long isSmsAgency) {
		this.isSmsAgency = isSmsAgency;
	}

	public Long getIsSmsCusd() {
		return isSmsCusd;
	}

	public void setIsSmsCusd(Long isSmsCusd) {
		this.isSmsCusd = isSmsCusd;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getDealFlow() {
		return dealFlow;
	}

	public void setDealFlow(String dealFlow) {
		this.dealFlow = dealFlow;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDoDepartName() {
		return doDepartName;
	}

	public void setDoDepartName(String doDepartName) {
		this.doDepartName = doDepartName;
	}

	public Long getDoDepartId() {
		return doDepartId;
	}

	public void setDoDepartId(Long doDepartId) {
		this.doDepartId = doDepartId;
	}

	public Long getBussDepartId() {
		return bussDepartId;
	}

	public void setBussDepartId(Long bussDepartId) {
		this.bussDepartId = bussDepartId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


}
