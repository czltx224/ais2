package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasCqStCorporateRate;
import com.xbwl.finance.dao.ICqStCorporateRateDao;

/**
 *@author LiuHao
 *@time 2011-7-21 ионГ11:35:32
 */
@Repository("cqStCorporateRateHibernateDaoImpl")
public class CqStCorporateRateHibernateDaoImpl extends
		BaseDAOHibernateImpl<BasCqStCorporateRate, Long> implements ICqStCorporateRateDao {
}
