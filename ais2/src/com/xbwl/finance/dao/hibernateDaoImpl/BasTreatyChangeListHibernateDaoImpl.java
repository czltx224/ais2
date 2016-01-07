package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasTreatyChangeList;
import com.xbwl.finance.dao.IBasTreatyChangeListDao;

/**
 * @author CaoZhili time Aug 10, 2011 2:28:06 PM
 * 
 * 协议价修改记录表数据访问层实现类
 */
@Repository("basTreatyChangeListHibernateDaoImpl")
public class BasTreatyChangeListHibernateDaoImpl extends
		BaseDAOHibernateImpl<BasTreatyChangeList, Long> implements
		IBasTreatyChangeListDao {

}
