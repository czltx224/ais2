package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiDeliverycost;
import com.xbwl.finance.dao.IFiDeliverycostDao;

/**
 * author shuw
 * time Oct 8, 2011 5:44:34 PM
 */
@Repository("fiDeliverycostHibernateDaoImpl")
public class FiDeliverycostHibernateDaoImpl  extends BaseDAOHibernateImpl<FiDeliverycost,Long>
													implements IFiDeliverycostDao {

}
