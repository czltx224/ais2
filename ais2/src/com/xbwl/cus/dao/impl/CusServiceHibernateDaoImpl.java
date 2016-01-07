package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusServiceDao;
import com.xbwl.entity.CusService;

/**
 * author CaoZhili time Oct 10, 2011 4:33:13 PM
 */
@Repository("cusServiceHibernateDaoImpl")
public class CusServiceHibernateDaoImpl extends
		BaseDAOHibernateImpl<CusService, Long> implements ICusServiceDao {

}
