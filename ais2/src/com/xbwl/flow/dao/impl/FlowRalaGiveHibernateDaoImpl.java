package com.xbwl.flow.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FlowRalaGive;
import com.xbwl.flow.dao.IFlowRalaGiveDao;

/**
 * Á÷³Ì¸³È¨
 *@author LiuHao
 *@time Apr 19, 2012 4:14:02 PM
 */
@Repository("flowRalaGiveHibernateDaoImpl")
public class FlowRalaGiveHibernateDaoImpl extends BaseDAOHibernateImpl<FlowRalaGive,Long>
		implements IFlowRalaGiveDao {

}
