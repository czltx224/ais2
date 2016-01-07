package com.xbwl.flow.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FlowRalarule;
import com.xbwl.flow.dao.IFlowRalaruleDao;

/**
 * ���̹���  Ȩ�޹������ݷ��ʲ�ʵ����
 *@author LiuHao
 *@time Feb 21, 2012 8:53:59 AM
 */
@Repository("flowRalaruleHibernateDaoImpl")
public class FlowRalaruleHibernateDaoImpl extends BaseDAOHibernateImpl<FlowRalarule,Long>
		implements IFlowRalaruleDao {

}
