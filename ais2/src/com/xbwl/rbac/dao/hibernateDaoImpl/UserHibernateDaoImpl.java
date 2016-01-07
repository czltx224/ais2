package com.xbwl.rbac.dao.hibernateDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.rbac.dao.IUserDao;
import com.xbwl.rbac.entity.SysUser;

@Repository
public class UserHibernateDaoImpl extends BaseDAOHibernateImpl<SysUser,Long> implements
		IUserDao {

	public List<SysUser> getUsersByStationId(Long stationId) {
		return this.findBy("stationId", stationId);
	}
}
