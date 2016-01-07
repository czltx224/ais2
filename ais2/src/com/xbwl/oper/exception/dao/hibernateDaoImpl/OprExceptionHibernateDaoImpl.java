package com.xbwl.oper.exception.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprException;
import com.xbwl.oper.exception.dao.IOprExceptionDao;

/**
 * author shuw
 * time Aug 22, 2011 4:50:06 PM
 */
@Repository("OprExceptionHibernateDaoImpl")
public class OprExceptionHibernateDaoImpl extends BaseDAOHibernateImpl<OprException, Long>
							implements IOprExceptionDao{

}
