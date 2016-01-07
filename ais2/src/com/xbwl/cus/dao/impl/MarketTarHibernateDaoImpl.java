package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.IMarketingTargetDao;
import com.xbwl.entity.MarketingTarget;

/**
 *@author LiuHao
 *@time Dec 20, 2011 6:00:46 PM
 */
@Repository("marketTarHibernateDaoImpl")
public class MarketTarHibernateDaoImpl extends BaseDAOHibernateImpl<MarketingTarget,Long> implements
		IMarketingTargetDao {
}
