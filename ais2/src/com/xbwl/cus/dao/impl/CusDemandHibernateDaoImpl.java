package com.xbwl.cus.dao.impl;

import java.io.File;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusDemandDao;
import com.xbwl.entity.CusDemand;

/**
 *@author LiuHao
 *@time Oct 9, 2011 9:12:32 AM
 */
@Repository("cusDemandHibernateDaoImpl")
public class CusDemandHibernateDaoImpl extends BaseDAOHibernateImpl<CusDemand,Long> implements
		ICusDemandDao {
}
