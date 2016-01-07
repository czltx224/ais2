package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiPaid;
import com.xbwl.finance.dao.IFiPaidDao;

@Repository("fiPaidHibernateDaoImpl")
public class FiPaidHibernateDaoImpl extends BaseDAOHibernateImpl<FiPaid, Long>
		implements IFiPaidDao {

}
