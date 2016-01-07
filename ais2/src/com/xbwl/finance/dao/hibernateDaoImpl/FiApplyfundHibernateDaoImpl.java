package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiApplyfund;
import com.xbwl.finance.dao.IFiApplyfundDao;

@Repository("fiApplyfundHibernateDaoImpl")
public class FiApplyfundHibernateDaoImpl extends BaseDAOHibernateImpl<FiApplyfund,Long> implements IFiApplyfundDao {

}
