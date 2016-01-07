package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiReconciliationAccount;
import com.xbwl.finance.dao.IFiReconciliationAccountDao;

/**
 * author shuw
 * time Dec 7, 2011 2:27:08 PM
 */
@Repository("fiReconciliationAccountHibernateDaoImpl")
public class FiReconciliationAccountHibernateDaoImpl extends
					BaseDAOHibernateImpl<FiReconciliationAccount, Long> implements
					IFiReconciliationAccountDao {
}
