package com.xbwl.flow.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FlowFormlink;
import com.xbwl.flow.dao.IFormlinkDao;

/**
 * ���̹���-���������ݷ��ʲ�ʵ����
 *@author LiuHao
 *@time Feb 14, 2012 4:33:53 PM
 */
@Repository("formlinkHibernateDaoImpl")
public class FormlinkHibernateDaoImpl extends BaseDAOHibernateImpl<FlowFormlink,Long> implements
		IFormlinkDao {
}
