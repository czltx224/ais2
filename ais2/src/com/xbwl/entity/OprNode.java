package com.xbwl.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * OprNode entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPR_NODE", schema = "AISUSER")
public class OprNode implements java.io.Serializable {

	// Fields

	private Long id;
	private String nodeName;
	private String nodeColor;
	private String outName;
	private Long nodeOrder;
	private Long isOut;
	private String statusCol;
	private Long nodeType;

	// Constructors

	/** default constructor */
	public OprNode() {
	}

	/** minimal constructor */
	public OprNode(Long id, String nodeName) {
		this.id = id;
		this.nodeName = nodeName;
	}

	/** full constructor */
	public OprNode(Long id, String nodeName, String nodeColor, String outName,
			Long nodeOrder, Long isOut, String statusCol) {
		this.id = id;
		this.nodeName = nodeName;
		this.nodeColor = nodeColor;
		this.outName = outName;
		this.nodeOrder = nodeOrder;
		this.isOut = isOut;
		this.statusCol = statusCol;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_OPR_OPRNODE")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NODE_NAME", nullable = false, length = 50)
	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Column(name = "NODE_COLOR", length = 20)
	public String getNodeColor() {
		return this.nodeColor;
	}

	public void setNodeColor(String nodeColor) {
		this.nodeColor = nodeColor;
	}

	@Column(name = "OUT_NAME", length = 50)
	public String getOutName() {
		return this.outName;
	}

	public void setOutName(String outName) {
		this.outName = outName;
	}

	@Column(name = "NODE_ORDER", precision = 2, scale = 0)
	public Long getNodeOrder() {
		return this.nodeOrder;
	}

	public void setNodeOrder(Long nodeOrder) {
		this.nodeOrder = nodeOrder;
	}

	@Column(name = "IS_OUT", precision = 1, scale = 0)
	public Long getIsOut() {
		return this.isOut;
	}

	public void setIsOut(Long isOut) {
		this.isOut = isOut;
	}

	@Column(name = "STATUS_COL", length = 20)
	public String getStatusCol() {
		return this.statusCol;
	}

	public void setStatusCol(String statusCol) {
		this.statusCol = statusCol;
	}

	@Column(name = "NODE_TYPE", length = 1)
	public Long getNodeType() {
		return nodeType;
	}

	public void setNodeType(Long nodeType) {
		this.nodeType = nodeType;
	}
	
	

}