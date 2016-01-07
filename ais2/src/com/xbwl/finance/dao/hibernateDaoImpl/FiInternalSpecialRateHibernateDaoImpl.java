package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiInternalSpecialRate;
import com.xbwl.finance.dao.IFiInternalSpecialRateDao;

/**
 * author CaoZhili time Oct 20, 2011 10:37:56 AM
 * 内部特殊客户协议价设置数据访问层实现类
 */
@Repository("fiInternalSpecialRateHibernateDaoImpl")
public class FiInternalSpecialRateHibernateDaoImpl extends
		BaseDAOHibernateImpl<FiInternalSpecialRate, Long> implements
		IFiInternalSpecialRateDao {

}
