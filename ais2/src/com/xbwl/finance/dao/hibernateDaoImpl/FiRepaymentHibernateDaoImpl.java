package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiRepayment;
import com.xbwl.finance.dao.IFiRepaymentDao;

@Repository("fiRepaymentHibernateDaoImpl")
public class FiRepaymentHibernateDaoImpl extends BaseDAOHibernateImpl<FiRepayment,Long> implements
		IFiRepaymentDao {

}
