package com.xbwl.oper.reports.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprArteryCarLimitation;
import com.xbwl.oper.reports.dao.IOprArteryCarLimitationDao;

/**
 * author CaoZhili time Nov 15, 2011 4:57:21 PM
 */
@Repository("oprArteryCarLimitationHibernateDaoImpl")
public class OprArteryCarLimitationHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprArteryCarLimitation, Long> implements
		IOprArteryCarLimitationDao {

}
