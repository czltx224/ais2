package com.xbwl.sys.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasRightDepart;
import com.xbwl.sys.dao.IBasRightDepartDao;

@Repository("basRightDepartDaoImpl")
public class BasRightDepartHibernateDaoImpl 
	extends BaseDAOHibernateImpl<BasRightDepart, Long>  implements IBasRightDepartDao{

}
