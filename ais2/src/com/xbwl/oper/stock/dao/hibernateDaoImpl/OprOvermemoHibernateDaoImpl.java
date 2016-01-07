package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprOvermemo;
import com.xbwl.oper.stock.dao.IOprOvermemoDao;

/**
 * author CaoZhili time Jul 2, 2011 2:34:59 PM
 */
@Repository
public class OprOvermemoHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprOvermemo, Long> implements IOprOvermemoDao {

}
