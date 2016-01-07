package com.xbwl.oper.receipt.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprReceipt;
import com.xbwl.oper.receipt.dao.IOprReceiptDao;

/**
 * author CaoZhili time Jul 25, 2011 6:01:53 PM
 * 
 * 回单表数据访问层实现类
 */
@Repository("oprReceiptHibernateDaoImpl")
public class OprReceiptHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprReceipt, Long> implements IOprReceiptDao {

}
