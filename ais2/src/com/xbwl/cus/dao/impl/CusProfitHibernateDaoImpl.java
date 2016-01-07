package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusProfitDao;
import com.xbwl.entity.CusProfit;

/**
 *@author LiuHao
 *@time Oct 20, 2011 2:01:54 PM
 */
@Repository("cusProfitHibernateDaoImpl")
public class CusProfitHibernateDaoImpl extends BaseDAOHibernateImpl<CusProfit,Long> implements
		ICusProfitDao {
}
