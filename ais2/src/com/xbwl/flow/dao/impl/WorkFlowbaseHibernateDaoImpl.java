package com.xbwl.flow.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FlowWorkflowbase;
import com.xbwl.flow.dao.IWorkFlowbaseDao;

/**
 *@author LiuHao
 *�������ݷ��ʲ�ʵ����
 *@time Feb 24, 2012 4:30:44 PM
 */
@Repository("workFlowbaseHibernateDaoImpl")
public class WorkFlowbaseHibernateDaoImpl extends BaseDAOHibernateImpl<FlowWorkflowbase,Long>
		implements IWorkFlowbaseDao {

}
