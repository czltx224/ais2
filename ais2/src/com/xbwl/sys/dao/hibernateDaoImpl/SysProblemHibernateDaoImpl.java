package com.xbwl.sys.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.SysProblem;
import com.xbwl.sys.dao.ISysProBlemDao;

/**
 * author shuw time Feb 11, 2012 2:31:37 PM
 */
@Repository("sysProblemHibernateDaoImpl")
public class SysProblemHibernateDaoImpl extends
		BaseDAOHibernateImpl<SysProblem, Long> implements ISysProBlemDao {

}
