package com.xbwl.ct.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.ct.dao.ICtUserDao;

import dto.CtUserDto;

/**
 * EDI ÓÃ»§²Ù×÷
 * author shuw
 * time May 2, 2012 9:51:14 AM
 */
@Repository("ctUserHibernateDaoImpl")
public class CtUserHibernateDaoImpl extends BaseDAOHibernateImpl<CtUserDto,Long> implements ICtUserDao{

}
