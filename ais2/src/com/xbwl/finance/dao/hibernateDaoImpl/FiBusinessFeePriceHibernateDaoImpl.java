package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiBusinessFeePrice;
import com.xbwl.finance.dao.IFiBusinessFeePriceDao;

/**
 * author shuw ҵ���Э��۹������ݷ��ʲ�ʵ����
 * time Dec 26, 2011 10:09:29 AM
 */
@Repository("fiBusinessFeePriceHibernateDaoImpl")
public class FiBusinessFeePriceHibernateDaoImpl extends 
								BaseDAOHibernateImpl<FiBusinessFeePrice, Long> implements IFiBusinessFeePriceDao {

}
