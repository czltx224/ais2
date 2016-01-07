package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprPrewiredDetail;
import com.xbwl.oper.stock.dao.IOprPrewiredDetailDao;

/**
 * author shuw
 * time 2011-7-19 ионГ11:11:53
 */

@Repository("oprPrewiredDetailHibernateDaoImpl")
public class OprPrewiredDetailHibernateDaoImpl extends BaseDAOHibernateImpl< OprPrewiredDetail, Long>
	implements IOprPrewiredDetailDao{
	

}
