package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiArrearset;
import com.xbwl.finance.dao.IFiArrearsetDao;

@Repository("fiArrearsetHibernateDaoImpl")
public class FiArrearsetHibernateDaoImpl extends
		BaseDAOHibernateImpl<FiArrearset, Long> implements IFiArrearsetDao {

}
