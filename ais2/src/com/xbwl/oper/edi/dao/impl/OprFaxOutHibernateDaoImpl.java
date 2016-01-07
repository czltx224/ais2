package com.xbwl.oper.edi.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprFaxOut;
import com.xbwl.oper.edi.dao.IOprFaxOutDao;

/**
 *@author LiuHao
 *@time Apr 16, 2012 4:34:53 PM
 */
@Repository("oprFaxOutHibernateDaoImpl")
public class OprFaxOutHibernateDaoImpl extends BaseDAOHibernateImpl<OprFaxOut,Long> implements
		IOprFaxOutDao {
}
