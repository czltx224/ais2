package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprSign;
import com.xbwl.oper.stock.dao.IOprSignDao;

/**
* author shuw
*/
@Repository
public class OprSignHibernateDaoImpl  
		extends BaseDAOHibernateImpl<OprSign,Long> implements IOprSignDao{
	

}
