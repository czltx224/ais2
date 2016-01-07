package com.xbwl.oper.psonline.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BizOperateLog;
import com.xbwl.oper.psonline.dao.IPSBizOperateLogDao;

/**
 * ��Ӫ��־���ݷ��ʲ�ʵ����
 * @author czl
 * @time 2012-04-10
 */
@Repository("pSBizOperateLogHibernateDaoImpl")
public class PSBizOperateLogHibernateDaoImpl extends
		BaseDAOHibernateImpl<BizOperateLog, String> 
  implements IPSBizOperateLogDao{

}
