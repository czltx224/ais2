package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.finance.dao.IFiCollectionstatementDao;
import com.xbwl.entity.FiCollectionstatement;

@Repository("fiCollectionstatementHibernateDaoImpl")
public class FiCollectionstatementHibernateDaoImpl extends BaseDAOHibernateImpl<FiCollectionstatement,Long>
		implements IFiCollectionstatementDao {

}
