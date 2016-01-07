package com.xbwl.finance.dao.hibernateDaoImpl;

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
import com.xbwl.entity.BasStSpecialTrainRate;
import com.xbwl.finance.dao.IStSpecialTrainRateDao;

/**
 *@author LiuHao
 *@time 2011-7-22 ����10:33:36
 */
@Repository("stSpecialTrainRateHibernateDaoImpl")
public class StSpecialTrainRateHibernateDaoImpl extends
		BaseDAOHibernateImpl<BasStSpecialTrainRate, Long> implements IStSpecialTrainRateDao {

}