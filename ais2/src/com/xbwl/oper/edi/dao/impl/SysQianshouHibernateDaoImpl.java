package com.xbwl.oper.edi.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.SysQianshou;
import com.xbwl.oper.edi.dao.ISysQianshouDao;

/**
 * ����ǩ�ռ�¼�����ݷ��ʲ�ʵ����
 * @author czl
 * @date 2012-05-28
 *
 */
@Repository("sysQianshouHibernateDaoImpl")
public class SysQianshouHibernateDaoImpl extends
		BaseDAOHibernateImpl<SysQianshou, String> implements ISysQianshouDao {

}
