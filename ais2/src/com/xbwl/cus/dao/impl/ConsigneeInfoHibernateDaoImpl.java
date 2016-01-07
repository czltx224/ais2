package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.IConsigneeInfoDao;
import com.xbwl.entity.ConsigneeInfo;

/**
 * author CaoZhili time Oct 9, 2011 2:35:12 PM
 */
@Repository("consigneeInfoHibernateDaoImpl")
public class ConsigneeInfoHibernateDaoImpl extends
		BaseDAOHibernateImpl<ConsigneeInfo, Long> implements IConsigneeInfoDao {

}
