package com.xbwl.oper.edi.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.CtTmD;
import com.xbwl.oper.edi.dao.ICtTmDDao;

/**
 * EDI������Ϣ���ݷ��ʲ�ʵ����
 * @project ais2
 * @author czl
 * @Time Feb 11, 2012 5:28:22 PM
 */
@Repository("ctTmDHibernateDaoImpl")
public class CtTmDHibernateDaoImpl extends BaseDAOHibernateImpl<CtTmD, String> 
	implements ICtTmDDao{
	
}
