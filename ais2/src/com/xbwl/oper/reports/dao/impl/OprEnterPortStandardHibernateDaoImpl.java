package com.xbwl.oper.reports.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprEnterPortStandard;
import com.xbwl.oper.reports.dao.IOprEnterPortStandardDao;

/**
 * author CaoZhili time Nov 9, 2011 10:59:50 AM
 */
@Repository("oprEnterPortStandardHibernateDaoImpl")
public class OprEnterPortStandardHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprEnterPortStandard, Long> implements
		IOprEnterPortStandardDao {

}
