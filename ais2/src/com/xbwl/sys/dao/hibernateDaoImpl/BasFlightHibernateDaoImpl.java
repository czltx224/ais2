package com.xbwl.sys.dao.hibernateDaoImpl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasFlight;
import com.xbwl.sys.dao.IBasFlightDao;


@Repository("basFlightHibernateDaoImpl")
public class BasFlightHibernateDaoImpl extends BaseDAOHibernateImpl<BasFlight,Long> implements
		IBasFlightDao {

}
