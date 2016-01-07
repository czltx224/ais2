package com.xbwl.sys.dao.hibernateDaoImpl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasCusService;
import com.xbwl.sys.dao.IBasCusServiceDao;

/**
 *@author LiuHao
 *@time Jul 29, 2011 4:11:38 PM
 */
@Repository("basCusServiceHibernateDaoImpl")
public class BasCusServiceHibernateDaoImpl extends BaseDAOHibernateImpl<BasCusService,Long>
		implements IBasCusServiceDao {

	public List<BasCusService> findCusServices(Long cusId, Long bussDepart)
			throws Exception {
		return this.find("from BasCusService bcs where bcs.departId=? and bcs.cusId=?",bussDepart,cusId);
		
	}
}
