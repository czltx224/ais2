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
 * FlowRalarule entity.
 * 流程权限分析规则实体
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FLOW_RALARULE")
public class FlowRalarule implements java.io.Serializable,AuditableEntity {

	// Fields

	private Long id;
	private Long nodeId;//节点ID
	private Long wfoptType;//审批类型(审批\知会\依次逐个审批)
	private Long shareType;//共享类型(人员类型\岗位类型\特定矩阵下的岗位体系\角色类型)
	private Long userobjType;//人员类型(指定人员、流程操作者字段、表单人力资源字段)
	private Long stationobjType;//岗位类型(指定岗位、流程操作者字段、表单人力资源字段、表单岗位字段)
	private Long usershareType;//特定矩阵下的岗位体系(直接上级岗位\所有上级岗位)
	private Long roleobjType;//角色类型(指定角色)
	private Long userIds;//指定人员id
	private Long formfieldId;//表单字段id
	private Long roleobjId;//角色id 
	private Long wfoperatornodeId;//流程节点id 
	private Long stationId;//岗位ID
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String ts;
	
	private Long status;

	// Constructors

	/** default constructor */
	public FlowRalarule() {
	}

	/** minimal constructor */
	public FlowRalarule(Long id) {
		this.id = id;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName="SEQ_FLOW_RALARULE")
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

	@Column(name = "WFOPT_TYPE", precision = 10, scale = 0)
	public Long getWfoptType() {
		return this.wfoptType;
	}

	public void setWfoptType(Long wfoptType) {
		this.wfoptType = wfoptType;
	}

	@Column(name = "SHARE_TYPE", precision = 10, scale = 0)
	public Long getShareType() {
		return this.shareType;
	}

	public void setShareType(Long shareType) {
		this.shareType = shareType;
	}

	@Column(name = "USEROBJ_TYPE", precision = 10, scale = 0)
	public Long getUserobjType() {
		return this.userobjType;
	}

	public void setUserobjType(Long userobjType) {
		this.userobjType = userobjType;
	}

	@Column(name = "STATIONOBJ_TYPE", precision = 10, scale = 0)
	public Long getStationobjType() {
		return this.stationobjType;
	}

	public void setStationobjType(Long stationobjType) {
		this.stationobjType = stationobjType;
	}

	@Column(name = "USERSHARE_TYPE", precision = 10, scale = 0)
	public Long getUsershareType() {
		return this.usershareType;
	}

	public void setUsershareType(Long usershareType) {
		this.usershareType = usershareType;
	}

	@Column(name = "ROLEOBJ_TYPE", precision = 10, scale = 0)
	public Long getRoleobjType() {
		return this.roleobjType;
	}

	public void setRoleobjType(Long roleobjType) {
		this.roleobjType = roleobjType;
	}

	@Column(name = "USER_IDS", precision = 10, scale = 0)
	public Long getUserIds() {
		return this.userIds;
	}

	public void setUserIds(Long userIds) {
		this.userIds = userIds;
	}

	@Column(name = "FORMFIELD_ID", precision = 10, scale = 0)
	public Long getFormfieldId() {
		return this.formfieldId;
	}

	public void setFormfieldId(Long formfieldId) {
		this.formfieldId = formfieldId;
	}

	@Column(name = "ROLEOBJ_ID", precision = 10, scale = 0)
	public Long getRoleobjId() {
		return this.roleobjId;
	}

	public void setRoleobjId(Long roleobjId) {
		this.roleobjId = roleobjId;
	}

	@Column(name = "WFOPERATORNODE_ID", precision = 10, scale = 0)
	public Long getWfoperatornodeId() {
		return this.wfoperatornodeId;
	}

	public void setWfoperatornodeId(Long wfoperatornodeId) {
		this.wfoperatornodeId = wfoperatornodeId;
	}

	@Column(name = "STATION_ID", precision = 10, scale = 0)
	public Long getStationId() {
		return this.stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_NAME", length = 20)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format="yyyy-MM-dd")
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "TS", length = 15)
	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	/**
	 * @return the status
	 */
	@Column(name = "STATUS")
	public Long getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}
	
}