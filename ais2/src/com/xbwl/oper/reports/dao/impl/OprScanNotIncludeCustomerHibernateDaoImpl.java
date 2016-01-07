package com.xbwl.oper.reports.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprScanNotIncludeCustomer;
import com.xbwl.oper.reports.dao.IOprScanNotIncludeCustomerDao;

/**
 * 签收和回单确收报表剔除代理数据访问层实现类
 * @author czl
 * @date 2012-04-20
 */
@Repository("oprScanNotIncludeCustomerHibernateDaoImpl")
public class OprScanNotIncludeCustomerHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprScanNotIncludeCustomer, Long> implements
		IOprScanNotIncludeCustomerDao {

}
