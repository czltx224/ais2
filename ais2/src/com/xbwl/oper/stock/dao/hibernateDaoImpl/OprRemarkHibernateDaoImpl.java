package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprRemark;
import com.xbwl.oper.stock.dao.IOprRemarkDao;

/**
 * author CaoZhili time Jul 19, 2011 4:10:52 PM
 */
@Repository("oprRemarkHibernateDaoImpl")
public class OprRemarkHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprRemark, Long> implements IOprRemarkDao {

}
