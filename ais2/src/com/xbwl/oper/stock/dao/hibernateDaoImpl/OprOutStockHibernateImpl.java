package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.oper.fax.vo.OprFaxInVo;
import com.xbwl.oper.stock.dao.IOprOutStockDao;

/**
 * author shuw
 * time Aug 22, 2011 11:38:19 AM
 */
@Repository("oprOutStockHibernateDaoImpl")
public class OprOutStockHibernateImpl extends BaseDAOHibernateImpl<OprFaxInVo, Long>
			implements IOprOutStockDao{

}
