package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasTraShipmentRate;
import com.xbwl.finance.dao.IBasTraShipmentRateDao;

/**
 * @author CaoZhili time Aug 4, 2011 10:20:23 AM
 * 
 * 中转协议价数据访问层实现类
 */
@Repository("basTraShipmentRateHibernateDaoImpl")
public class BasTraShipmentRateHibernateDaoImpl extends
		BaseDAOHibernateImpl<BasTraShipmentRate, Long> implements
		IBasTraShipmentRateDao {

}
