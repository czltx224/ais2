package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.IProAnalyseDao;
import com.xbwl.entity.ProductAnalyse;

/**
 *@author LiuHao
 *@time Dec 3, 2011 3:10:39 PM
 */
@Repository("proAnalyseHibernateDaoImpl")
public class ProAnalyseHibernateDaoImpl extends BaseDAOHibernateImpl<ProductAnalyse,Long> implements
		IProAnalyseDao {
}
