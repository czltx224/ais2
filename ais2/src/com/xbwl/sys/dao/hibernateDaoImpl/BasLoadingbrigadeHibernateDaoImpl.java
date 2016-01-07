package com.xbwl.sys.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasDictionaryDetail;
import com.xbwl.entity.BasLoadingbrigade;
import com.xbwl.sys.dao.IBasLoadingbrigadeDao;
import com.xbwl.sys.dao.IBasDictionaryDetailDao;

@Repository("basLoadingbrigadeDaoImpl")
public class BasLoadingbrigadeHibernateDaoImpl extends BaseDAOHibernateImpl <BasLoadingbrigade,Long> implements IBasLoadingbrigadeDao {

}
