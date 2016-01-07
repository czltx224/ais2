package com.xbwl.sys.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasDictionary;
import com.xbwl.sys.dao.IBasDictionaryDao;

@Repository("basDictionaryDaoImpl")
public class BasDictionaryHibernateDaoImpl extends BaseDAOHibernateImpl <BasDictionary,Long> implements IBasDictionaryDao {

}
