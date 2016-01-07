package com.xbwl.oper.edi.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.SysQianshou;
import com.xbwl.oper.edi.dao.ISysQianshouDao;

/**
 * 短信签收记录表数据访问层实现类
 * @author czl
 * @date 2012-05-28
 *
 */
@Repository("sysQianshouHibernateDaoImpl")
public class SysQianshouHibernateDaoImpl extends
		BaseDAOHibernateImpl<SysQianshou, String> implements ISysQianshouDao {

}
