package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiHeadquarterAccount;
import com.xbwl.finance.dao.IFiHeadquarterAccountDao;

@Repository("fiHeadquarterAccountHibernateDaoImpl")
public class FiHeadquarterAccountHibernateDaoImpl extends BaseDAOHibernateImpl<FiHeadquarterAccount,Long> implements
IFiHeadquarterAccountDao {
	
}
