package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasProjectRate;
import com.xbwl.finance.dao.IBasProjectRateDao;

/**
 * @author CaoZhili time Aug 10, 2011 10:41:45 AM
 * 
 * 项目客户协议价数据访问层实现类
 */
@Repository("basProjectRateHibernateDaoImpl")
public class BasProjectRateHibernateDaoImpl extends
		BaseDAOHibernateImpl<BasProjectRate, Long> implements
		IBasProjectRateDao {

}
