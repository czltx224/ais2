package com.xbwl.oper.takegoods.dao.impl;

import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprShuntApply;
import com.xbwl.oper.takegoods.dao.IOprShuntApplyDao;

/**
 *@author LiuHao
 *@time Dec 5, 2011 11:30:07 AM
 */
@Repository("oprShuntApplyHibernateDaoImpl")
public class OprShuntApplyHibernateDaoImpl extends BaseDAOHibernateImpl<OprShuntApply,Long>
		implements IOprShuntApplyDao {
}
