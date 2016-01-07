package com.xbwl.oper.fax.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.oper.fax.dao.IOprFaxInDao;

/**
 * author CaoZhili time Jul 6, 2011 2:34:09 PM
 */
@Repository("oprFaxInHibernateDaoImpl")
public class OprFaxInHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprFaxIn, Long> implements IOprFaxInDao {
}
                        













