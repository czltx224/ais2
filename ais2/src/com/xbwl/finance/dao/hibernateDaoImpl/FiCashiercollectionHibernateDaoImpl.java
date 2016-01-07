package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiCashiercollection;
import com.xbwl.finance.dao.IFiCashiercollectionDao;

@Repository("fiCashiercollectionHibernateDaoImpl")
public class FiCashiercollectionHibernateDaoImpl extends BaseDAOHibernateImpl<FiCashiercollection,Long>
		implements IFiCashiercollectionDao {

}
