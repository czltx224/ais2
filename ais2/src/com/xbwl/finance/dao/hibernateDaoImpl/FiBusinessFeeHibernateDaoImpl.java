package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiBusinessFee;
import com.xbwl.finance.dao.IFiBusinessFeeDao;

/**
 * author CaoZhili time Oct 17, 2011 3:33:44 PM
 * 业务费管理数据访问层实现类
 */
@Repository("fiBusinessFeeHibernateDaoImpl")
public class FiBusinessFeeHibernateDaoImpl extends 
		BaseDAOHibernateImpl<FiBusinessFee, Long> implements IFiBusinessFeeDao {

}
