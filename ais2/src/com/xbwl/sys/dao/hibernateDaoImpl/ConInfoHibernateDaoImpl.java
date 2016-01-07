package com.xbwl.sys.dao.hibernateDaoImpl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.PropertyFilter.MatchType;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.ConsigneeInfo;
import com.xbwl.sys.dao.IConInfoDao;

/**
 *@author LiuHao
 *@time 2011-7-19 ÏÂÎç03:27:34
 */
@Repository("conInfoHibernateDaoImpl")
public class ConInfoHibernateDaoImpl extends BaseDAOHibernateImpl<ConsigneeInfo, Long>
		implements IConInfoDao {

}
