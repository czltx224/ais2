package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.oper.stock.dao.IOprOvermemoDetailDao;

/**
 * author CaoZhili time Jul 2, 2011 2:37:05 PM
 */
@Repository
public class OprOvermemoDetailHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprOvermemoDetail, Long> implements
		IOprOvermemoDetailDao {

}
