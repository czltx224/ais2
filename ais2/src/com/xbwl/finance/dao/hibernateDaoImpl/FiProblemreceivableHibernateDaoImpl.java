package com.xbwl.finance.dao.hibernateDaoImpl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FiProblemreceivable;
import com.xbwl.finance.dao.IFiProblemreceivableDao;

@Repository("fiProblemreceivableHibernateDaoImpl")
public class FiProblemreceivableHibernateDaoImpl extends
		BaseDAOHibernateImpl<FiProblemreceivable, Long> implements
		IFiProblemreceivableDao {

}
