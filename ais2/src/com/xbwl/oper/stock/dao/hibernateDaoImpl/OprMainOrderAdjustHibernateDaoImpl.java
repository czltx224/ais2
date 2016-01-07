package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprMainOrderAdjust;
import com.xbwl.oper.stock.dao.IOprMainOrderAdjustDao;

/**
 * @author CaoZhili time Aug 8, 2011 9:55:02 AM
 * 
 * ����������¼�����ݷ��ʲ�ʵ����
 */
@Repository("oprMainOrderAdjustHibernateDaoImpl")
public class OprMainOrderAdjustHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprMainOrderAdjust, Long> implements
		IOprMainOrderAdjustDao {

}
