package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusRateAnalyseDao;
import com.xbwl.entity.CusRateAnalyse;

/**
 *@author LiuHao
 *@time May 21, 2012 3:43:08 PM
 */
@Repository("cusRateAnalyseHibernateDaoImpl")
public class CusRateAnalyseHibernateDaoImpl extends BaseDAOHibernateImpl<CusRateAnalyse,Long>
		implements ICusRateAnalyseDao {

}
