package com.xbwl.common.orm.hibernate.pojo;

import java.util.Date;

import javax.persistence.MappedSuperclass;

/**
 * �Զ����봴���ˣ�����ʱ�䣬�޸��ˣ��޸�ʱ���Entity����.
 * 
 * @author calvin
 */
@MappedSuperclass
public interface AuditableEntity {
	
	public void setCreateTime(Date createTime);
	public Date getCreateTime();
	
	public void setCreateName(String Name);
	public String getCreateName();
	
	public void setUpdateTime(Date updateTime);
	public Date getUpdateTime();
	
	public void setUpdateName(String name);
	public String getUpdateName();
	
	public void setTs(String ts);
	public String getTs();

}
