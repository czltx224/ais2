package com.xbwl.flow.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FlowPipeinfo;
import com.xbwl.flow.dao.IFlowPipeinfoDao;

/**
 * 流程管道管理 数据访问层实现类
 *@author LiuHao
 *@time Feb 16, 2012 6:11:15 PM
 */
@Repository("flowPipeinfoHibernateDaoImpl")
public class FlowPipeinfoHibernateDaoImpl extends BaseDAOHibernateImpl<FlowPipeinfo,Long>
		implements IFlowPipeinfoDao {

}
