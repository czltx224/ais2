package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprPrewired;
import com.xbwl.oper.stock.dao.IOprPrewiredDao;

/**
 * author shuw
 * time 2011-7-19 ионГ11:07:28
 */
@Repository("oprPrewiredHibernateDaoImpl")
public class OprPrewiredHibernateDaoImpl extends BaseDAOHibernateImpl<OprPrewired, Long>
			implements IOprPrewiredDao{
	

}
