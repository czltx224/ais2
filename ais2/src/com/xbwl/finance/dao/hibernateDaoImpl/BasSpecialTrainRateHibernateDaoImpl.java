package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasSpecialTrainRate;
import com.xbwl.finance.dao.IBasSpecialTrainRateDao;

/**
 * @author CaoZhili time Aug 2, 2011 4:00:18 PM
 * 
 * 专车协议价数据访问层实现类
 */
@Repository("basSpecialTrainRateHibernateDaoImpl")
public class BasSpecialTrainRateHibernateDaoImpl extends
		BaseDAOHibernateImpl<BasSpecialTrainRate, Long> implements
		IBasSpecialTrainRateDao {

}
