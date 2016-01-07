package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprHistory;
import com.xbwl.oper.stock.dao.IOprHistoryDao;

/**
 * author CaoZhili time Jul 21, 2011 9:56:35 AM
 */
@Repository("oprHistoryHibernateDaoImpl")
public class OprHistoryHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprHistory, Long> implements IOprHistoryDao {

}
