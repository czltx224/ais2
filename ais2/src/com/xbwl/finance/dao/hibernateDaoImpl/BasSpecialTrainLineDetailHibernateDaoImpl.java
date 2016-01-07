package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasSpecialTrainLineDetail;
import com.xbwl.finance.dao.IBasSpecialTrainLineDetailDao;

/**
 * @author CaoZhili time Aug 12, 2011 11:37:05 AM
 * 
 * ר����·ϸ�����ݷ��ʲ�ʵ����
 */
@Repository("basSpecialTrainLineDetailHibernateDaoImpl")
public class BasSpecialTrainLineDetailHibernateDaoImpl extends
		BaseDAOHibernateImpl<BasSpecialTrainLineDetail, Long> implements
		IBasSpecialTrainLineDetailDao {

}
