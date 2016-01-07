package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusOverManagerDao;
import com.xbwl.entity.CusOverweightManager;

/**
 *@author LiuHao
 *@time Oct 25, 2011 10:42:39 AM
 */
@Repository("cusOverManagerHibernateDaoImpl")
public class CusOverManagerHibernateDaoImpl extends BaseDAOHibernateImpl<CusOverweightManager,Long>
		implements ICusOverManagerDao {
}
