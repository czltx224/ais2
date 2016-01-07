package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiInternalRate;
import com.xbwl.finance.dao.IFiInternalRateDao;

/**
 * author CaoZhili time Oct 19, 2011 5:20:27 PM
 *�ڲ��������ݷ��ʲ�ʵ����
 */
@Repository("fiInternalRateHibernateDaoImpl")
public class FiInternalRateHibernateDaoImpl extends
		BaseDAOHibernateImpl<FiInternalRate, Long> implements
		IFiInternalRateDao {

}
