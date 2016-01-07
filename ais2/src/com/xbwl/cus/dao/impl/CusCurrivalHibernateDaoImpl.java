package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusCurrivalDao;
import com.xbwl.entity.CusCurrival;

/**
 *@author LiuHao
 *@time Oct 28, 2011 9:50:02 AM
 */
@Repository("cusCurrivalHibernateDaoImpl")
public class CusCurrivalHibernateDaoImpl extends BaseDAOHibernateImpl<CusCurrival,Long> implements
		ICusCurrivalDao {
}
