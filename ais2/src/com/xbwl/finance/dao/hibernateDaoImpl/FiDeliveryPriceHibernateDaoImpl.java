package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiDeliveryPrice;
import com.xbwl.finance.dao.IFiDeliveryPriceDao;

/**
 * author shuw
 * time Oct 10, 2011 3:53:08 PM
 */
@Repository("fiDeliveryPriceHibernateDaoImpl")
public class FiDeliveryPriceHibernateDaoImpl extends BaseDAOHibernateImpl<FiDeliveryPrice,Long>
					implements IFiDeliveryPriceDao  {
 

}
