package com.xbwl.flow.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FlowNodeinfo;
import com.xbwl.flow.dao.IFlowNodeinfoDao;

/**
 *@author LiuHao
 *@time Feb 17, 2012 4:08:42 PM
 */
@Repository("flowNodeinfoHibernateDaoImpl")
public class FlowNodeinfoHibernateDaoImpl extends BaseDAOHibernateImpl<FlowNodeinfo,Long>
		implements IFlowNodeinfoDao {

}
