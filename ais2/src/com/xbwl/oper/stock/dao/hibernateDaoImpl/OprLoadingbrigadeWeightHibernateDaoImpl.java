package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.oper.stock.dao.IOprLoadingbrigadeWeightDao;

/**
 * author CaoZhili time Jul 8, 2011 10:06:06 AM
 */
@Repository("oprLoadingbrigadeWeightHibernateDaoImpl")
public class OprLoadingbrigadeWeightHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprLoadingbrigadeWeight, Long> implements
		IOprLoadingbrigadeWeightDao {

}
