package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiReceipt;
import com.xbwl.finance.dao.IFiReceiptDao;

/**
 * author shuw
 * time Dec 20, 2011 10:17:36 AM
 */
@Repository("fiReceiptDaoHibernateDaoImpl")
public class FiReceiptDaoHibernateDaoImpl extends BaseDAOHibernateImpl< FiReceipt,Long> 
														implements IFiReceiptDao{

}
