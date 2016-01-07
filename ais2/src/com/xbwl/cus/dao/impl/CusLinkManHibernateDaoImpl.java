package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusLinkManDao;
import com.xbwl.entity.CusLinkman;

/**
 *@author LiuHao
 *@time Oct 8, 2011 5:57:17 PM
 */
@Repository("cusLinkManHibernateDaoImpl")
public class CusLinkManHibernateDaoImpl extends BaseDAOHibernateImpl<CusLinkman,Long> implements
		ICusLinkManDao {
}
