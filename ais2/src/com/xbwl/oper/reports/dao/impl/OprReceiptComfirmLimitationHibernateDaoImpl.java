package com.xbwl.oper.reports.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprReceiptComfirmLimitation;
import com.xbwl.oper.reports.dao.IOprReceiptComfirmLimitationDao;

/**
 * author CaoZhili time Nov 15, 2011 5:28:17 PM
 */
@Repository("oprReceiptComfirmLimitationHibernateDaoImpl")
public class OprReceiptComfirmLimitationHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprReceiptComfirmLimitation, Long> implements
		IOprReceiptComfirmLimitationDao {

}
