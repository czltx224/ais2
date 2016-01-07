package com.xbwl.rbac.dao;

import java.util.List;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.rbac.entity.SysUser;

public interface IUserDao extends IBaseDAO<SysUser,Long> {
	/**
	 * ���ݸ�λID��ѯ�û���Ϣ
	 * @author LiuHao
	 * @time Apr 7, 2012 11:50:33 AM 
	 * @param stationId
	 * @return
	 */
	public List<SysUser> getUsersByStationId(Long stationId);
}
