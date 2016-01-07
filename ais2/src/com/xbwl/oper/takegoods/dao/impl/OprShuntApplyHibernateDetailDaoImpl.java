package com.xbwl.oper.takegoods.dao.impl;

import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprShuntApply;
import com.xbwl.entity.OprShuntApplyDetail;
import com.xbwl.oper.takegoods.dao.IOprShuntApplyDao;
import com.xbwl.oper.takegoods.dao.IOprShuntApplyDetailDao;

/**
 *@author LiuHao
 *@time Dec 5, 2011 11:30:07 AM
 */
@Repository("oprShuntApplyDetailHibernateDaoImpl")
public class OprShuntApplyHibernateDetailDaoImpl extends BaseDAOHibernateImpl<OprShuntApplyDetail,Long>
		implements IOprShuntApplyDetailDao {
}
