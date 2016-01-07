package com.xbwl.flow.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FlowWorkflowinfo;
import com.xbwl.flow.dao.IWorkFlowinfoDao;

/**
 * ������ת��Ϣ���ݷ��ʲ�ʵ����
 *@author LiuHao
 *@time Feb 24, 2012 5:21:31 PM
 */
@Repository("workFlowinfoHibernateDaoImpl")
public class WorkFlowinfoHibernateDaoImpl extends BaseDAOHibernateImpl<FlowWorkflowinfo,Long>
		implements IWorkFlowinfoDao {

}
