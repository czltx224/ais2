package com.xbwl.oper.fax.dao.hibernateDaoImpl;
import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprChangeDetail;
import com.xbwl.entity.OprChangeMain;
import com.xbwl.oper.fax.dao.IOprFaxChangeDao;
import com.xbwl.oper.fax.dao.IOprFaxChangeDetailDao;

/**
 *@author LiuHao
 *@time Aug 25, 2011 3:00:59 PM
 */
@Repository("oprFaxChangeDetailHibernateDaoImpl")
public class OprFaxChangeDetailHibernateDaoImpl extends BaseDAOHibernateImpl<OprChangeDetail,Long>
		implements IOprFaxChangeDetailDao {
}
