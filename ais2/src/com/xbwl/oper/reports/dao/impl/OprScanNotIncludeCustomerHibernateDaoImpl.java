package com.xbwl.oper.reports.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprScanNotIncludeCustomer;
import com.xbwl.oper.reports.dao.IOprScanNotIncludeCustomerDao;

/**
 * ǩ�պͻص�ȷ�ձ����޳��������ݷ��ʲ�ʵ����
 * @author czl
 * @date 2012-04-20
 */
@Repository("oprScanNotIncludeCustomerHibernateDaoImpl")
public class OprScanNotIncludeCustomerHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprScanNotIncludeCustomer, Long> implements
		IOprScanNotIncludeCustomerDao {

}
