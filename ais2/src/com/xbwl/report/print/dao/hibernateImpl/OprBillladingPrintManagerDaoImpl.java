package com.xbwl.report.print.dao.hibernateImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprBillladingPrintManager;
import com.xbwl.report.print.dao.IOprBillladingPrintManagerDao;

/**
 * author CaoZhili time Nov 1, 2011 9:52:40 AM
 */
@Repository("oprBillladingPrintManagerDaoImpl")
public class OprBillladingPrintManagerDaoImpl extends
		BaseDAOHibernateImpl<OprBillladingPrintManager, Long> implements IOprBillladingPrintManagerDao {

}
