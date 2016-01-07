package com.xbwl.flow.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FlowWorkflowlog;
import com.xbwl.flow.dao.IWorkFlowlogDao;

/**
 * ������־��Ϣ���ݷ��ʲ�ʵ����
 *@author LiuHao
 *@time Feb 24, 2012 5:21:31 PM
 */
@Repository("workFlowlogHibernateDaoImpl")
public class WorkFlowlogHibernateDaoImpl extends BaseDAOHibernateImpl<FlowWorkflowlog,Long>
		implements IWorkFlowlogDao {

}
