package com.xbwl.oper.edi.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.EDIOprHistory;
import com.xbwl.oper.edi.dao.IEDIOprHistoryDao;

@Repository("ediOprHistoryHibernateDaoImpl")
public class EDIOprHistoryHibernateDaoImpl extends
		BaseDAOHibernateImpl<EDIOprHistory, Long> implements IEDIOprHistoryDao {

}
