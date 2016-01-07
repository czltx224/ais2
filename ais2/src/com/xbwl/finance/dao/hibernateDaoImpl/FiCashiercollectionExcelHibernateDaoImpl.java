package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiCashiercollectionExcel;
import com.xbwl.finance.dao.IFiCashiercollectionExcelDao;

/**
 * author shuw
 * time Nov 15, 2011 9:53:11 AM
 */
@Repository("fiCashiercollectionExcelHibernateDaoImpl")
public class FiCashiercollectionExcelHibernateDaoImpl extends BaseDAOHibernateImpl<FiCashiercollectionExcel,Long>
		implements IFiCashiercollectionExcelDao {
}
