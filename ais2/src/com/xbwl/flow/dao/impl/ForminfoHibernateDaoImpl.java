package com.xbwl.flow.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FlowForminfo;
import com.xbwl.flow.dao.IForminfoDao;

/**
 * 流程管理-表单信息数据访问层实现类
 *@author LiuHao
 *@time Feb 14, 2012 4:27:35 PM
 */
@Repository("forminfoHibernateDaoImpl")
public class ForminfoHibernateDaoImpl extends BaseDAOHibernateImpl<FlowForminfo,Long> implements
		IForminfoDao {

}
