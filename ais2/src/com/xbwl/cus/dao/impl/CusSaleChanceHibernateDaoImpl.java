package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusSaleChanceDao;
import com.xbwl.entity.CusSaleChance;

/**
 *@author LiuHao
 *@time Oct 22, 2011 9:30:12 AM
 */
@Repository("cusSaleChanceHibernateDaoImpl")
public class CusSaleChanceHibernateDaoImpl extends BaseDAOHibernateImpl<CusSaleChance,Long>
		implements ICusSaleChanceDao {
}
