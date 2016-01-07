package com.xbwl.sys.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.SysCommonMenu;
import com.xbwl.sys.dao.ISysCommonMenuDao;

/**
 * author shuw
 * time Mar 1, 2012 3:21:05 PM
 *    系统常用菜单
 */
@Repository("sysCommonMenuHibernateDaoImpl")
public class SysCommonMenuHibernateDaoImpl extends BaseDAOHibernateImpl<SysCommonMenu, Long> implements ISysCommonMenuDao{


}
