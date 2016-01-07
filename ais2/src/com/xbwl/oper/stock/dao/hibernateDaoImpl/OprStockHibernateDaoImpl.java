package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprStock;
import com.xbwl.oper.stock.dao.IOprStockDao;

@Repository("oprStockDaoImpl")
public class OprStockHibernateDaoImpl extends 
			BaseDAOHibernateImpl<OprStock, Long> implements IOprStockDao {

}
