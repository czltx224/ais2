package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiReceivablestatement;
import com.xbwl.finance.dao.IFiReceivablestatementDao;

@Repository("fiReceivablestatementHibernateDaoImpl")
public class FiReceivablestatementHibernateDaoImpl extends
		BaseDAOHibernateImpl<FiReceivablestatement, Long> implements
		IFiReceivablestatementDao {

}
