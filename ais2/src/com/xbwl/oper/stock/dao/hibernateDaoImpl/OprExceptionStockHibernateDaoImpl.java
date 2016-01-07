package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprExceptionStock;
import com.xbwl.oper.stock.dao.IOprExceptionStockDao;

/**
 * author CaoZhili time Nov 18, 2011 9:04:17 AM
 */
@Repository("oprExceptionStockHibernateDaoImpl")
public class OprExceptionStockHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprExceptionStock, Long> implements
		IOprExceptionStockDao {

}
