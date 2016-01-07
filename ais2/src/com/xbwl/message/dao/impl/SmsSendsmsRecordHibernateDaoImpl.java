package com.xbwl.message.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.SmsSendsmsRecord;
import com.xbwl.message.dao.ISmsSendsmsRecordDao;
/**
 * ���ŷ��ͼ�¼�����ݷ��ʲ�ʵ����
 * @author czl
 *
 */
@Repository("smsSendsmsRecordHibernateDaoImpl")
public class SmsSendsmsRecordHibernateDaoImpl extends
		BaseDAOHibernateImpl<SmsSendsmsRecord, Long> implements
		ISmsSendsmsRecordDao {

}
