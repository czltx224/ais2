package com.xbwl.sys.dao.hibernateDaoImpl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasArea;
import com.xbwl.sys.dao.IBasAreaDao;

/**
 *author LiuHao
 *time Jun 22, 2011 11:54:03 AM
 */
@Repository("basAreaDaoImpl")
public class BasAreaHibernateDaoImpl extends BaseDAOHibernateImpl<BasArea,Long> implements
		IBasAreaDao {

}
