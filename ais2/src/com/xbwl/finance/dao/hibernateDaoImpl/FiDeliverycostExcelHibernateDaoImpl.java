package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiDeliverycostExcel;
import com.xbwl.finance.dao.IFiDeliverycostExcelDao;

/**
 * author shuw
 * time Oct 22, 2011 10:53:04 AM
 */
@Repository("fiDeliverycostExcelHibernateDaoImpl")
public class FiDeliverycostExcelHibernateDaoImpl extends BaseDAOHibernateImpl<FiDeliverycostExcel,Long>
								implements IFiDeliverycostExcelDao {

}
