package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasTraShipmentRate;
import com.xbwl.finance.dao.IBasTraShipmentRateDao;

/**
 * @author CaoZhili time Aug 4, 2011 10:20:23 AM
 * 
 * ��תЭ������ݷ��ʲ�ʵ����
 */
@Repository("basTraShipmentRateHibernateDaoImpl")
public class BasTraShipmentRateHibernateDaoImpl extends
		BaseDAOHibernateImpl<BasTraShipmentRate, Long> implements
		IBasTraShipmentRateDao {

}
