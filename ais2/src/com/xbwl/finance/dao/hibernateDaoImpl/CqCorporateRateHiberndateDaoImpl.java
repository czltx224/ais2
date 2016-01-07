package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasCqCorporateRate;
import com.xbwl.finance.dao.ICqCorporateRateDao;

/**
 *@author LiuHao
 *@time 2011-7-21 ионГ11:20:31
 */
@Repository("cqCorporateRateHiberndateDaoImpl")
public class CqCorporateRateHiberndateDaoImpl extends
		BaseDAOHibernateImpl<BasCqCorporateRate, Long> implements ICqCorporateRateDao {
}
