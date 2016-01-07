package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprStoreArea;
import com.xbwl.oper.stock.dao.IOprStoreAreaDao;

@Repository("oprStoreAreaHibernateDaoImpl")
public class OprStoreAreaHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprStoreArea, Long> implements IOprStoreAreaDao {

}
