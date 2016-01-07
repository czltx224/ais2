package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasStSpecialTrainRate;
import com.xbwl.finance.dao.IBasStSpecialTrainRateDao;

/**
 * @author CaoZhili time Aug 2, 2011 3:58:34 PM
 * 
 * ר����׼Э������ݷ��ʲ�ʵ����
 */
@Repository("basStSpecialTrainRateHibernateDaoImpl")
public class BasStSpecialTrainRateHibernateDaoImpl extends
		BaseDAOHibernateImpl<BasStSpecialTrainRate, Long> implements
		IBasStSpecialTrainRateDao {

}
