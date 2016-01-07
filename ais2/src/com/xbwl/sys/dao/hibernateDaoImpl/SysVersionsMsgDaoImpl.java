package com.xbwl.sys.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.log.dao.ISysVersionsMsgDao;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.SysVersionsMsg;

/**
 * @author Administrator
 * @createTime 11:33:51 AM
 * @updateName Administrator
 * @updateTime 11:33:51 AM
 * 
 */
@Repository("sysVersionsMsgHibernateImpl")
public class SysVersionsMsgDaoImpl extends BaseDAOHibernateImpl<SysVersionsMsg,Long> implements
		ISysVersionsMsgDao {

}
