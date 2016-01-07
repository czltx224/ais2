package com.xbwl.oper.reports.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprEnterPortReport;
import com.xbwl.oper.reports.dao.IOprEnterPortReportDao;

/**
 * author CaoZhili
 * time Nov 9, 2011 4:33:22 PM
 */
@Repository("oprEnterPortReportHibernateDaoImpl")
public class OprEnterPortReportHibernateDaoImpl extends BaseDAOHibernateImpl<OprEnterPortReport, Long> implements IOprEnterPortReportDao{

}
