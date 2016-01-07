package com.xbwl.finance.dao.hibernateDaoImpl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiIncome;
import com.xbwl.finance.dao.IFiIncomeDao;

/**
 *@author LiuHao
 *@time Aug 26, 2011 6:33:05 PM
 */
@Repository("fiIncomeHibernateDaoImpl")
public class FiIncomeHibernateDaoImpl extends BaseDAOHibernateImpl<FiIncome,Long> implements
		IFiIncomeDao {
}
