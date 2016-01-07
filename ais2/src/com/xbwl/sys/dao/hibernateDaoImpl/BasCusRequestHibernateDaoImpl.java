package com.xbwl.sys.dao.hibernateDaoImpl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasCusRequest;
import com.xbwl.sys.dao.IBasCusRequestDao;

/**
 *@author LiuHao
 *@time Jun 27, 2011 6:43:42 PM
 */
@Repository("basCusRequestHibernateDaoImpl")
public class BasCusRequestHibernateDaoImpl extends BaseDAOHibernateImpl<BasCusRequest,Long>
		implements IBasCusRequestDao {

}
