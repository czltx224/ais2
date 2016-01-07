package com.xbwl.oper.edi.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.EdiScanTiming;
import com.xbwl.oper.edi.dao.IEdiScanTimingDao;

/**
 * 扫描时间记录表数据访问层操作类
 * @author czl
 * @date 2012-05-24
 */
@Repository("ediScanTimingHibernateDaoImpl")
public class EdiScanTimingHibernateDaoImpl extends
		BaseDAOHibernateImpl<EdiScanTiming, Long> implements IEdiScanTimingDao {

}
