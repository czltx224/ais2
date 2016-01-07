package com.xbwl.oper.reports.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprScanNotIncludeSendman;
import com.xbwl.oper.reports.dao.IOprScanNotIncludeSendmanDao;

/**
 * ǩ�պͻص�ȷ�ձ����޳��ͻ�Ա���ݷ��ʲ�ʵ����
 * @author czl
 * @date 2012-04-20
 */
@Repository("oprScanNotIncludeSendmanHibernateDaoImpl")
public class OprScanNotIncludeSendmanHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprScanNotIncludeSendman, Long> implements
		IOprScanNotIncludeSendmanDao {

}
