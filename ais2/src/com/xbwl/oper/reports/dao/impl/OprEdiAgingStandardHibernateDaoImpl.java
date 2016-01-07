package com.xbwl.oper.reports.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprEdiAgingStandard;
import com.xbwl.oper.reports.dao.IOprEdiAgingStandardDao;

/**
 * 中转时效标准数据访问层实现类
 * @author czl
 * @date 2012-05-19
 *
 */
@Repository("oprEdiAgingStandardHibernateDaoImpl")
public class OprEdiAgingStandardHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprEdiAgingStandard, Long> implements
		IOprEdiAgingStandardDao {

}
