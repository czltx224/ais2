package com.xbwl.message.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.SmsSendsmsRecord;
import com.xbwl.message.dao.ISmsSendsmsRecordDao;
/**
 * 短信发送记录表数据访问层实现类
 * @author czl
 *
 */
@Repository("smsSendsmsRecordHibernateDaoImpl")
public class SmsSendsmsRecordHibernateDaoImpl extends
		BaseDAOHibernateImpl<SmsSendsmsRecord, Long> implements
		ISmsSendsmsRecordDao {

}
