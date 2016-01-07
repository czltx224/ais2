package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.finance.dao.IFiCapitaaccountsetDao;

@Repository("fiCapitaaccountsetHibernateDaoImpl")
public class FiCapitaaccountsetHibernateDaoImpl extends BaseDAOHibernateImpl<FiCapitaaccountset,Long>
		implements IFiCapitaaccountsetDao {
}
