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
 * FlowExport entity.
 *流程 出口实体
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FLOW_EXPORT")
public class FlowExport implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;//
	private Long startnodeId;//开始节点ID
	private Long endnodeId;//结束节点id 
	private String condition;//出口条件
	private String conditionRemark;//条件备注
	private String linkName;//出口名称 
	private Long pipeId;//流程id
	private Long linkFrom;//开始连接点
	private Long linkTo;//结束连接点
	private Long x1;//图形坐标
	private Long x2;
	private Long x3;
	private Long x4;
	private Long x5;
	private Long y1;
	private Long y2;
	private Long y3;
	private Long y4;
	private Long y5;
	private String updateName;
	private Date updateTime;
	private String createName;
	private Long status;
	private Date createTime;
	private String ts;

	// Constructors

	/** default constructor */
	public FlowExport() {
	}

	/** minimal constructor */
	public FlowExport(Long id) {
		this.id = id;
	}
	public Long getX(int n) {
	    switch (n)
	    {
	    case 1:
	      return this.getX1();
	    case 2:
	      return getX2();
	    case 3:
	      return getX3();
	    case 4:
	      return getX4();
	    case 5:
	      return getX5();
	    }
	    return -1L;
	  }
	public Long getY(int n)
	  {
	    switch (n)
	    {
	    case 1:
	      return getY1();
	    case 2:
	      return getY2();
	    case 3:
	      return getY3();
	    case 4:
	      return getY4();
	    case 5:
	      return getY5();
	    }
	    return -1L;
	  }
	public void setX(int n, Long x) {
	    switch (n)
	    {
	    case 1:
	      setX1(x);
	      break;
	    case 2:
	      setX2(x);
	      break;
	    case 3:
	      setX3(x);
	      break;
	    case 4:
	      setX4(x);
	      break;
	    case 5:
	      setX5(x);
	      break;
	    default:
	      return;
	    }
	  }

	  public void setY(int n, Long y) {
	    switch (n)
	    {
	    case 1:
	      setY1(y);
	      break;
	    case 2:
	      setY2(y);
	      break;
	    case 3:
	      setY3(y);
	      break;
	    case 4:
	      setY4(y);
	      break;
	    case 5:
	      setY5(y);
	      break;
	    default:
	      return;
	    }
	  }
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName="SEQ_FLOW_EXPORT")
	@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "STARTNODE_ID", precision = 10, scale = 0)
	public Long getStartnodeId() {
		return this.startnodeId;
	}

	public void setStartnodeId(Long startnodeId) {
		this.startnodeId = startnodeId;
	}

	@Column(name = "ENDNODE_ID", precision = 10, scale = 0)
	public Long getEndnodeId() {
		return this.endnodeId;
	}

	public void setEndnodeId(Long endnodeId) {
		this.endnodeId = endnodeId;
	}

	@Column(name = "CONDITION", length = 1000)
	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Column(name = "LINK_NAME", length = 50)
	public String getLinkName() {
		return this.linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	@Column(name = "PIPE_ID", precision = 10, scale = 0)
	public Long getPipeId() {
		return this.pipeId;
	}

	public void setPipeId(Long pipeId) {
		this.pipeId = pipeId;
	}

	@Column(name = "LINK_FROM", precision = 10, scale = 0)
	public Long getLinkFrom() {
		return this.linkFrom;
	}

	public void setLinkFrom(Long linkFrom) {
		this.linkFrom = linkFrom;
	}

	@Column(name = "LINK_TO", precision = 10, scale = 0)
	public Long getLinkTo() {
		return this.linkTo;
	}

	public void setLinkTo(Long linkTo) {
		this.linkTo = linkTo;
	}

	@Column(name = "X1", precision = 10, scale = 0)
	public Long getX1() {
		return this.x1;
	}

	public void setX1(Long x1) {
		this.x1 = x1;
	}

	@Column(name = "X2", precision = 10, scale = 0)
	public Long getX2() {
		return this.x2;
	}

	public void setX2(Long x2) {
		this.x2 = x2;
	}

	@Column(name = "X3", precision = 10, scale = 0)
	public Long getX3() {
		return this.x3;
	}

	public void setX3(Long x3) {
		this.x3 = x3;
	}

	@Column(name = "X4", precision = 10, scale = 0)
	public Long getX4() {
		return this.x4;
	}

	public void setX4(Long x4) {
		this.x4 = x4;
	}

	@Column(name = "X5", precision = 10, scale = 0)
	public Long getX5() {
		return this.x5;
	}

	public void setX5(Long x5) {
		this.x5 = x5;
	}

	@Column(name = "Y1", precision = 10, scale = 0)
	public Long getY1() {
		return this.y1;
	}

	public void setY1(Long y1) {
		this.y1 = y1;
	}

	@Column(name = "Y2", precision = 10, scale = 0)
	public Long getY2() {
		return this.y2;
	}

	public void setY2(Long y2) {
		this.y2 = y2;
	}

	@Column(name = "Y3", precision = 10, scale = 0)
	public Long getY3() {
		return this.y3;
	}

	public void setY3(Long y3) {
		this.y3 = y3;
	}

	@Column(name = "Y4", precision = 10, scale = 0)
	public Long getY4() {
		return this.y4;
	}

	public void setY4(Long y4) {
		this.y4 = y4;
	}

	@Column(name = "Y5", precision = 10, scale = 0)
	public Long getY5() {
		return this.y5;
	}

	public void setY5(Long y5) {
		this.y5 = y5;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "STATUS", precision = 1, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	/**
	 * @return the conditionRemark
	 */
	@Column(name = "CONDITION_REMARK", length = 15)
	public String getConditionRemark() {
		return conditionRemark;
	}

	/**
	 * @param conditionRemark the conditionRemark to set
	 */
	public void setConditionRemark(String conditionRemark) {
		this.conditionRemark = conditionRemark;
	}
	
}