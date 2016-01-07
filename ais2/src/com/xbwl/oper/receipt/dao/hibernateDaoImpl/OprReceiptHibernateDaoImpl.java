package com.xbwl.oper.receipt.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprReceipt;
import com.xbwl.oper.receipt.dao.IOprReceiptDao;

/**
 * author CaoZhili time Jul 25, 2011 6:01:53 PM
 * 
 * �ص������ݷ��ʲ�ʵ����
 */
@Repository("oprReceiptHibernateDaoImpl")
public class OprReceiptHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprReceipt, Long> implements IOprReceiptDao {

}
