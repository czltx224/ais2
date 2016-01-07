package com.xbwl.oper.fax.dao.hibernateDaoImpl;
import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprChangeMain;
import com.xbwl.oper.fax.dao.IOprFaxChangeDao;

/**
 *@author LiuHao
 *@time Aug 25, 2011 3:00:59 PM
 */
@Repository("oprFaxChangeHibernateDaoImpl")
public class OprFaxChangeHibernateDaoImpl extends BaseDAOHibernateImpl<OprChangeMain,Long>
		implements IOprFaxChangeDao {
}
