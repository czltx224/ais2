package com.xbwl.oper.reports.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprEnterPortKpi;
import com.xbwl.oper.reports.dao.IOprEnterPortKpiDao;

/**
 * author CaoZhili
 * time Nov 10, 2011 1:46:20 PM
 */
@Repository("oprEnterPortKpiHibernateDaoImpl")
public class OprEnterPortKpiHibernateDaoImpl extends BaseDAOHibernateImpl<OprEnterPortKpi, Long> implements IOprEnterPortKpiDao{

}
