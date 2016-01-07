package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprStocktake;
import com.xbwl.entity.OprStocktakeDetail;
import com.xbwl.oper.stock.dao.IOprStockTakeDao;
import com.xbwl.oper.stock.dao.IOprStockTakeDetailDao;

/**
 * @author Administrator
 * ÅÌµãÖ÷±í
 *
 */
@Repository("oprStockTakeDaoImpl")
public class OprStockTakeHibernateDaoImpl extends 
	BaseDAOHibernateImpl<OprStocktake, Long> implements IOprStockTakeDao{

}
