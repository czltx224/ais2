package com.xbwl.sys.dao.hibernateDaoImpl;

 
import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasDictionaryDetail;
import com.xbwl.sys.dao.IBasDictionaryDetailDao;

@Repository("basDictionaryDetailDaoImpl")
public class BasDictionaryDetailHibernateDaoImpl extends BaseDAOHibernateImpl <BasDictionaryDetail,Long> implements IBasDictionaryDetailDao {
 

}
