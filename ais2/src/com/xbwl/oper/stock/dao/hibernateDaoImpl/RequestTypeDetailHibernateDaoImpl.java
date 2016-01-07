package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.RequestTypeDetail;
import com.xbwl.oper.stock.dao.IRequestTypeDetailDao;

/**
 *@author LiuHao
 *@time Nov 30, 2011 5:11:12 PM
 */
@Repository("requestTypeDetailHibernateDaoImpl")
public class RequestTypeDetailHibernateDaoImpl extends BaseDAOHibernateImpl<RequestTypeDetail,Long>
		implements IRequestTypeDetailDao {
}
