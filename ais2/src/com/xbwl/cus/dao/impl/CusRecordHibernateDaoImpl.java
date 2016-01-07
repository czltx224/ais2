package com.xbwl.cus.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.cus.dao.ICusRecordDao;
import com.xbwl.entity.CusRecord;

/**
 *@author LiuHao
 *@time Oct 8, 2011 1:48:06 PM
 */
@Repository("cusRecordHibernateDaoImpl")
public class CusRecordHibernateDaoImpl extends BaseDAOHibernateImpl<CusRecord,Long> implements
		ICusRecordDao {

	public List<CusRecord> findCusRecord(String name, Long bussDepart)
			throws Exception {
		return this.find("from CusRecord cr where cr.cusName=? and cr.departId=?",name,bussDepart);
	}

}
