package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiAppreciationService;
import com.xbwl.finance.dao.IFiAppreciationServiceDao;

/**
 * author shuw
 * time Oct 26, 2011 1:44:06 PM
 */
@Repository("fiAppreciationServicesHibernateDaoImpl")
public class FiAppreciationServicesHibernateDaoImpl extends 
							BaseDAOHibernateImpl<FiAppreciationService, Long> implements IFiAppreciationServiceDao{

}
