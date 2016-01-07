package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasSpecialTrainRate;
import com.xbwl.finance.dao.ISpecialTrainRateDao;

/**
 *@author LiuHao
 *@time 2011-7-22 ионГ10:23:58
 */
@Repository("specialTrainRateHiberndateDaoImpl")
public class SpecialTrainRateHiberndateDaoImpl extends
		BaseDAOHibernateImpl<BasSpecialTrainRate, Long> implements ISpecialTrainRateDao {
}
