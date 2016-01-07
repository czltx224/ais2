package com.xbwl.finance.dao.hibernateDaoImpl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiReceivabledetail;
import com.xbwl.finance.dao.IFiReceivabledetailDao;

@Repository("fiReceivabledetailHibernateDaoImpl")
public class FiReceivabledetailHibernateDaoImpl extends BaseDAOHibernateImpl<FiReceivabledetail,Long>
		implements IFiReceivabledetailDao {

}
