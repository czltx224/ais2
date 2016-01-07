package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprSignRoute;
import com.xbwl.oper.stock.dao.IOprSignRouteDao;

/**
 * author shuw
 * time Aug 2, 2011 1:46:32 PM
 */
@Repository("oprSignRouteHibernateDaoImpl")
public class OprSignRouteHibernateDaoImpl extends BaseDAOHibernateImpl<OprSignRoute, Long>
								implements IOprSignRouteDao{

}
