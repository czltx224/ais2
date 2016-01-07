package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprNode;
import com.xbwl.oper.stock.dao.IOprNodeDao;

/**
 * author shuw
 * time Aug 9, 2011 8:39:49 AM
 */
@Repository("oprNodeHibernateDaoImpl")
public class OprNodeHibernateImpl extends BaseDAOHibernateImpl<OprNode, Long> 
								implements IOprNodeDao{

}
