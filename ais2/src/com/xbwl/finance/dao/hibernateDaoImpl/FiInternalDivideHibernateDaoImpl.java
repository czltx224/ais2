package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiInternalDivide;
import com.xbwl.finance.dao.IFiInternalDivideDao;

/**
 * author CaoZhili time Oct 21, 2011 4:29:30 PM
 * 内部结算划分数据访问层实现类
 */
@Repository("fiInternalDivideHibernateDaoImpl")
public class FiInternalDivideHibernateDaoImpl extends
		BaseDAOHibernateImpl<FiInternalDivide, Long> implements
		IFiInternalDivideDao {

}
