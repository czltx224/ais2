package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprValueAddFee;
import com.xbwl.oper.stock.dao.IOprValueAddFeeDao;

/**
 * author CaoZhili time Jul 21, 2011 9:56:35 AM
 */
@Repository("oprValueAddFeeHibernateDaoImpl")
public class OprValueAddFeeHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprValueAddFee, Long> implements IOprValueAddFeeDao {

}
