package com.xbwl.sys.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.sys.dao.IOprExceptionTypeDao;
import com.xbwl.entity.OprExceptionType;

/**
 * author shuw
 * time Aug 17, 2011 9:22:02 AM
 */
@Repository("oprExceptionTypeHibernateDaoImpl")
public class OprExceptionTypeHibernateDaoImpl extends BaseDAOHibernateImpl<OprExceptionType, Long> implements IOprExceptionTypeDao{

}
