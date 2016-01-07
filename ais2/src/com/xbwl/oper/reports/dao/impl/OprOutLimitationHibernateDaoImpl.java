package com.xbwl.oper.reports.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprOutLimitation;
import com.xbwl.oper.reports.dao.IOprOutLimitationDao;

/**
 * author CaoZhili
 * time Nov 15, 2011 1:48:44 PM
 */
@Repository("oprOutLimitationHibernateDaoImpl")
public class OprOutLimitationHibernateDaoImpl extends BaseDAOHibernateImpl<OprOutLimitation, Long> implements IOprOutLimitationDao {

}
