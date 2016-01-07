package com.xbwl.report.print.dao.hibernateImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.SysPrintManager;
import com.xbwl.report.print.dao.ISysPrintManagerDao;

/**
 * @author Administrator
 * @createTime 2:19:58 PM
 * @updateName Administrator
 * @updateTime 2:19:58 PM
 * 
 */
@Repository("sysPrintManagerHibernateImpl")
public class SysPrintManagerDaoImpl extends BaseDAOHibernateImpl<SysPrintManager,Long> implements ISysPrintManagerDao {

}
