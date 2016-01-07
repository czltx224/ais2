package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusOverWeightDao;
import com.xbwl.entity.OprOverweight;

/**
 *@author LiuHao
 *@time Oct 25, 2011 10:55:12 AM
 */
@Repository("cusOverWeightHibernateDaoImpl")
public class CusOverWeightHibernateDaoImpl extends BaseDAOHibernateImpl<OprOverweight,Long>
		implements ICusOverWeightDao {
}
