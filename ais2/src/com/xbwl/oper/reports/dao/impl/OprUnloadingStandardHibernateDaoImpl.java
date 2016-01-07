package com.xbwl.oper.reports.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprUnloadingStandard;
import com.xbwl.oper.reports.dao.IOprUnloadingStandardDao;

/**卸货时效标准数据访问层实现类
 * @author Caozhili
 *
 */
@Repository("oprUnloadingStandardHibernateDaoImpl")
public class OprUnloadingStandardHibernateDaoImpl extends BaseDAOHibernateImpl<OprUnloadingStandard, Long> implements IOprUnloadingStandardDao{

}
