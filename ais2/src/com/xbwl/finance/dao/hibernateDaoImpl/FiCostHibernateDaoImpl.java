package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiCost;
import com.xbwl.finance.dao.IFiCostDao;

/**
 * author shuw
 * time Sep 21, 2011 3:48:11 PM
 */
@Repository("fiCostHibernateDaoImpl")
public class FiCostHibernateDaoImpl  extends BaseDAOHibernateImpl<FiCost,Long>
																													implements IFiCostDao {
 

}
