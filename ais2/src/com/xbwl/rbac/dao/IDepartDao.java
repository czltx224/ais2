package com.xbwl.rbac.dao;

import java.util.HashMap;
import java.util.List;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.entity.SysDepart;

/**
 *author LiuHao
 *time Jun 9, 2011 2:06:48 PM
 */
public interface IDepartDao extends IBaseDAO<SysDepart,Long> {
	//�����ϼ����ű�Ų�ѯ������Ϣ
	public List<SysDepart> getDepartByParentId(Long parentId) throws Exception;
}
