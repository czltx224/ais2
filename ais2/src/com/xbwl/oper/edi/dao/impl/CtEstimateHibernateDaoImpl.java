package com.xbwl.oper.edi.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.CtEstimate;
import com.xbwl.oper.edi.dao.ICtEstimateDao;

/**
 * EDIԤ�ƻ�����ʱ�����ݷ��ʲ�ʵ����
 * @project ais2
 * @author czl
 * @Time Feb 10, 2012 3:45:52 PM
 */
@Repository("ctEstimateHibernateDaoImpl")
public class CtEstimateHibernateDaoImpl extends BaseDAOHibernateImpl<CtEstimate, String> 
	implements ICtEstimateDao {

}
