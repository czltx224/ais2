package com.xbwl.sys.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasSpecialArea;
import com.xbwl.sys.dao.IBasSpecialAreaDao;

/**
 * author shuw
 * time Apr 12, 2012 3:44:44 PM
 */
@Repository("basSpecialAreaHibernateDaoImpl")
public class BasSpecialAreaHibernateDaoImpl extends BaseDAOHibernateImpl<BasSpecialArea, Long>  implements IBasSpecialAreaDao {

}
