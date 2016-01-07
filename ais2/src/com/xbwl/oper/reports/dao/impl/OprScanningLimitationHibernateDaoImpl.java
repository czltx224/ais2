package com.xbwl.oper.reports.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprScanningLimitation;
import com.xbwl.oper.reports.dao.IOprScanningLimitationDao;

/**
 * author CaoZhili time Nov 15, 2011 4:35:12 PM
 */
@Repository("oprScanningLimitationHibernateDaoImpl")
public class OprScanningLimitationHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprScanningLimitation, Long> implements
		IOprScanningLimitationDao {

}
