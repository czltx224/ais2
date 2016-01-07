package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusAnalyseDao;
import com.xbwl.entity.CusAnalyse;

/**
 *@author LiuHao
 *@time Dec 17, 2011 11:09:55 AM
 */
@Repository("cusAnalyseHibernateDaoImpl")
public class CusAnalyseHibernateDaoImpl extends BaseDAOHibernateImpl<CusAnalyse,Long> implements
		ICusAnalyseDao {
}
