package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprStocktakeDetail;
import com.xbwl.oper.stock.dao.IOprStockTakeDetailDao;


/**
 * author shuw time Jul 2, 2011 2:34:59 PM
 */


@Repository("oprStockTakeDetailDaoImpl")
public class OprStockTakeDetailHibernateDaoImpl extends 
								BaseDAOHibernateImpl<OprStocktakeDetail, Long> implements IOprStockTakeDetailDao{

}
