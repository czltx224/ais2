package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasProjCusTransitRate;
import com.xbwl.finance.dao.IBasProjCusTransitRateDao;

/**
 * author CaoZhili time Nov 29, 2011 9:32:38 AM
 */
@Repository("basProjCusTransitRateHibernateDaoImpl")
public class BasProjCusTransitRateHibernateDaoImpl extends
		BaseDAOHibernateImpl<BasProjCusTransitRate, Long> implements
		IBasProjCusTransitRateDao {

}
