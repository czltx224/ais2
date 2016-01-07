package com.xbwl.sys.vo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * BasRightDepart entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class BasRightDepartVo implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;// �û�ID
	private Long rightDepartid;// Ȩ�޲���ID(���Բ��ű�)
	private String createName;// ������
	private Date createTime;// ����ʱ��
	private String updateName;// �޸���
	private Date updateTime;// �޸�ʱ��
	private String ts;// ʱ���

	private String departName;// ��������
	private String userName;// �û�����

	// Constructors

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	/** default constructor */
	public BasRightDepartVo() {
	}

	/** minimal constructor */
	public BasRightDepartVo(Long id) {
		this.id = id;
	}

	/** full constructor */

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRightDepartid() {
		return this.rightDepartid;
	}

	public void setRightDepartid(Long rightDepartid) {
		this.rightDepartid = rightDepartid;
	}

	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@JSON(format = "yyyy-MM-dd hh:mm:ss")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@JSON(format = "yyyy-MM-dd hh:mm:ss")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
