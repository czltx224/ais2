package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusDevelopDao;
import com.xbwl.entity.CusDevelop;

/**
 *@author LiuHao
 *@time Oct 13, 2011 3:36:15 PM
 */
@Repository("cusDevelopHibernateDaoImpl")
public class CusDevelopHibernateDaoImpl extends BaseDAOHibernateImpl<CusDevelop,Long> implements
		ICusDevelopDao {
}
