package com.xbwl.flow.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FlowWorkflowstep;
import com.xbwl.flow.dao.IWorkFlowstepDao;

/**
 * ������־��Ϣ���ݷ��ʲ�ʵ����
 *@author LiuHao
 *@time Feb 24, 2012 5:21:31 PM
 */
@Repository("workFlowstepHibernateDaoImpl")
public class WorkFlowstepHibernateDaoImpl extends BaseDAOHibernateImpl<FlowWorkflowstep,Long>
		implements IWorkFlowstepDao {

}
