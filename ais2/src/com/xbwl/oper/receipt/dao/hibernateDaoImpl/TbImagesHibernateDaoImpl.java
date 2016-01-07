package com.xbwl.oper.receipt.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.TbImages;
import com.xbwl.oper.receipt.dao.ITbImagesDao;

/**
 * ÉÏ´«Í¼Æ¬Dao
 * author shuw
 * time May 7, 2012 11:51:29 AM
 */
@Repository("tbImagesHibernateDaoImpl")
public class TbImagesHibernateDaoImpl extends
										BaseDAOHibernateImpl<TbImages, Long> implements ITbImagesDao{

}
