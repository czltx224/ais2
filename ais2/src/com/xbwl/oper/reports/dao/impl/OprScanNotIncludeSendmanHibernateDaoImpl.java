package com.xbwl.oper.reports.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprScanNotIncludeSendman;
import com.xbwl.oper.reports.dao.IOprScanNotIncludeSendmanDao;

/**
 * 签收和回单确收报表剔除送货员数据访问层实现类
 * @author czl
 * @date 2012-04-20
 */
@Repository("oprScanNotIncludeSendmanHibernateDaoImpl")
public class OprScanNotIncludeSendmanHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprScanNotIncludeSendman, Long> implements
		IOprScanNotIncludeSendmanDao {

}
