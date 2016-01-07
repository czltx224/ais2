package com.xbwl.oper.fax.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprFaxMain;
import com.xbwl.oper.fax.dao.IOprFaxMainDao;

/**
 * @author CaoZhili time Aug 29, 2011 10:29:14 AM
 */
@Repository("oprFaxMainHibernateDaoImpl")
public class OprFaxMainHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprFaxMain, Long> implements IOprFaxMainDao {
}
