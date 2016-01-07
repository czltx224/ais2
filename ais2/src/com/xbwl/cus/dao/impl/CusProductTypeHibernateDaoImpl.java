package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusProductTypeDao;
import com.xbwl.entity.CusProducttype;

/**
 *@author LiuHao
 *@time Oct 19, 2011 5:13:44 PM
 */
@Repository("cusProductTypeHibernateDaoImpl")
public class CusProductTypeHibernateDaoImpl extends BaseDAOHibernateImpl<CusProducttype,Long>
		implements ICusProductTypeDao {

}
