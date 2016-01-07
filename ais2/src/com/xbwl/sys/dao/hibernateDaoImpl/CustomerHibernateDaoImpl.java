package com.xbwl.sys.dao.hibernateDaoImpl;


import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.Customer;
import com.xbwl.sys.dao.ICustomerDao;

@Repository("customerHibernateDaoImpl")
public class CustomerHibernateDaoImpl extends
		BaseDAOHibernateImpl<Customer, Long> implements ICustomerDao {

}
