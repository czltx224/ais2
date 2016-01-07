package com.xbwl.flow.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FlowExport;
import com.xbwl.flow.dao.IFlowExportDao;

/**
 * ���̹��� - ���ڹ��� ���ݷ��ʲ�ʵ����
 *@author LiuHao
 *@time Feb 20, 2012 11:02:18 AM
 */
@Repository("flowExportHibernateDaoImpl")
public class FlowExportHibernateDaoImpl extends BaseDAOHibernateImpl<FlowExport,Long> implements
		IFlowExportDao {
}
