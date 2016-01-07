package com.xbwl.flow.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FlowFormfield;
import com.xbwl.flow.dao.IFormfieldDao;

/**
 * 流程管理-表单字段数据访问层实现类
 *@author LiuHao
 *@time Feb 14, 2012 4:18:47 PM
 */
@Repository("formfieldHibernateDaoImpl")
public class FormfieldHibernateDaoImpl extends BaseDAOHibernateImpl<FlowFormfield,Long> implements
		IFormfieldDao {

}
