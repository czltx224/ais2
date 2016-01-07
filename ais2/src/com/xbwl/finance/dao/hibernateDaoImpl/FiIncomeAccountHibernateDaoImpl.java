package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiIncomeAccount;
import com.xbwl.finance.dao.IFiIncomeAccountDao;

@Repository("fiIncomeAccountHibernateDaoImpl")
public class FiIncomeAccountHibernateDaoImpl extends BaseDAOHibernateImpl<FiIncomeAccount,Long>
		implements IFiIncomeAccountDao {

}
