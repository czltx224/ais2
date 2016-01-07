package com.xbwl.flow.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FlowWorkflowoperators;
import com.xbwl.flow.dao.IWorkFlowoperDao;

/**
 * 流程日志信息数据访问层实现类
 *@author LiuHao
 *@time Feb 24, 2012 5:21:31 PM
 */
@Repository("workFlowoperHibernateDaoImpl")
public class WorkFlowoperHibernateDaoImpl extends BaseDAOHibernateImpl<FlowWorkflowoperators,Long>
		implements IWorkFlowoperDao {

}
