package com.xbwl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;

/**
 * OprExceptionType entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_EXCEPTION_TYPE", schema = "AISUSER")
public class OprExceptionType  implements java.io.Serializable,AuditableEntity  {

	// Fields

	private Long id;
	private Long nodeId;
	private String nodeName;
	private Long isOpen;
	private Long isSmsAgency;
	private Long isSmsCusd;
	private String typeName;
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
	private Long isDoPiece;
	
	/** default constructor */
	public OprExceptionType() {
	}

	/** minimal constructor */
	public OprExceptionType(Long id) {
		this.id = id;
	}

	/** full constructor */
	public OprExceptionType(Long id, Long nodeId, String nodeName, Long isOpen,
			Long isSmsAgency, Long isSmsCusd, String typeName, 
			String parentType, String dealFlow, String createName,
			Date createTime, String updateName, Date updateTime, String ts,
			String remark) {
		this.id = id;
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.isOpen = isOpen;
		this.isSmsAgency = isSmsAgency;
		this.isSmsCusd = isSmsCusd;
		this.typeName = typeName;
		this.dealFlow = dealFlow;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.ts = ts;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_EXCEPTION_TYPE",allocationSize=1)
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NODE_ID", precision = 10, scale = 0)
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "NODE_NAME", length = 100)
	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Column(name = "IS_OPEN", precision = 1, scale = 0)
	public Long getIsOpen() {
		return this.isOpen;
	}

	public void setIsOpen(Long isOpen) {
		this.isOpen = isOpen;
	}

	@Column(name = "IS_SMS_AGENCY", precision = 1, scale = 0)
	public Long getIsSmsAgency() {
		return this.isSmsAgency;
	}

	public void setIsSmsAgency(Long isSmsAgency) {
		this.isSmsAgency = isSmsAgency;
	}

	@Column(name = "IS_SMS_CUSD", precision = 1, scale = 0)
	public Long getIsSmsCusd() {
		return this.isSmsCusd;
	}

	public void setIsSmsCusd(Long isSmsCusd) {
		this.isSmsCusd = isSmsCusd;
	}

	@Column(name = "TYPE_NAME", length = 50)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "DEAL_FLOW", length = 200)
	public String getDealFlow() {
		return this.dealFlow;
	}

	public void setDealFlow(String dealFlow) {
		this.dealFlow = dealFlow;
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

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "DO_DEPART_NAME", length = 50)
	public String getDoDepartName() {
		return doDepartName;
	}

	public void setDoDepartName(String doDepartName) {
		this.doDepartName = doDepartName;
	}

	@Column(name = "DO_DEPART_ID", precision = 10, scale = 0)
	public Long getDoDepartId() {
		return doDepartId;
	}

	public void setDoDepartId(Long doDepartId) {
		this.doDepartId = doDepartId;
	}

	@Column(name = "BUSS_DEPART_ID", precision = 10, scale = 0)
	public Long getBussDepartId() {
		return bussDepartId;
	}

	public void setBussDepartId(Long bussDepartId) {
		this.bussDepartId = bussDepartId;
	}

	@Column(name = "IS_DO_PIECE", precision = 1, scale = 0)
	public Long getIsDoPiece() {  
		return isDoPiece;
	}

	public void setIsDoPiece(Long isDoPiece) {
		this.isDoPiece = isDoPiece;
	}


}