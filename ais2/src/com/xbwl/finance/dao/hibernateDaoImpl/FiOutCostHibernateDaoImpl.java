package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiOutcost;
import com.xbwl.finance.dao.IFiOutCostDao;

/**
 *@author LiuHao
 *@time Aug 29, 2011 5:47:56 PM
 */
@Repository("fiOutCostHibernateDaoImpl")
public class FiOutCostHibernateDaoImpl extends BaseDAOHibernateImpl<FiOutcost,Long> implements
		IFiOutCostDao {

}
