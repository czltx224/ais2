package com.xbwl.oper.reports.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.oper.reports.dao.IInuploadGoodsDao;

/**
 *@author LiuHao
 *@time Sep 19, 2011 3:24:29 PM
 */
@Repository("inuploadGoodsHibernateDaoImpl")
public class InuploadGoodsHibernateDaoImpl extends BaseDAOHibernateImpl<OprLoadingbrigadeWeight,Long> implements
		IInuploadGoodsDao {
}
