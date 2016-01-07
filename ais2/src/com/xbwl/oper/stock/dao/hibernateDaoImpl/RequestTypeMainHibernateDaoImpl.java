package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.RequestTypeMain;
import com.xbwl.oper.stock.dao.IRequestTypeMainDao;

/**
 *@author LiuHao
 *@time Nov 30, 2011 5:09:30 PM
 */
@Repository("requestTypeMainHibernateDaoImpl")
public class RequestTypeMainHibernateDaoImpl extends BaseDAOHibernateImpl<RequestTypeMain,Long>
		implements IRequestTypeMainDao {
}
