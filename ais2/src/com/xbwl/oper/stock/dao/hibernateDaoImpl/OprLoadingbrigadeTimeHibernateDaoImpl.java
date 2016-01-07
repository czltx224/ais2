package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprLoadingbrigadTime;
import com.xbwl.oper.stock.dao.IOprLoadingbrigadeTimeDao;

/**
 * author shuw
 * time Sep 20, 2011 7:54:16 PM
 */
@Repository("oprLoadingbrigadeTimeHibernateDaoImpl")
public class OprLoadingbrigadeTimeHibernateDaoImpl extends
			BaseDAOHibernateImpl<OprLoadingbrigadTime, Long> implements
			IOprLoadingbrigadeTimeDao {

}
