package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusCollectionDao;
import com.xbwl.entity.CusCollection;

/**
 *@author LiuHao
 *@time Nov 3, 2011 4:06:34 PM
 */
@Repository("cusCollectionHibernateDaoImpl")
public class CusCollectionHibernateDaoImpl extends BaseDAOHibernateImpl<CusCollection,Long>
		implements ICusCollectionDao {

}
