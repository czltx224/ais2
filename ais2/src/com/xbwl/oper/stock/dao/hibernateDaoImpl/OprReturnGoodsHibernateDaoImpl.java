package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprReturnGoods;
import com.xbwl.oper.stock.dao.IOprReturnGoodsDao;

/**
 * @author CaoZhili
 * time Jul 30, 2011 10:42:03 AM
 * 
 * 返货管理数据访问层实现类
 */
@Repository
public class OprReturnGoodsHibernateDaoImpl extends BaseDAOHibernateImpl<OprReturnGoods, Long> 
		implements IOprReturnGoodsDao{

}
