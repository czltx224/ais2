package com.xbwl.flow.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FlowWorkflowbase;
import com.xbwl.flow.dao.IWorkFlowbaseDao;

/**
 *@author LiuHao
 *流程数据访问层实现类
 *@time Feb 24, 2012 4:30:44 PM
 */
@Repository("workFlowbaseHibernateDaoImpl")
public class WorkFlowbaseHibernateDaoImpl extends BaseDAOHibernateImpl<FlowWorkflowbase,Long>
		implements IWorkFlowbaseDao {

}
