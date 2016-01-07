package com.xbwl.oper.reports.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprSignLimitation;
import com.xbwl.oper.reports.dao.IOprSignLimitationDao;

/**
 * author CaoZhili time Nov 15, 2011 3:03:15 PM
 */
@Repository("oprSignLimitationHibernateDaoImpl")
public class OprSignLimitationHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprSignLimitation, Long> implements
		IOprSignLimitationDao {

}
