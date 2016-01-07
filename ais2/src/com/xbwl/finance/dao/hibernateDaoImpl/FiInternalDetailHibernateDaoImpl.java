package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiInternalDetail;
import com.xbwl.finance.dao.IFiInternalDetailDao;

/**
 * author CaoZhili time Oct 20, 2011 1:50:11 PM
 * �ڲ�������ϸ���ݷ��ʲ�ʵ����
 */
@Repository("fiInternalDetailHibernateDaoImpl")
public class FiInternalDetailHibernateDaoImpl extends
		BaseDAOHibernateImpl<FiInternalDetail, Long> implements
		IFiInternalDetailDao {

}
