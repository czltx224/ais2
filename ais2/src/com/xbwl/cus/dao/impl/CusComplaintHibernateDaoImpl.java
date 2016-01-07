package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusComplaintDao;
import com.xbwl.entity.CusComplaint;

/**
 *@author LiuHao
 *@time Oct 21, 2011 3:06:12 PM
 */
@Repository("cusComplaintHibernateDaoImpl")
public class CusComplaintHibernateDaoImpl extends BaseDAOHibernateImpl<CusComplaint,Long>
		implements ICusComplaintDao {
}
