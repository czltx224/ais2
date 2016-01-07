package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiTransitcost;
import com.xbwl.finance.dao.IFiTransicostDao;

/**
 * author shuw
 * time Oct 7, 2011 11:43:26 AM
 */
@Repository("fiTransitcostHibernateDaoImpl")
public class FiTransitcostHibernateDaoImpl extends
		BaseDAOHibernateImpl<FiTransitcost, Long> implements
		IFiTransicostDao {

}
