package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusSearchDao;
import com.xbwl.entity.CusSearch;

/**
 * author CaoZhili time Oct 17, 2011 10:07:11 AM
 */
@Repository("cusSearchHibernateDaoImpl")
public class CusSearchHibernateDaoImpl extends
		BaseDAOHibernateImpl<CusSearch, Long> implements ICusSearchDao {

}
