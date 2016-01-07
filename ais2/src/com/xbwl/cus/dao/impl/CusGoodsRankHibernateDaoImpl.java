package com.xbwl.cus.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusGoodsRankDao;
import com.xbwl.entity.CusGoodsRank;

/**
 *@author LiuHao
 *@time May 18, 2012 10:04:44 AM
 */
@Repository("cusGoodsRankHibernateDaoImpl")
public class CusGoodsRankHibernateDaoImpl extends BaseDAOHibernateImpl<CusGoodsRank,Long>
		implements ICusGoodsRankDao {

}
